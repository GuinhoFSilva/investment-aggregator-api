package com.guinhofsilva.agregadorinvestimentos.service;

import com.guinhofsilva.agregadorinvestimentos.Dto.CreateUserDto;
import com.guinhofsilva.agregadorinvestimentos.Dto.UpdateUserDto;
import com.guinhofsilva.agregadorinvestimentos.exceptions.EmailAlreadyUsedException;
import com.guinhofsilva.agregadorinvestimentos.exceptions.EmptyRequestBodyException;
import com.guinhofsilva.agregadorinvestimentos.exceptions.ResourceNotFoundException;
import com.guinhofsilva.agregadorinvestimentos.exceptions.UsernameAlreadyUsedException;
import com.guinhofsilva.agregadorinvestimentos.mapper.UserMapper;
import com.guinhofsilva.agregadorinvestimentos.model.User;
import com.guinhofsilva.agregadorinvestimentos.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID save(CreateUserDto user) {
        if (user != null) {
            User userToSave = userRepository.save(UserMapper.createUserMapper(user));
            return userToSave.getUser_id();
        } else {
            throw new EmptyRequestBodyException();
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found!");
        } else {
            return user.get();
        }
    }

    public User updateUser(UUID id, UpdateUserDto user) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found!");
        } else {
            User userToUpdate = existingUser.get();
            if (user.email() != null && !user.email().equals(userToUpdate.getEmail())) {
                validateEmail(user, id);
            }

            if (user.username() != null && !user.username().equals(userToUpdate.getUsername())) {
                validateUsername(user, id);
            }

            return userRepository.save(UserMapper.updateUserMapper(user, userToUpdate));
        }
    }

    public void deleteUser(UUID id){
        Optional<User> userToDelete = userRepository.findById(id);

        if(userToDelete.isEmpty()){
            throw new ResourceNotFoundException("User not found!");
        }

        userRepository.delete(userToDelete.get());
    }

    public void validateEmail(UpdateUserDto user, UUID currentUserId){
        Optional<User> email = userRepository.findByEmail(user.email());
        if (email.isPresent() && !email.get().getUser_id().equals(currentUserId)) {
            throw new EmailAlreadyUsedException("This email is already used!");
        }
    }

    public void validateUsername(UpdateUserDto user, UUID currentUserId){
        Optional<User> username = userRepository.findByUsername(user.username());
        if(username.isPresent() && !username.get().getUser_id().equals(currentUserId)){
            throw new UsernameAlreadyUsedException("This username is already used!");
        }
    }

}

