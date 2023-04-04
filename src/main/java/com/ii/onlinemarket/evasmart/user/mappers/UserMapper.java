package com.ii.onlinemarket.evasmart.user.mappers;

import com.ii.onlinemarket.evasmart.user.models.User;
import com.ii.onlinemarket.evasmart.user.payload.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    UserDTO mapToDTO(User user);
    User mapToEntity(UserDTO userDTO);

    List<UserDTO> listToDTOS(List<User> users);
}
