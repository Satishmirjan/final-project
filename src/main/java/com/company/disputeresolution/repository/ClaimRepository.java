package com.company.disputeresolution.repository;

import com.company.disputeresolution.entity.Claim;
import com.company.disputeresolution.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    
    @Query(value = "SELECT * FROM claims WHERE maker_id IS NULL AND lifecycle = 'INITIATED' LIMIT 1 FOR UPDATE SKIP LOCKED", nativeQuery = true)
    Optional<Claim> findNextUnassignedClaim();
}
