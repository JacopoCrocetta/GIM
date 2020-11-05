package com.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ShoppingItems")
@Data
@NoArgsConstructor
@Getter
@Setter
public class CeramicheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String product;

    private int quantity;
}
