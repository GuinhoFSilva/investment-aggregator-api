package com.guinhofsilva.agregadorinvestimentos.service;

import com.guinhofsilva.agregadorinvestimentos.Dto.CreateUserDto;
import com.guinhofsilva.agregadorinvestimentos.Dto.UpdateUserDto;
import com.guinhofsilva.agregadorinvestimentos.exceptions.EmailAlreadyUsedException;
import com.guinhofsilva.agregadorinvestimentos.exceptions.EmptyRequestBodyException;
import com.guinhofsilva.agregadorinvestimentos.exceptions.ResourceNotFoundException;
import com.guinhofsilva.agregadorinvestimentos.exceptions.UsernameAlreadyUsedException;
import com.guinhofsilva.agregadorinvestimentos.model.User;
import com.guinhofsilva.agregadorinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;
    // No trecho acima, estou a dizer para o mockito criar uma instância da classe de Service, injetando os mocks, nesse caso o userRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> emailArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> usernameArgumentCaptor;


    // Todo teste tem que passar por três passos, arrange, act e assert. Cada passo diz o que o teste irá seguir, primeiro ele arruma/organiza tudo necessário, depois chama o trecho que gostaríamos de testar e, por fim, será feita todas as verificações para ver se ele executou tudo necessário.

    // Como é um teste de unidade, o objetivo é testar exatamente tudo aquilo que está dentro da classe, não dependendo de implementações de classes exteriores, toda dependência exterior estára mockada pelo mockito.

    @Nested
    class CreateUser {
        @Test
        @DisplayName("Should Create a User With Success")
        void shouldCreateAUser() {
            // Arrange
            User user = new User(UUID.randomUUID(), "username", "email@email.email", "password", Instant.now(), null);
            doReturn(user).when(userRepository).save(any(User.class));
            CreateUserDto input = new CreateUserDto("username", "email@email.com", "password");
            // Act
            UUID output = userService.save(input);
            // Assert
            assertNotNull(output);
            verify(userRepository).save(userArgumentCaptor.capture());
            User userCaptured = userArgumentCaptor.getValue();


            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());

        }

        @Test
        @DisplayName("Should Not Create a User And Throw a EmptyRequestBodyException")
        void shouldNotCreateAUserAndThrowException() {
            // Arrange
            CreateUserDto input = null;
            // Act
            // Assert
            assertThrows(EmptyRequestBodyException.class, () -> userService.save(input));
        }
    }

    @Nested
    class GetUserById {
        @Test
        @DisplayName("Should Return a User With Success")
        void shouldGetAUser() {
            // Arrange
            User user = new User(UUID.randomUUID(), "username", "email@email.email", "password", Instant.now(), null);
            doReturn(Optional.of(user)).when(userRepository).findById(any(UUID.class));
            // Act
            var output = userService.findById(user.getUser_id());
            // Assert
            assertNotNull(output);
            verify(userRepository).findById(uuidArgumentCaptor.capture());
            assertEquals(user.getUser_id(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should Not Return a User And Throws a ResourceNotFoundException")
        void shouldNotGetAUserAndThrowsException() {
            // Arrange
            UUID userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(any(UUID.class));
            // Act
            // Assert
            assertThrows(ResourceNotFoundException.class, () -> userService.findById(userId));
            verify(userRepository).findById(uuidArgumentCaptor.capture());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class GetAllUsers {
        @Test
        @DisplayName("Should Return All Users With Success")
        void shouldReturnAllUsersWithSuccess() {
            // Arrange
            User user = new User(UUID.randomUUID(), "username", "email@email.email", "password", Instant.now(), null);
            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();
            // Act
            var output = userService.findAll();
            // Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class UpdateUser {
        @Test
        @DisplayName("Should Update a User With Success")
            void shouldUpdateAUser() {
                // Arrange
                UUID id = UUID.randomUUID();
                User user = new User(id, "username", "email@email.email", "password", Instant.now(), Instant.now());
                doReturn(Optional.of(user)).when(userRepository).findById(any(UUID.class));
                doReturn(user).when(userRepository).save(any(User.class));
                UpdateUserDto input = new UpdateUserDto("username", "email", "password");

                User output = userService.updateUser(id, input);

                assertNotNull(output);
                verify(userRepository, times(1)).findById(uuidArgumentCaptor.capture());
                verify(userRepository, times(1)).save(userArgumentCaptor.capture());
                assertEquals(input.username(), userArgumentCaptor.getValue().getUsername());
                assertEquals(input.email(), userArgumentCaptor.getValue().getEmail());
                assertEquals(input.password(), userArgumentCaptor.getValue().getPassword());
                assertEquals(id, uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should Not Update a User When User Not Exists")
        void shouldNotUpdateAUserWhenUserNotExists() {
            // Arrange
            UUID id = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(any(UUID.class));
            UpdateUserDto input = new UpdateUserDto("username", "email", "password");


            assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(id, input));

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.capture());
            assertEquals(id, uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).save(any());
        }
    }

    @Nested
    class ValidateEmail {
        @Test
        @DisplayName("Should Validate Email With Success")
        void shouldValidateEmailWithSuccess() {
            // Arrange
            UUID id = UUID.randomUUID();
            UpdateUserDto input = new UpdateUserDto("username", "email", "password");
            doReturn(Optional.empty()).when(userRepository).findByEmail(any(String.class));
            // Act
            // Assert
            assertDoesNotThrow(() -> userService.validateEmail(input, id));
            verify(userRepository, times(1)).findByEmail(emailArgumentCaptor.capture());
            assertEquals(input.email(), emailArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should Validate Email If From The Same User")
        void shouldValidateEmailIfFromTheSameUser() {
            // Arrange
            UUID id = UUID.randomUUID();
            User user = new User(id, "username", "email@email.email", "password", Instant.now(), Instant.now());
            UpdateUserDto input = new UpdateUserDto("username", "email@email.email", "password");

            doReturn(Optional.of(user)).when(userRepository).findByEmail(any(String.class));
            // Act
            // Assert
            assertDoesNotThrow(() -> userService.validateEmail(input, id));
            verify(userRepository, times(1)).findByEmail(emailArgumentCaptor.capture());
            assertEquals(input.email(), emailArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should Not Validate Email And Throws EmailAlreadyExistsException")
        void shouldNotValidateEmailThrowsEmailAlreadyExistsException() {
            // Arrange
            UUID id = UUID.randomUUID();
            User user = new User(UUID.randomUUID(), "username", "email@email.email", "password", Instant.now(), Instant.now());
            UpdateUserDto input = new UpdateUserDto("username", "email@email.email", "password");
            doReturn(Optional.of(user)).when(userRepository).findByEmail(any(String.class));
            // Act
            assertThrows(EmailAlreadyUsedException.class, () -> userService.validateEmail(input, id));
            // Assert
            verify(userRepository, times(1)).findByEmail(emailArgumentCaptor.capture());
            assertEquals(input.email(), emailArgumentCaptor.getValue());
        }
    }

    @Nested
    class ValidateUsername {
        @Test
        @DisplayName("Should Validate Username With Success")
        void shouldValidateUsernameWithSuccess() {
            // Arrange
            UUID id = UUID.randomUUID();
            UpdateUserDto input = new UpdateUserDto("username", "email@email.email", "password");
            doReturn(Optional.empty()).when(userRepository).findByUsername(any(String.class));
            // Act
            // Assert
            assertDoesNotThrow(() -> userService.validateUsername(input, id));
            verify(userRepository, times(1)).findByUsername(usernameArgumentCaptor.capture());
            assertEquals(input.username(), usernameArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should Validate Username If From The Same User")
        void shouldValidateUsernameIfFromTheSameUser() {
            // Arrange
            UUID id = UUID.randomUUID();
            User user = new User(id, "username", "email@email.email", "password", Instant.now(), Instant.now());
            UpdateUserDto input = new UpdateUserDto("username", "email@email.email", "password");

            doReturn(Optional.of(user)).when(userRepository).findByUsername(any(String.class));
            // Act
                // Assert
            assertDoesNotThrow(() -> userService.validateUsername(input, id));
            verify(userRepository, times(1)).findByUsername(usernameArgumentCaptor.capture());
            assertEquals(input.username(), usernameArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should Not Validate Username And Throws UsernameAlreadyExistsException")
        void shouldNotValidateUsernameThrowsUsernameAlreadyExistsException() {
            // Arrange
            UUID id = UUID.randomUUID();
            User user = new User(UUID.randomUUID(), "username", "email@email.email", "password", Instant.now(), Instant.now());
            UpdateUserDto input = new UpdateUserDto("username", "email@email.email", "password");
            doReturn(Optional.of(user)).when(userRepository).findByUsername(any(String.class));
            // Act
            assertThrows(UsernameAlreadyUsedException.class, () -> userService.validateUsername(input, id));
            // Assert
            verify(userRepository, times(1)).findByUsername(usernameArgumentCaptor.capture());
            assertEquals(input.username(), usernameArgumentCaptor.getValue());
        }
    }


    @Nested
    class DeleteUser {
        @Test
        @DisplayName("Should Delete a User With Success")
        void shouldDeleteAUserWithSuccess() {
            // Arrange
            UUID id = UUID.randomUUID();
            doReturn(true).when(userRepository).existsById(any(UUID.class));
            doNothing().when(userRepository).deleteById(any(UUID.class));
            // Act
            userService.deleteUser(id);
            // Assert
            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.capture());
            verify(userRepository, times(1)).deleteById(uuidArgumentCaptor.capture());
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(id, idList.get(0));
            assertEquals(id, idList.get(1));
        }

        @Test
        @DisplayName("Should Not Delete a User When User Not Exists")
        void shouldNotDeleteAUserWhenUserNotExists() {
            // Arrange
            UUID id = UUID.randomUUID();
            doReturn(false).when(userRepository).existsById(id);
            // Act
            // Assert
            assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(id));
            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.capture());
            verify(userRepository, times(0)).deleteById(any());
            assertEquals(id, uuidArgumentCaptor.getValue());
        }


    }


}