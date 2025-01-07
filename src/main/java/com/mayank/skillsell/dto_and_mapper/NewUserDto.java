package com.mayank.skillsell.dto_and_mapper;

import java.util.Set;

public record NewUserDto(
        String username,
        String password,
        String email,
        String phoneNo,
        Set<String> roles
) { }
