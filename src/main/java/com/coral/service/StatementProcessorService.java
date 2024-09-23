package com.coral.service;

import com.coral.dto.TransactionDTO;
import com.coral.dto.TransactionResponseDTO;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Setter
public class StatementProcessorService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${core-banking.api.base-url}")
    String bankAPIUrl;

    @Value("${core-banking.api.transactions-path}")
    String path;

    public Single<List<TransactionDTO>> generateAccountStatement(String accountNumber, String fromDate, String toDate) {
        return fetchAndAccumulateTransactions(accountNumber, fromDate, toDate, 0)
                .subscribeOn(Schedulers.io());
    }

    private Single<List<TransactionDTO>> fetchAndAccumulateTransactions(String accountNumber, String fromDate, String toDate, int currentPage) {
        return fetchTransaction(accountNumber, fromDate, toDate, currentPage)
                .flatMap(response -> {
                    List<TransactionDTO> currentTransactions = response.getTransactionDTOList();
                    if (response.isLastPage()) {
                        return Single.just(currentTransactions);
                    } else {
                        return fetchAndAccumulateTransactions(accountNumber, fromDate, toDate, currentPage + 1)
                                .map(nextTransactions -> {
                                    List<TransactionDTO> combinedTransactions = new ArrayList<>(currentTransactions);
                                    combinedTransactions.addAll(nextTransactions);
                                    return combinedTransactions;
                                });
                    }
                });
    }

    private Single<TransactionResponseDTO> fetchTransaction(String accountNumber, String fromDate, String toDate, int page) {
        return Single.fromCallable(() -> {
            String coreBankApiUrl = createCoreBankApiUrl(accountNumber, fromDate, toDate, page);
            return restTemplate.getForObject(coreBankApiUrl, TransactionResponseDTO.class);
        }).subscribeOn(Schedulers.io());
    }

    private String createCoreBankApiUrl(String accountNumber, String fromDate, String toDate, int page) {
        return String.format("%s%s?accountNumber=%s&fromDate=%s&toDate=%s&page=%d",
                bankAPIUrl, path, accountNumber, fromDate, toDate, page);
    }

}
