package com.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password")
@Data
@NoArgsConstructor
@Getter
@Setter
public class PasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String PS;
    private String PS_SALT;
    private String COD_OPERATORE;
    private LocalDateTime DAT_INS;
    private LocalDateTime DAT_UPD;
}
