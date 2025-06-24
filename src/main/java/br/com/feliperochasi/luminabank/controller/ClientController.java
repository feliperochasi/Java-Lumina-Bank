package br.com.feliperochasi.luminabank.controller;

import br.com.feliperochasi.luminabank.dto.*;
import br.com.feliperochasi.luminabank.service.ClientService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<DetailsClientDTO>> findAllClients() {
        return ResponseEntity.ok(this.clientService.findAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailsClientDTO> findClientById(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.findClientById(id));
    }

    @GetMapping("/{idClient}/address/{idAddress}")
    public ResponseEntity<DetailsAddressDTO> findAddressById(@PathVariable Long idClient, @PathVariable Long idAddress) {
        return ResponseEntity.ok(this.clientService.findAddressById(idAddress));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DetailsClientDTO> createClient(@RequestBody @Valid ClientRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        var newClientCreated = this.clientService.createClient(dto);
        var uri = uriBuilder.path("/clients/{id}").buildAndExpand(newClientCreated.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailsClientDTO(newClientCreated));
    }

    @PostMapping("/address")
    @Transactional
    public ResponseEntity<DetailsAddressDTO> createAddressForClient(@RequestBody @Valid AddressRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        var newAddresssCreated = this.clientService.createAddressForClient(dto);
        var uri = uriBuilder.path("/clients/{idClient}/address/{idAddress}").buildAndExpand(dto.clientId(), newAddresssCreated.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetailsAddressDTO(newAddresssCreated));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DetailsClientDTO> updateClient(@RequestBody @Valid ClientUpdateDTO dto) {
        return ResponseEntity.ok(this.clientService.updateClient(dto));
    }

    @PutMapping("/address")
    @Transactional
    public ResponseEntity<DetailsAddressDTO> updateAddressOfClient(@RequestBody @Valid AddressUpdateClient dto) {
        return ResponseEntity.ok(this.clientService.updateAddressOfClient(dto));
    }

    @DeleteMapping("/address/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAddressOfClient(@PathVariable Long id){
        this.clientService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> inativeClient(@PathVariable Long id){
        this.clientService.inativeClient(id);
        return ResponseEntity.noContent().build();
    }
}
