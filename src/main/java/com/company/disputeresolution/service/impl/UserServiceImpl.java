package com.company.disputeresolution.service.impl;

import com.company.disputeresolution.entity.User;
import com.company.disputeresolution.repository.UserRepository;
import com.company.disputeresolution.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
