package com.company.disputeresolution.dto.response;

import com.company.disputeresolution.entity.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private UserRole role;
    private String accountStatus;
}
