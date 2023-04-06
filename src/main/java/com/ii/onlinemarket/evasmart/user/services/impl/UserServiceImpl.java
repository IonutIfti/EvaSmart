package com.ii.onlinemarket.evasmart.user.services.impl;

import com.ii.onlinemarket.evasmart.user.exceptions.UserNotFoundException;
import com.ii.onlinemarket.evasmart.user.exceptions.UserServiceException;
import com.ii.onlinemarket.evasmart.user.mappers.UserMapper;
import com.ii.onlinemarket.evasmart.user.models.User;
import com.ii.onlinemarket.evasmart.user.payload.UserDTO;
import com.ii.onlinemarket.evasmart.user.repositories.UserRepository;
import com.ii.onlinemarket.evasmart.user.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        try {
            User user = userMapper.mapToEntity(userDTO);
            User savedUser = userRepository.save(user);
            log.info("Created user with Email: {}",savedUser.getEmail());
            return userMapper.mapToDTO(savedUser);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            throw new UserServiceException("Error creating user");
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            log.info("Found user with ID: {}",id);
            return userMapper.mapToDTO(user);
        } catch (UserNotFoundException e) {
            log.warn("User with ID: {} - NOT FOUND", id);
            throw e;
        } catch (Exception e) {
            log.error("Error getting user by ID: {}", e.getMessage());
            throw new UserServiceException("Error getting user by ID");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            log.info("Getting all users with the following ID`s: {}",
                    users.stream().map(User::getId).collect(Collectors.toList()));
            return userMapper.listToDTOS(users);
        } catch (Exception e) {
            log.error("Error getting all users: {}", e.getMessage());
            throw new UserServiceException("Error getting all users");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            log.info("Deleted user with ID: {}",id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("User with ID: {} - NOT FOUND", id);
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            throw new UserServiceException("Error deleting user");
        }
    }
}
