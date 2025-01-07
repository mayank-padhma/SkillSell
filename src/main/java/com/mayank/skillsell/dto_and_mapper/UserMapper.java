package com.mayank.skillsell.dto_and_mapper;

import com.mayank.skillsell.entity.User;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;

@Service
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNo(),
                user.getRoles(),
                user.getCreatedOn().toEpochSecond(ZoneOffset.UTC),
                user.getUpdatedOn().toEpochSecond(ZoneOffset.UTC),
                user.getIsVerified()
        );
    }

    public User toUser(NewUserDto userDto) {
        var user = new User();
        user.setUsername(userDto.username());
        user.setRoles(userDto.roles());
        return user;
    }
}
