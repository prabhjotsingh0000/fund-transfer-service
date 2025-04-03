package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.TransferRequestDTO;
import org.example.dto.TransferResponseDTO;
import org.example.model.TransferStatus;
import org.example.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class TransferControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController transferController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    }

    @Test
    void createTransfer() throws Exception {
        TransferRequestDTO requestDTO = new TransferRequestDTO("123456789", "987654321", BigDecimal.valueOf(100.50), "Payment for invoice #123");
        TransferResponseDTO responseDTO = new TransferResponseDTO(
                1L,
                "123456789",
                "987654321",
                BigDecimal.valueOf(100.50),
                "Payment for invoice #123",
                TransferStatus.PENDING,
                LocalDateTime.now()
        );
        when(transferService.createTransfer(any(TransferRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromAccount").value("123456789"))
                .andExpect(jsonPath("$.toAccount").value("987654321"))
                .andExpect(jsonPath("$.amount").value(100.50))
                .andExpect(jsonPath("$.description").value("Payment for invoice #123"));
    }

    @Test
    void testGetTransferById() throws Exception {
        Long transferId = 1L;

        TransferResponseDTO transferResponseDTO = new TransferResponseDTO(
                1L,
                "123456789",
                "987654321",
                BigDecimal.valueOf(100.50),
                "Payment for invoice #123",
                TransferStatus.PENDING,
                LocalDateTime.now()
        );

        when(transferService.getTransferById(transferId)).thenReturn(transferResponseDTO);

        mockMvc.perform(get("/api/transfer/{id}", transferId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fromAccount").value("123456789"))
                .andExpect(jsonPath("$.toAccount").value("987654321"))
                .andExpect(jsonPath("$.amount").value(100.50))
                .andExpect(jsonPath("$.description").value("Payment for invoice #123"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());

        verify(transferService, times(1)).getTransferById(transferId);
    }

}
