package com.shoppinglist.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "DecoroCoppette")
@Data
@NoArgsConstructor
@Getter
@Setter
public class DecoroCoppetteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    String DES_TIP_DECORO_COPPETTE;
    String COD_OPERATORE;

    LocalDateTime DatIns;
    LocalDateTime DatUpd;
}