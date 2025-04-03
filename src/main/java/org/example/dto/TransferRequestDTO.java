package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {

    @NotBlank(message = "From account cannot be blank")
    @Size(min = 5, max = 20, message = "From account number must be between 5 and 20 characters")
    private String fromAccount;

    @NotBlank(message = "To account cannot be blank")
    @Size(min = 5, max = 20, message = "To account number must be between 5 and 20 characters")
    private String toAccount;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", inclusive = false, message = "Amount must be greater than zero")
    @DecimalMax(value = "20000", inclusive = false, message = "Amount must be less than 20000")
    @Digits(integer = 5, fraction = 2, message = "Amount must be a valid number with max 5 digits and 2 decimal places")
    private BigDecimal amount;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
