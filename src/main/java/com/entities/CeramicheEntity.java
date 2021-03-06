package com.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CeramicheItems")
@Data
@NoArgsConstructor
@Getter
@Setter
public class CeramicheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int quantity;

    private String product;
    private String cod_OPERATORE;

    private LocalDateTime datIns;
    private LocalDateTime datUpd;
}
