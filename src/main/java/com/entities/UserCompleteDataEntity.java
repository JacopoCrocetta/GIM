package com.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Data
@Getter
@Setter
public class UserCompleteDataEntity {
    @Id
    private String USER;
    private String NAME;
    private String SURNAME;
    private String PWD;
}
