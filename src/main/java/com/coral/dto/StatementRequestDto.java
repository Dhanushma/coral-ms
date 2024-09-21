package com.coral.dto;

import com.coral.entity.StatementRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(
        name="StatementRequest",
        description = "Request Account Statement"
)
public class StatementRequestDto {

    @Schema(
            description = "Account Number", example = "1234567890"
    )
    @NotBlank(message = "Account number must not be empty")
    @Pattern(regexp = "\\d{10}", message = "Account number must be exactly 10 digits")
    private String accountNumber;

    @Schema(
            description = "Start date", example = "2024-08-20"
    )
    @NotNull(message = "From date must not be empty")
    private LocalDate fromDate;

    @Schema(
            description = "End date", example = "2024-09-20"
    )
    @NotNull(message = "To date must not be empty")
    private LocalDate toDate;

    public StatementRequest toStatementRequest() {
        return StatementRequest.builder()
                .accountNumber(accountNumber)
                .fromDate(fromDate)
                .toDate(fromDate)
                .build();
    }
}
