package com.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "MerceItems")
@Data
@NoArgsConstructor
@Getter
@Setter
public class MerceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private BigDecimal prezzoAcquisto;
    private BigDecimal prezzoVendita;
    private BigDecimal quantita;

    private String codMerce;
    private String note;
    private String cod_OPERATORE;

    LocalDateTime datIns;
    LocalDateTime datUpd;
}
