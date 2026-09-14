package com.example.financialbank.controller;

import com.example.financialbank.dto.*;
import com.example.financialbank.model.User;
import com.example.financialbank.service.ExpenseService;
import com.example.financialbank.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public FinanceController(IncomeService incomeService, ExpenseService expenseService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    @GetMapping("/income")
    public Map<String, BigDecimal> getIncome(@AuthenticationPrincipal User user) {
        return Map.of("averageIncome", incomeService.getIncome(user));
    }

    @PutMapping("/income")
    public Map<String, BigDecimal> updateIncome(@AuthenticationPrincipal User user, @Valid @RequestBody UpdateIncomeDTO dto) {
        return Map.of("averageIncome", incomeService.updateIncome(user, dto));
    }

    @PostMapping("/expenses")
    public ExpenseView addExpense(@AuthenticationPrincipal User user, @Valid @RequestBody CreateExpenseDTO dto) {
        return expenseService.addExpense(user, dto);
    }

    @GetMapping("/expenses")
    public List<ExpenseView> getExpenses(@AuthenticationPrincipal User user) {
        return expenseService.getHistory(user);
    }

    @GetMapping("/expenses/not-worth-it")
    public List<ExpenseView> getNotWorthIt(@AuthenticationPrincipal User user) {
        return expenseService.getNotWorthIt(user);
    }

    @GetMapping("/summary")
    public PersonalFinanceSummaryView getSummary(@AuthenticationPrincipal User user) {
        return expenseService.getSummary(user);
    }
}
