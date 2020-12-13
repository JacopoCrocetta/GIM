package com.utility;

import com.entities.UtentiEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Filters {
    private UtentiEntity utentiEntity;

    //Costruttori
    public Filters(UtentiEntity utentiEntity){
        this.utentiEntity = utentiEntity;
    }


    //Metodi
    public int getIdUtente(Iterable<UtentiEntity> listOfUser, String username){
        for (UtentiEntity utente: listOfUser) {
            if (username.equals(utente.getUSERNAME_UTENTE())){
                return utente.getID_UTENTE();
            }
        }
        return 0;
    }
}
