package org.example.service;

import org.example.dto.TransferRequestDTO;
import org.example.dto.TransferResponseDTO;
import org.example.dto.TransferSummaryDTO;
import org.example.mapper.TransferMapper;
import org.example.model.Transfer;
import org.example.model.TransferStatus;
import org.example.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private TransferMapper transferMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransfer() {
        TransferRequestDTO requestDTO = new TransferRequestDTO("123456789", "987654321", BigDecimal.valueOf(100.50), "Payment for invoice #123");
        Transfer transfer = Transfer.builder()
                .fromAccount("123456789")
                .toAccount("987654321")
                .amount(BigDecimal.valueOf(100.50))
                .description("Payment for invoice #123")
                .status(TransferStatus.PENDING)
                .build();
        TransferResponseDTO responseDTO = new TransferResponseDTO(
                1L,
                "123456789",
                "987654321",
                BigDecimal.valueOf(100.50),
                "Payment for invoice #123",
                TransferStatus.PENDING,
                LocalDateTime.now()
        );
        when(transferMapper.toEntity(any(TransferRequestDTO.class))).thenReturn(transfer);
        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);
        when(transferMapper.toResponseDTO(any(Transfer.class))).thenReturn(responseDTO);

        TransferResponseDTO result = transferService.createTransfer(requestDTO);

        assertNotNull(result);
        assertEquals("123456789", result.getFromAccount());
        assertEquals("987654321", result.getToAccount());
        assertEquals(BigDecimal.valueOf(100.50), result.getAmount());
        assertEquals("Payment for invoice #123", result.getDescription());
    }

    @Test
    void testGetTransfersForUser() {
        String accountNumber = "123456789";
        Pageable pageable = PageRequest.of(0, 10);

        Transfer transfer = new Transfer(
                1L, "123456789", "987654321", BigDecimal.valueOf(100.50), "Payment for invoice #123",
                TransferStatus.PENDING, LocalDateTime.now()
        );

        TransferSummaryDTO transferSummaryDTO = new TransferSummaryDTO(
                1L,
                BigDecimal.valueOf(100.50),
                TransferStatus.PENDING,
                LocalDateTime.now()
        );

        Page<Transfer> transferPage = new PageImpl<>(List.of(transfer));

        when(transferRepository.findByFromAccount(accountNumber, pageable)).thenReturn(transferPage);
        when(transferMapper.toSummaryDTO(transfer)).thenReturn(transferSummaryDTO);

        Page<TransferSummaryDTO> result = transferService.getTransfersForUser(accountNumber, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(BigDecimal.valueOf(100.50), result.getContent().get(0).getAmount());
        assertEquals(TransferStatus.PENDING, result.getContent().get(0).getStatus());
        assertNotNull(result.getContent().get(0).getCreatedAt());


        verify(transferRepository, times(1)).findByFromAccount(accountNumber, pageable);
        verify(transferMapper, times(1)).toSummaryDTO(transfer);
    }
}
