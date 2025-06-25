package br.com.feliperochasi.luminabank.service;

import br.com.feliperochasi.luminabank.dto.BankMovementDTO;
import br.com.feliperochasi.luminabank.dto.DetailsBankStatementDTO;
import br.com.feliperochasi.luminabank.infra.ValidationException;
import br.com.feliperochasi.luminabank.model.Account;
import br.com.feliperochasi.luminabank.model.BankStatement;
import br.com.feliperochasi.luminabank.model.TransactionType;
import br.com.feliperochasi.luminabank.repository.AccountRepository;
import br.com.feliperochasi.luminabank.repository.BankStatementRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankStatementService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankStatementRepository bankStatementRepository;

    public DetailsBankStatementDTO findTransactionById(Long id) {
        return new DetailsBankStatementDTO(bankStatementRepository.getReferenceById(id));
    }

    public BankStatement applyNewBankingMovement(@Valid BankMovementDTO dto) {
        Account accountClientOperator = findAccountByNumber(dto.originAccount());
        switch (dto.transactionType()) {
            case PAY -> accountClientOperator.pay(dto);
            case DEPOSIT -> accountClientOperator.deposit(dto);
            case TRANSFER -> {
                Account accountClientReciver = findAccountByNumber(dto.reciverNumber());
                accountClientOperator.transfer(dto);
                accountClientReciver.deposit(dto);
                BankStatement operationReciver = new BankStatement(accountClientReciver, dto.transactionType(), "Transferencia recebida pelo sistema de conta interna", "Transferencia", dto.amount());
                bankStatementRepository.save(operationReciver);
            }
            case WITHDRAWAL -> accountClientOperator.withdrawal(dto);
        }
        var amountToOperator = dto.transactionType() ==  TransactionType.DEPOSIT ? dto.amount() : dto.amount() * -1;
        BankStatement newOperation = new BankStatement(accountClientOperator, dto.transactionType(), dto.description(), dto.reference(), amountToOperator);
        bankStatementRepository.save(newOperation);
        return newOperation;
    }

    public List<DetailsBankStatementDTO> listBankStatementByAccountNumber(Long numberAccount) {
        Account accountToListBankStatement = findAccountByNumber(numberAccount);
        List<BankStatement> bankStatementOfAccount = bankStatementRepository.findByAccountEquals(accountToListBankStatement);
        return bankStatementOfAccount.stream().map(DetailsBankStatementDTO::new).toList();
    }

    private Account findAccountByNumber(Long number) {
        return accountRepository.findByNumberEquals(number);
    }
}
