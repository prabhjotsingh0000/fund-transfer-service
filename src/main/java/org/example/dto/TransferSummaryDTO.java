package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransferSummaryDTO {

    private Long id;
    private BigDecimal amount;
    private TransferStatus status;
    private LocalDateTime createdAt;
}
