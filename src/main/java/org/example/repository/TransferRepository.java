package org.example.repository;

import org.example.model.Transfer;
import org.example.model.TransferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Page<Transfer> findByFromAccount(String fromAccount, Pageable pageable);

    Optional<Transfer> findById(Long id);

    long countByFromAccountAndStatus(String fromAccount, TransferStatus status);
}
