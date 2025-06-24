package br.com.feliperochasi.luminabank.controller;

import br.com.feliperochasi.luminabank.dto.BankMovementDTO;
import br.com.feliperochasi.luminabank.dto.DetailsBankStatementDTO;
import br.com.feliperochasi.luminabank.service.BankStatementService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/bank-statement")
public class BankStatementController {

    @Autowired
    private BankStatementService bankStatementService;

    @GetMapping("/{numberAccount}")
    public ResponseEntity<List<DetailsBankStatementDTO>> listBankStatementByAccountNumber(@PathVariable Long numberAccount) {
        return ResponseEntity.ok(this.bankStatementService.listBankStatementByAccountNumber(numberAccount));
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<DetailsBankStatementDTO> findTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(this.bankStatementService.findTransactionById(id));
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<DetailsBankStatementDTO> applyNewBankingMovement(@RequestBody @Valid BankMovementDTO dto, UriComponentsBuilder uriBuilder) {
        var registredBankStatement = this.bankStatementService.applyNewBankingMovement(dto);
        var uri = uriBuilder.path("/bank-statement/transaction/{id}").buildAndExpand(registredBankStatement.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailsBankStatementDTO(registredBankStatement));
    }
}
