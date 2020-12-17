package com.utility;

import com.entities.UtentiEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Filters {
    private static String operator = "\"GIM_BE\"";
    public static String retrievePasswordfromUser = "select p.ps from utenti u, password p where username_utente = ?";
    public static String retrieveidUser = "select u.id_utente from utenti u where username_utente = ?";
    public static String insertNewUser = "INSERT INTO UTENTI (USERNAME_UTENTE,NOME_UTENTE,COGNOME_UTENTE,COD_OPERATORE,DAT_INS,DAT_UPD) VALUES (?,?,?,"+operator+",(SELECT SYSDATE()),(SELECT SYSDATE()))";
    public static String insertNewPwd = "INSERT INTO PASSWORD (ID_UTENTE,PS,PS_SALT,COD_OPERATORE,DAT_INS,DAT_UPD) VALUES (?,?,?,"+operator+",(SELECT SYSDATE()),(SELECT SYSDATE()))";
}
