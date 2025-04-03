package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.TransferRequestDTO;
import org.example.dto.TransferResponseDTO;
import org.example.dto.TransferSummaryDTO;
import org.example.mapper.TransferMapper;
import org.example.model.Transfer;
import org.example.model.TransferStatus;
import org.example.repository.TransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;

    private static final BigDecimal MIN_TRANSFER_AMOUNT = BigDecimal.valueOf(0.01);
    private static final BigDecimal MAX_TRANSFER_AMOUNT = BigDecimal.valueOf(20000);
    private static final int MAX_PENDING_TRANSFERS = 10;

    @Transactional
    public TransferResponseDTO createTransfer(TransferRequestDTO requestDTO) {

        if (requestDTO.getFromAccount().equals(requestDTO.getToAccount())) {
            throw new IllegalArgumentException("From account and To account must be different.");
        }

        // Validate the amount
        if (requestDTO.getAmount().compareTo(MIN_TRANSFER_AMOUNT) <= 0 ||
                requestDTO.getAmount().compareTo(MAX_TRANSFER_AMOUNT) >= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than 0 and less than 20,000.");
        }

        // Check if user already has 10 pending transfers
        long pendingTransfersCount = transferRepository.countByFromAccountAndStatus(
                requestDTO.getFromAccount(), TransferStatus.PENDING);
        if (pendingTransfersCount >= MAX_PENDING_TRANSFERS) {
            throw new IllegalStateException("A user cannot have more than 10 pending transfers.");
        }

        Transfer transfer = transferMapper.toEntity(requestDTO);
        transfer = transferRepository.save(transfer);

        return transferMapper.toResponseDTO(transfer);
    }

    public TransferResponseDTO getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transfer not found"));
        return transferMapper.toResponseDTO(transfer);
    }

    public Page<TransferSummaryDTO> getTransfersForUser(String accountNumber, Pageable pageable) {
        Page<Transfer> transfers = transferRepository.findByFromAccount(accountNumber, pageable);
        return transfers.map(transferMapper::toSummaryDTO);
    }

    @Transactional
    public TransferResponseDTO cancelTransfer(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transfer not found"));

        if (transfer.getStatus() != TransferStatus.PENDING) {
            throw new IllegalStateException("Only pending transfers can be cancelled.");
        }

        transfer.setStatus(TransferStatus.CANCELLED);
        transfer = transferRepository.save(transfer);

        return transferMapper.toResponseDTO(transfer);
    }

    @Transactional
    public TransferResponseDTO completeTransfer(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transfer not found"));

        if (transfer.getStatus() != TransferStatus.PENDING) {
            throw new IllegalStateException("Only pending transfers can be completed.");
        }

        transfer.setStatus(TransferStatus.COMPLETED);
        transfer = transferRepository.save(transfer);

        return transferMapper.toResponseDTO(transfer);
    }
}
