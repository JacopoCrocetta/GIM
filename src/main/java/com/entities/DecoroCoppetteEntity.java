package com.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tip_decoro_coppette")
@Data
@NoArgsConstructor
@Getter
@Setter
public class DecoroCoppetteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String des_TIP_DECORO_COPPETTE;
    private String cod_OPERATORE;

    private LocalDateTime datIns;
    private LocalDateTime datUpd;
}