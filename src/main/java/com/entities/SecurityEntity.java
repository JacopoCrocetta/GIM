package com.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Data
@Getter
@Setter
public class SecurityEntity {
    @Id
    private Boolean ACCESS_GRANTED;
    private Boolean ACCESS_LOCKED;
    private Boolean PWD_ERR;
}
