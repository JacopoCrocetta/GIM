package com.utility;

import com.entities.UtentiEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Filters {
    private static String operator = "\"GIM_BE\"";
    public static int maxTentatives = 3;
    public static String retrievePasswordfromUser = "select p.ps from utenti u, password p where u.username_utente = ? and p.id_utente = u.id_utente";
    public static String retrieveTentativesfromUser = "select p.try from utenti u, password p where u.username_utente = ? and p.id_utente = u.id_utente";;
    public static String retrieveidUser = "select u.id_utente from utenti u where username_utente = ?";
    public static String insertNewUser = "INSERT INTO UTENTI (USERNAME_UTENTE,NOME_UTENTE,COGNOME_UTENTE,COD_OPERATORE,DAT_INS,DAT_UPD) VALUES (?,?,?,"+operator+",(SELECT SYSDATE()),(SELECT SYSDATE()))";
    public static String insertNewPwd = "INSERT INTO PASSWORD (ID_UTENTE,PS,PS_SALT,TRY,COD_OPERATORE,DAT_INS,DAT_UPD) VALUES (?,?,?,0,"+operator+",(SELECT SYSDATE()),(SELECT SYSDATE()))";
    public static String updateTentatives = "UPDATE PASSWORD SET TRY=TRY+1 WHERE ID_UTENTE = ?";
    public static String resetTentatives = "UPDATE PASSWORD SET TRY=0 WHERE ID_UTENTE = ?";
    public static String destroyUserPassword = "UPDATE PASSWORD SET PS=NULL WHERE ID_UTENTE = ?";
}
