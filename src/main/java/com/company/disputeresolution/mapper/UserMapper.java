package com.company.disputeresolution.mapper;

import com.company.disputeresolution.dto.response.UserDTO;
import com.company.disputeresolution.entity.User;

public final class UserMapper {
    private UserMapper() {}

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .accountStatus(user.getAccountStatus())
                .build();
    }
}
