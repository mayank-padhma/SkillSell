package com.mayank.skillsell.dto_and_mapper;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserDto(
        @NotEmpty(message = "Firstname should not be empty")
        String username,
        @NotEmpty(message = "Email should not be empty")
        String email,
        String phoneNo,
        Set<String>roles,

        Long createdOn,
        Long modifiedOn,
        Boolean isVerified
) { }


