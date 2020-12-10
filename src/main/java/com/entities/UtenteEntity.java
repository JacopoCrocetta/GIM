package com.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor
@Getter
@Setter
public class UtenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String USERNAME_UTENTE;
    private String NOME_UTENTE;
    private String COGNOME_UTENTE;
    private String COD_OPERATORE;
    private LocalDateTime DAT_INS;
    private LocalDateTime DAT_UPD;
}
