package com.coral.dto;

import com.coral.entity.StatementRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(
        name="StatementRequest",
        description = "Request Account Statement"
)
public class StatementRequestDTO {

    @Schema(
            description = "Account Number", example = "1234567890"
    )
    @NotBlank(message = "Account number must not be blank")
    @Pattern(regexp = "\\d{10}", message = "Account number must be exactly 10 digits")
    private String accountNumber;

    @Schema(
            description = "Start date", example = "2024-08-20"
    )
    @NotBlank(message = "From date must not be blank")
    private String fromDate;

    @Schema(
            description = "End date", example = "2024-09-20"
    )
    @NotBlank(message = "To date must not be blank")
    private String toDate;

    @Schema(
            description = "Email ID", example = "name@gmail.com"
    )
    @Email(message = "Please input a valid email Id")
    @NotBlank(message = "Email Id must not be blank")
    private String emailId;

    public StatementRequest toStatementRequest() {
        return StatementRequest.builder()
                .accountNumber(accountNumber)
                .fromDate(LocalDate.parse(fromDate))
                .toDate(LocalDate.parse(toDate))
                .emailId(emailId)
                .build();
    }
}
