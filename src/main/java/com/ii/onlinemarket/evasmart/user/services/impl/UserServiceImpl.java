package com.ii.onlinemarket.evasmart.user.services.impl;

import com.ii.onlinemarket.evasmart.user.exceptions.UserNotFoundException;
import com.ii.onlinemarket.evasmart.user.mappers.UserMapper;
import com.ii.onlinemarket.evasmart.user.models.User;
import com.ii.onlinemarket.evasmart.user.payload.UserDTO;
import com.ii.onlinemarket.evasmart.user.repositories.UserRepository;
import com.ii.onlinemarket.evasmart.user.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        User user = userMapper.mapToEntity(userDTO);
        User savedUser = userRepository.save(user);
        log.info("Created user with Email: {}",savedUser.getEmail());
        return userMapper.mapToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        log.info("Found user with ID: {}",id);
        return userMapper.mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Getting all users with the following ID`s: {}",
                users.stream().map(User::getId).collect(Collectors.toList()));
        return userMapper.listToDTOS(users);
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            log.info("Deleted user with ID: {}",id);
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.warn("User with ID: {} - NOT FOUND", id);
            throw new UserNotFoundException();
        }
    }
}
