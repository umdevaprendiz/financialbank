package com.example.financialbank.service;

import com.example.financialbank.configuration.TransactionType;
import com.example.financialbank.model.Account;
import com.example.financialbank.model.Transaction;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.AccountRepository;
import com.example.financialbank.repository.TransactionRepository;
import com.example.financialbank.repository.UserRepository;
import jakarta.persistence.GeneratedValue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

        //A expressão UUID.randomUUID().toString().substring(...)
        // em Java é usada para gerar um identificador único,
        // convertê-lo para texto e depois recortar uma parte específica dessa String
        account.setNumberAccount(UUID.randomUUID().toString().substring(0, 8));

        return accountRepository.save(account);
    }

    //Ato de colocar dinheiro ou ativos financeiros em uma conta bancária para aumentar o saldo, ou pagar uma divida que
    //será cobrada se saldo estiver negativo.

    //validando o depósito.
    public Account deposit(Long accountId, BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor de depósito inválido");
            //comparando o valor a 0.
        }

        //verifica se a conta existe.
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        //cria um objeto do tipo big decimal chamado saldo atual.
        BigDecimal saldoAtual = account.getBalance();

        //cria um objeto chamado novo saldo, que soma o saldo atual com o valor.
        BigDecimal novoSaldo = saldoAtual.add(valor);

        //salva o novo saldo.
        account.setBalance(novoSaldo);

        return accountRepository.save(account);

    }

    //O extrato bancário é um resumo detalhado de todas as movimentações (entradas e saídas)
    // de uma conta corrente ou poupança em um período definido. Ele registra depósitos, saques, transferências (TED/DOC),
    // pagamentos e o saldo final.
    // Pode ser consultado em aplicativos, internet banking ou caixas eletrônicos.

    private void registrarTransacao(Account account,
                                    BigDecimal value,
                                    TransactionType type,
                                    BigDecimal currentBalance){

        Transaction transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setValue(value);
        transaction.setTransactionType(type);
        transaction.setCurrentBalance(currentBalance);

        transactionRepository.save(transaction);
    }

    private List<Transaction> getStatement(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new RuntimeException("Conta não encontrada!"));

        return transactionRepository
                .findByAccountOrderByDateDesc(account);
    }
}
