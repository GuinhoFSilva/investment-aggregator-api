package com.guinhofsilva.agregadorinvestimentos.controller;

import com.guinhofsilva.agregadorinvestimentos.Dto.CreateAccountDto;
import com.guinhofsilva.agregadorinvestimentos.Dto.CreateUserDto;
import com.guinhofsilva.agregadorinvestimentos.Dto.UpdateUserDto;
import com.guinhofsilva.agregadorinvestimentos.model.Account;
import com.guinhofsilva.agregadorinvestimentos.model.User;
import com.guinhofsilva.agregadorinvestimentos.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@RequestBody CreateUserDto userDto) {
        UUID userId = userService.save(userDto);
        return ResponseEntity.created(URI.create(userId.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(userService.updateUser(id, updateUserDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Account> createAccount(@PathVariable UUID userId, @RequestBody CreateAccountDto accountDto) {
        return ResponseEntity.created(URI.create(userService.createAccount(userId, accountDto).toString())).build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<Account>> getAllAccountsFromUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getAllAccountsFromUser(userId));
    }


}
