package br.com.feliperochasi.luminabank.dto;

import br.com.feliperochasi.luminabank.model.Account;
import br.com.feliperochasi.luminabank.model.PlanAccount;
import br.com.feliperochasi.luminabank.model.TypeAccount;
import com.fasterxml.jackson.annotation.JsonAlias;

public record DetailsAccountDTO(
        Long number,
        Integer digit,
        @JsonAlias("type_account")
        TypeAccount typeAccount,
        @JsonAlias("plan_account")
        PlanAccount planAccount
) {
    public DetailsAccountDTO(Account account) {
        this(account.getNumber(), account.getDigit(), account.getType(), account.getPlan());
    }
}
