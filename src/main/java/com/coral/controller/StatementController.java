package com.coral.controller;

import com.coral.dto.ErrorResponseDTO;
import com.coral.dto.StatementRequestDto;
import com.coral.service.StatementService;
import com.coral.utils.StatementConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "Account Statement Rest APIs",
        description = "Rest APIs for Account Statement"
)
public class StatementController {

    @Autowired
    private StatementService statementService;

    @Operation(
            summary = "Generate account statement",
            description = "Generate Account statement for an account based on fromDate and ToDate"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request"
            )
    })
    @PostMapping("/statements")
    public ResponseEntity<String> generateStatement(@Valid @RequestBody StatementRequestDto statementRequestDto) {
        statementService.generateStatement(statementRequestDto);
        return new ResponseEntity<>(StatementConstants.STATEMENT_REQ_SUBMITTED, HttpStatus.CREATED);
    }
}
