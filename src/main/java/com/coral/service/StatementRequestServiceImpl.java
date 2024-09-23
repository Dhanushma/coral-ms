package com.coral.service;

import com.coral.dto.StatementRequestDTO;
import com.coral.dto.TransactionDTO;
import com.coral.entity.StatementRequest;
import com.coral.repository.StatementRequestRepository;
import com.coral.utils.StatementRequestConstants;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.coral.utils.StatementRequestConstants.*;

@Service
@Slf4j
public class StatementRequestServiceImpl implements StatementRequestService {

    @Autowired
    private StatementRequestRepository statementRequestRepository;

    @Autowired
    private StatementProcessorService statementProcessorService;

    @Autowired
    private EmailService emailService;

    @Override
    public StatementRequest saveStatementRequest(StatementRequestDTO statementRequestDto) {
        StatementRequest statementRequest = statementRequestDto.toStatementRequest();
        statementRequest.setStatus(StatementRequestConstants.SCHEDULED);
        try {
            return statementRequestRepository.save(statementRequest);
        } catch (Exception e) {
            log.error("Error occurred while saving StatementRequest {}", e.getMessage());
            throw new RuntimeException("Failed to save StatementRequest", e);
        }
    }

    @Override
    public void processStatementRequestEvent(Long id){
        Optional<StatementRequest> optionalStatementRequest = statementRequestRepository.findById(id);
        if (optionalStatementRequest.isPresent() && SCHEDULED.equals(optionalStatementRequest.get().getStatus())) {
            StatementRequest statementRequest = optionalStatementRequest.get();

            generateStatementForRequest(statementRequest)
                    .doOnSuccess(transactions -> {
                        log.info("Transactions received from external core banking api");
                        statementRequest.setStatus(COMPLETED);
                        statementRequestRepository.save(statementRequest);
                        emailService.generateEmailStatement(transactions, statementRequest.getEmailId());

                    }).doOnError(error -> {
                        statementRequest.setStatus(FAILED);
                        statementRequestRepository.save(statementRequest);
                        log.error("Error generating statement: {}", error.getMessage(), error);
                    }).subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }

    private Single<List<TransactionDTO>> generateStatementForRequest(StatementRequest statementRequest) {
        log.info("Current Thread {}", Thread.currentThread().getName() );
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e){
            log.error(e.getMessage());
        }*/
        return statementProcessorService.generateAccountStatement(
                statementRequest.getAccountNumber(),
                statementRequest.getFromDate().toString(),
                statementRequest.getToDate().toString());
    }

}
