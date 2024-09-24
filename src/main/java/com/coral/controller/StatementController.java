package com.coral.controller;

import com.coral.dto.ErrorResponseDTO;
import com.coral.dto.StatementRequestDTO;
import com.coral.entity.StatementRequest;
import com.coral.event.StatementRequestEvent;
import com.coral.service.StatementRequestService;
import com.coral.utils.StatementRequestConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Statement Rest APIs",
        description = "Rest APIs for Account Statement"
)
@RequestMapping("/coral-ms/api")
@RestController
public class StatementController {

    @Autowired
    private StatementRequestService statementService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

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
    public ResponseEntity<String> generateStatement(@Valid @RequestBody StatementRequestDTO statementRequestDto) {
        StatementRequest statementRequest = statementService.saveStatementRequest(statementRequestDto);
        eventPublisher.publishEvent(new StatementRequestEvent(statementRequest.getId()));
        return new ResponseEntity<>(StatementRequestConstants.STATEMENT_REQ_SUBMITTED, HttpStatus.CREATED);
    }
}
