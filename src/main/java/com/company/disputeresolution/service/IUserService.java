package com.company.disputeresolution.service;

import com.company.disputeresolution.entity.User;
import java.util.Optional;

public interface IUserService {
    Optional<User> getUserByUserId(String userId);
}
