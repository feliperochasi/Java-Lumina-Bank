package br.com.feliperochasi.luminabank.service;

import br.com.feliperochasi.luminabank.dto.AccountRegisterDTO;
import br.com.feliperochasi.luminabank.dto.DetailsAccountDTO;
import br.com.feliperochasi.luminabank.model.Account;
import br.com.feliperochasi.luminabank.model.Client;
import br.com.feliperochasi.luminabank.repository.AccountRepository;
import br.com.feliperochasi.luminabank.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService {

    private static final Integer NUMBER_CHARACTERS_ACCOUNT = 11;
    private static final Integer NUMBERS_TO_GET = 9;


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    public DetailsAccountDTO findAccountByNumber(Long number) {
        return  new DetailsAccountDTO(accountRepository.findByNumberEquals(number));
    }

    public Account createNewAccountForClient(AccountRegisterDTO dto) {
        Client clientForRegisterNewAccount = clientRepository.getReferenceById(dto.clientId());

        Long number;
        Integer digit;
        do {
            number = generateAccountNumber();
            digit = generateAccountDigit();
        } while (accountRepository.existsByNumberAndDigit(number, digit));

        Account newAccount = new Account(clientForRegisterNewAccount, dto.typeAccount(), number, digit);
        accountRepository.save(newAccount);
        return newAccount;
    }

    public void approveAccountClient(Long id) {
        Account accountForApprove = accountRepository.getReferenceById(id);
        accountForApprove.approveAccount();
    }

    public void inativeAccountClient(Long id) {
        Account accountForInative = accountRepository.getReferenceById(id);
        accountForInative.inativeAccount();
    }

    private Long generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        sb.append(random.nextInt(9) + 1);
        for (int i = 0; i < NUMBER_CHARACTERS_ACCOUNT; i++) {
            sb.append(random.nextInt(NUMBERS_TO_GET));
        }

        return Long.valueOf(sb.toString());
    }

    private Integer generateAccountDigit() {
        Random random = new Random();
        return random.nextInt((NUMBERS_TO_GET) + 1);
    }
}
