package com.coral.service;

import com.coral.dto.TransactionDTO;
import com.coral.dto.TransactionResponseDTO;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Setter
public class StatementGeneratorService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${core-banking.api.base-url}")
    String bankAPIUrl;

    @Value("${core-banking.api.transactions-path}")
    String path;

    public List<TransactionDTO> generateAccountStatement(String accountNumber, String fromDate, String toDate) {

        List<TransactionDTO> accntTransactionList = new ArrayList<>();
        boolean isLastPage = false;
        int page = 0;

        while (!isLastPage) {
            String url = String.format("%s%s?accountNumber=%s&fromDate=%s&toDate=%s&page=%d",
                    bankAPIUrl, path, accountNumber, fromDate, toDate, page);

            TransactionResponseDTO transactionResponseDTO =
                    restTemplate.getForObject(url, TransactionResponseDTO.class);

            if (transactionResponseDTO != null) {
                accntTransactionList.addAll(transactionResponseDTO.getTransactionDTOList());
                isLastPage = transactionResponseDTO.isLastPage();
            }
            page++;
        }
        return accntTransactionList;

    }
}
