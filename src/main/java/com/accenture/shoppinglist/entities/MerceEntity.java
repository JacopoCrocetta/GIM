package com.accenture.shoppinglist.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

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
    private BigInteger quantita;

    private String codMerce;
    private String note;
    private String codOperatore;
}
