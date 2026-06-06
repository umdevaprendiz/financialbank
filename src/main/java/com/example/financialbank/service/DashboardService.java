package com.example.financialbank.service;

import com.example.financialbank.dto.DashboardDTO;
import com.example.financialbank.enums.AccountStatus;
import com.example.financialbank.enums.SituationEmail;
import com.example.financialbank.repository.AccountRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final TransactionService transactionService;
    private final AccountService accountService;

    //onde criamos o metódo para montar o dashboard. passando
    public DashboardDTO montarDashboard(){
        DashboardDTO dashBoardDTO = new DashboardDTO();
        dashBoardDTO.setTransactionToday(transactionService.countToday());
        dashBoardDTO.setVolumeFinanceToday(transactionService.sumAmountToday());
        dashBoardDTO.setTransactionTypeSummary(transactionService.countGroupByType());
        dashBoardDTO.setAccountActive(accountService.countActive());
        dashBoardDTO.setAccountBlocked(accountService.countBlocked());
        return dashBoardDTO;
    }

}
