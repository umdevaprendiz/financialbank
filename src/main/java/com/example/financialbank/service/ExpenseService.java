package com.example.financialbank.service;

import com.example.financialbank.dto.CreateExpenseDTO;
import com.example.financialbank.dto.ExpenseView;
import com.example.financialbank.dto.PersonalFinanceSummaryView;
import com.example.financialbank.dto.SpendingByMethodView;
import com.example.financialbank.model.ExpenseEntry;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.ExpenseEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ExpenseService {

    @Autowired
    private ExpenseEntryRepository expenseRepository;
    @Autowired
    private IncomeService incomeService;


    @Transactional
    public ExpenseView addExpense(User user, CreateExpenseDTO dto) {
        ExpenseEntry entry = new ExpenseEntry();
        entry.setUser(user);
        entry.setAmount(dto.amount());
        entry.setPaymentMethod(dto.paymentMethod());
        entry.setDescription(dto.description());
        entry.setWorthIt(dto.worthIt());
        entry.setReason(dto.reason());
        return toView(expenseRepository.save(entry));
    }

    public List<ExpenseView> getHistory(User user) {
        return expenseRepository.findByUserOrderByDateCreationDesc(user).stream()
            .map(this::toView)
            .toList();
    }

    public List<ExpenseView> getNotWorthIt(User user) {
        return expenseRepository.findByUserAndWorthItFalseOrderByDateCreationDesc(user).stream()
            .map(this::toView)
            .toList();
    }

    public PersonalFinanceSummaryView getSummary(User user) {
        BigDecimal income = incomeService.getIncome(user);
        YearMonth currentMonth = YearMonth.now();
        YearMonth previousMonth = currentMonth.minusMonths(1);

        List<ExpenseEntry> currentMonthExpenses = expenseRepository.findByUserAndDateCreationBetween(
            user, currentMonth.atDay(1).atStartOfDay(), LocalDateTime.now());
        List<ExpenseEntry> previousMonthExpenses = expenseRepository.findByUserAndDateCreationBetween(
            user, previousMonth.atDay(1).atStartOfDay(), previousMonth.atEndOfMonth().atTime(23, 59, 59));

        BigDecimal totalThisMonth = sum(currentMonthExpenses);
        BigDecimal totalPreviousMonth = sum(previousMonthExpenses);
        BigDecimal remaining = income.subtract(totalThisMonth);

        BigDecimal percentOfIncomeSpent = income.compareTo(BigDecimal.ZERO) == 0
            ? BigDecimal.ZERO
            : totalThisMonth.divide(income, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        BigDecimal percentChange = totalPreviousMonth.compareTo(BigDecimal.ZERO) == 0
            ? BigDecimal.ZERO
            : totalThisMonth.subtract(totalPreviousMonth)
                .divide(totalPreviousMonth, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        List<SpendingByMethodView> byMethod = currentMonthExpenses.stream()
            .collect(Collectors.groupingBy(ExpenseEntry::getPaymentMethod))
            .entrySet().stream()
            .map(e -> new SpendingByMethodView(e.getKey(), sum(e.getValue()), e.getValue().size()))
            .sorted(Comparator.comparing(SpendingByMethodView::total).reversed())
            .toList();

        return new PersonalFinanceSummaryView(income, totalThisMonth, remaining, percentOfIncomeSpent,
            byMethod, totalPreviousMonth, percentChange);
    }

    private BigDecimal sum(List<ExpenseEntry> entries) {
        return entries.stream().map(ExpenseEntry::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ExpenseView toView(ExpenseEntry e) {
        return new ExpenseView(e.getId(), e.getAmount(), e.getPaymentMethod(), e.getDescription(),
            e.getWorthIt(), e.getReason(), e.getDateCreation());
    }
}
