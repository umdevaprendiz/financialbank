package com.example.financialbank.service;

import com.example.financialbank.configuration.TransactionType;
import com.example.financialbank.model.Account;
import com.example.financialbank.model.Transaction;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.AccountRepository;
import com.example.financialbank.repository.TransactionRepository;
import com.example.financialbank.repository.UserRepository;
import jakarta.persistence.GeneratedValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    //criar conta
    //buscar contas do usuário
    //depositar dinheiro
    //sacar dinheiro
    //transferir dinheiro
    private final AccountRepository accountRepository;
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    //criação da conta,

    public Account createAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado! Faça seu cadastro!"));

        //para criar uma nova conta.
        Account account = new Account();
        account.setUsuario(user);
        account.setBalance(BigDecimal.ZERO);
        account.setDateCreation(LocalDateTime.now());
        account.setNumberAccount(UUID.randomUUID().toString().substring(0, 8));
        return accountRepository.save(account);
    }

    //Ato de colocar dinheiro ou ativos financeiros em uma conta bancária para aumentar o saldo, ou pagar uma divida que
    //será cobrada se saldo estiver negativo.

    //validando o depósito.
    @Transactional
    public Account deposit(Long accountId, BigDecimal valor) {
        // 1. Validação básica de entrada
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor de depósito inválido");
        }

        // 2. Busca a conta (usando o método auxiliar que você já tem)
        Account account = getAccountById(accountId);

        // 3. Atualiza o saldo logicamente
        account.setBalance(account.getBalance().add(valor));

        // 4. Salva a alteração no banco (Apenas UMA vez)
        Account accountAtualizada = accountRepository.save(account);

        // 5. Registra o histórico da transação
        registrarTransacao(accountAtualizada, valor, TransactionType.DEPOSIT, accountAtualizada.getBalance());

        // 6. Retorna a conta atualizada
        return accountAtualizada;
    }

    //O extrato bancário é um resumo detalhado de todas as movimentações (entradas e saídas)
    // de uma conta corrente ou poupança em um período definido. Ele registra depósitos, saques, transferências (TED/DOC),
    // pagamentos e o saldo final.
    // Pode ser consultado em aplicativos, internet banking ou caixas eletrônicos.

    public void registrarTransacao(Account account,
                                   BigDecimal value,
                                   TransactionType type,
                                   BigDecimal currentBalance) {

        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setValue(value);
        transaction.setTransactionType(type);
        transaction.setCurrentBalance(currentBalance);

        transactionRepository.save(transaction);
    }

    private List<Transaction> getStatement(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        return transactionRepository
                .findByAccountOrderByDateDesc(account);
    }

    // 1. Verifica se a conta existe
    // 2. Opcional: Impedir deleção se houver saldo (Regra de negócio)
    // 3. Deleta a conta
    public void delete(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada para exclusão"));

        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Não é possível deletar uma conta com saldo positivo.");
        }
        accountRepository.delete(account);
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public Account saque(Long accountId, BigDecimal saque) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        //Verifica se o saque é menor ou igual a 0, e leva uma exceção
        if (saque == null || saque.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O valor do saque não pode ser menor ou igual à 0!");
        }
        account.setBalance(account.getBalance().subtract(saque));
         return accountRepository.save(account);

    }
}
