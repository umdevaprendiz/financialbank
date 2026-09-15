package com.example.financialbank.service;

import com.example.financialbank.dto.AccountBalanceView;
import com.example.financialbank.dto.TransactionSummaryView;
import com.example.financialbank.model.Transaction;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class DashboardAnalyticsService {

    @Autowired
    private TransactionRepository transactionRepository;


    public List<AccountBalanceView> getAccountBalances(User user) {
        return user.getContas().stream()
            .map(a -> new AccountBalanceView(a.getNumberAccount(), a.getBalance(), a.getStatus().name()))
            .toList();
    }

    public List<TransactionSummaryView> getTransactionsSummary(User user, int lastNDays, int limit) {
        LocalDateTime since = LocalDateTime.now().minusDays(lastNDays);
        return user.getContas().stream()
            .flatMap(account -> Stream.concat(
                transactionRepository.findByAccountOrigin(account).stream().map(t -> toView(t, "SAIDA")),
                transactionRepository.findByAccountDestination(account).stream().map(t -> toView(t, "ENTRADA"))
            ))
            .filter(v -> v.dateCreation().isAfter(since))
            .sorted(Comparator.comparing(TransactionSummaryView::dateCreation).reversed())
            .limit(limit)
            .toList();
    }

    private TransactionSummaryView toView(Transaction t, String direction) {
        return new TransactionSummaryView(
            t.getTrackingCode(), t.getType().name(), t.getAmount(), direction,
            t.getDescription(), t.getDateCreation());
    }
}
