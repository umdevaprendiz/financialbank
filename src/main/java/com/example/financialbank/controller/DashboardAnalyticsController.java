package com.example.financialbank.controller;

import com.example.financialbank.dto.AccountBalanceView;
import com.example.financialbank.dto.TransactionSummaryView;
import com.example.financialbank.model.User;
import com.example.financialbank.service.DashboardAnalyticsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardAnalyticsController {

    private final DashboardAnalyticsService service;

    public DashboardAnalyticsController(DashboardAnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/balances")
    public List<AccountBalanceView> balances(@AuthenticationPrincipal User user) {
        return service.getAccountBalances(user);
    }

    @GetMapping("/transactions")
    public List<TransactionSummaryView> transactions(@AuthenticationPrincipal User user,
                                                       @RequestParam(defaultValue = "30") int days,
                                                       @RequestParam(defaultValue = "20") int limit) {
        return service.getTransactionsSummary(user, days, limit);
    }
}
