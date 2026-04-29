package com.company.disputeresolution.repository;

import com.company.disputeresolution.entity.RequestFingerprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestFingerprintRepository extends JpaRepository<RequestFingerprint, String> {
}
