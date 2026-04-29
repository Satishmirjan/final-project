package com.company.disputeresolution.repository;

import com.company.disputeresolution.entity.Dispute;
import com.company.disputeresolution.entity.Transaction;
import com.company.disputeresolution.entity.User;
import com.company.disputeresolution.entity.enums.DisputeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {
    List<Dispute> findByCustomer(User customer);
    Optional<Dispute> findByTransaction(Transaction transaction);
    List<Dispute> findByStatus(DisputeStatus status);
}
