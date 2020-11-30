package com.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tip_decoro_vassoi")
@Data
@NoArgsConstructor
@Getter
@Setter
public class DecoroVassoiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    String DES_TIP_DECORO_VASSOI;
    String cod_OPERATORE;

    LocalDateTime datIns;
    LocalDateTime datUpd;
}