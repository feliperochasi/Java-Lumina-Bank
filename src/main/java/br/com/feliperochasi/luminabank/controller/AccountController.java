package br.com.feliperochasi.luminabank.controller;

import br.com.feliperochasi.luminabank.dto.AccountRegisterDTO;
import br.com.feliperochasi.luminabank.dto.DetailsAccountDTO;
import br.com.feliperochasi.luminabank.service.AccountService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{number}")
    public ResponseEntity<DetailsAccountDTO> findAccountByNumber(@PathVariable Long number) {
        return ResponseEntity.ok(this.accountService.findAccountByNumber(number));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DetailsAccountDTO> createNewAccountForClient(@RequestBody @Valid AccountRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        var accountCreated = this.accountService.createNewAccountForClient(dto);
        var uri = uriBuilder.path("/accounts/{number}").buildAndExpand(accountCreated.getNumber()).toUri();

        return ResponseEntity.created(uri).body(new DetailsAccountDTO(accountCreated));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> approveAccountClient(@PathVariable Long id) {
        this.accountService.approveAccountClient(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> inativeAccountClient(@PathVariable Long id) {
        this.accountService.inativeAccountClient(id);
        return ResponseEntity.noContent().build();
    }
}
