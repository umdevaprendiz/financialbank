package com.example.financialbank.repository;

import com.example.financialbank.dto.TransactionTypeSummaryDTO;
import com.example.financialbank.enums.TransactionStatus;
import com.example.financialbank.enums.TransactionType;
import com.example.financialbank.model.Account;
import com.example.financialbank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    // últimas transações (pro dashboard)
    List<Transaction> findTop10ByOrderByDateCreationDesc();

    // transações de uma conta específica
    List<Transaction> findByAccountOrigin(Account account);
    List<Transaction> findByAccountDestination(Account account);

    // contar transações do dia (pro dashboard)
    @Query("SELECT COUNT(t) FROM Transaction t WHERE DATE(t.dateCreation) = CURRENT_DATE")
    Long countToday();

    // volume financeiro do dia (pro dashboard)
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE DATE(t.dateCreation) = CURRENT_DATE AND t.status = 'APROVADA'")
    BigDecimal sumAmountToday();

    // agrupar por tipo no mês atual (pro dashboard)
    @Query("SELECT new com.example.financialbank.dto.TransactionTypeSummaryDTO(t.type, COUNT(t)) FROM Transaction t WHERE MONTH(t.dateCreation) = MONTH(CURRENT_DATE) GROUP BY t.type")
    List<TransactionTypeSummaryDTO> countGroupByType();

    // filtros para tela de listagem do admin
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByDateCreationBetween(LocalDateTime start, LocalDateTime end);
}
