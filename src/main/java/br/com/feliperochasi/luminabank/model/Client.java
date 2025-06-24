package br.com.feliperochasi.luminabank.model;

import br.com.feliperochasi.luminabank.dto.ClientRegisterDTO;
import br.com.feliperochasi.luminabank.dto.ClientUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String cpf;

    private String phone;

    private LocalDate birthday;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private LocalDateTime deleted_at;

    private int active;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();;

    public Client(ClientRegisterDTO dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.cpf = dto.cpf();
        this.phone = dto.phone();
        this.birthday = dto.birthday();
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
        this.active = 1;
    }

    public void updateInfoClient(ClientUpdateDTO dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }

        if (dto.email() != null) {
            this.email = dto.email();
        }

        if (dto.phone() != null) {
            this.phone = dto.phone();
        }

        this.updated_at = LocalDateTime.now();
    }

    public void inativeClient() {
        this.active = 0;
        this.deleted_at = LocalDateTime.now();
    }
}
