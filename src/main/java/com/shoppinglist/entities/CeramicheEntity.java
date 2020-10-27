package com.shoppinglist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ShoppingItems")
@Data
@NoArgsConstructor
public class CeramicheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String product;

    private int quantity;
}
