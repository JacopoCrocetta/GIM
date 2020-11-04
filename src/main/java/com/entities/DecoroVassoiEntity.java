package com.entities;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DecoroVassoi")
@Data
@NoArgsConstructor
@Getter
@Setter
public class DecoroVassoiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    String DES_TIP_DECORO_VASSOI;
    String COD_OPERATORE;

    LocalDateTime DatIns;
    LocalDateTime DatUpd;
}