package com.ii.onlinemarket.evasmart.user.services;

import com.ii.onlinemarket.evasmart.user.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
