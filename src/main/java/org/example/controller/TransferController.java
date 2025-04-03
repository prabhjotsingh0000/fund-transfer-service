package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.TransferRequestDTO;
import org.example.dto.TransferResponseDTO;
import org.example.dto.TransferSummaryDTO;
import org.example.service.TransferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    /**
     * Create a new fund transfer request.
     */
    @PostMapping
    public ResponseEntity<TransferResponseDTO> createTransfer(@Valid @RequestBody TransferRequestDTO requestDTO) {
        TransferResponseDTO response = transferService.createTransfer(requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve details of a specific transfer by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransferResponseDTO> getTransferById(@PathVariable Long id) {
        TransferResponseDTO response = transferService.getTransferById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all transfers for a specific user with pagination.
     */
    @GetMapping("/user/{accountNumber}")
    public ResponseEntity<Page<TransferSummaryDTO>> getTransfersForUser(
            @PathVariable String accountNumber, Pageable pageable) {
        Page<TransferSummaryDTO> response = transferService.getTransfersForUser(accountNumber, pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancel a transfer if it's still pending.
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<TransferResponseDTO> cancelTransfer(@PathVariable Long id) {
        TransferResponseDTO response = transferService.cancelTransfer(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Complete a transfer if it's still pending
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<TransferResponseDTO> completeTransfer(@PathVariable Long id) {
        TransferResponseDTO response = transferService.completeTransfer(id);
        return ResponseEntity.ok(response);
    }
}
