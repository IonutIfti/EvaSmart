package com.ii.onlinemarket.evasmart.user.services;

import com.ii.onlinemarket.evasmart.user.payload.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    void deleteUserById(Long id);
}
