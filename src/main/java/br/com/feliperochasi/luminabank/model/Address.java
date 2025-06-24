package br.com.feliperochasi.luminabank.model;


import br.com.feliperochasi.luminabank.dto.AddressRegisterDTO;
import br.com.feliperochasi.luminabank.dto.AddressUpdateClient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String neighborhood;

    private String zipcode;

    private String complement;

    private String number;

    private String uf;

    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Address(AddressRegisterDTO dto, Client clientForAddress) {
        this.address = dto.address();
        this.neighborhood = dto.neighborhood();
        this.zipcode = dto.zipCode();
        this.complement = dto.complement().isEmpty() ? "" : dto.complement();
        this.number = dto.number().isEmpty() ? "" : dto.number();
        this.uf = dto.uf();
        this.city = dto.city();
        this.client = clientForAddress;
    }

    public void updateInfoAddress(AddressUpdateClient dto) {
        if (dto.address() != null) {
            this.address = dto.address();
        }
        if (dto.neighborhood() != null) {
            this.neighborhood = dto.neighborhood();
        }
        if (dto.zipCode() != null) {
            this.zipcode = dto.zipCode();
        }
        if (dto.complement() != null) {
            this.complement = dto.complement();
        }
        if (dto.number() != null) {
            this.number = dto.number();
        }
        if (dto.uf() != null) {
            this.uf = dto.uf();
        }
        if (dto.city() != null) {
            this.city = dto.city();
        }
    }
}
