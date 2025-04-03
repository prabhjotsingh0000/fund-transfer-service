package org.example.dto;

import lombok.*;
import org.example.model.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
public class TransferResponseDTO {

    private Long id;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String description;
    private TransferStatus status;
    private LocalDateTime createdAt;
}
