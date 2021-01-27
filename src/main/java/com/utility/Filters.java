package com.utility;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Filters {
    private static String operator = "\"GIM_BE\"";
    public static int maxTentatives = 3;
    public static String checkUser = "select count(u.id_utente) from utenti u where username_utente = ?";
    public static String retrievePasswordfromUser = "select p.ps from utenti u, password p where u.username_utente = ? and p.id_utente = u.id_utente";
    public static String retrieveSaltedPasswordfromUser = "select p.ps_salt from utenti u, password p where u.username_utente = ? and p.id_utente = u.id_utente";
    public static String retrieveTentativesfromUser = "select p.try from utenti u, password p where u.username_utente = ? and p.id_utente = u.id_utente";
    public static String retrieveSaltedTentativesfromUser = "select p.try_salt from utenti u, password p where u.username_utente = ? and p.id_utente = u.id_utente";
    public static String retrieveidUser = "select u.id_utente from utenti u where username_utente = ?";
    public static String retrieveUserMail= "select u.email_utente from utenti u where username_utente = ?";
    public static String insertNewUser = "INSERT INTO UTENTI (USERNAME_UTENTE,EMAIL_UTENTE,NOME_UTENTE,COGNOME_UTENTE,COD_OPERATORE,DAT_INS,DAT_UPD) VALUES (?,?,?,?,"+operator+",(SELECT SYSDATE()),(SELECT SYSDATE()))";
    public static String insertNewPwd = "INSERT INTO PASSWORD (ID_UTENTE,PS,PS_SALT,TRY,TRY_SALT,COD_OPERATORE,DAT_INS,DAT_UPD) VALUES (?,?,?,0,0,"+operator+",(SELECT SYSDATE()),(SELECT SYSDATE()))";
    public static String updateTentatives = "UPDATE PASSWORD SET TRY=TRY+1 WHERE ID_UTENTE = ?";
    public static String updateSaltedTentatives = "UPDATE PASSWORD SET TRY_SALT=TRY_SALT+1 WHERE ID_UTENTE = ?";
    public static String updatePassword = "UPDATE PASSWORD SET PS=?, TRY=0 WHERE ID_UTENTE = ?";
    public static String resetTentatives = "UPDATE PASSWORD SET TRY=0 WHERE ID_UTENTE = ?";
    public static String resetSaltedTentatives = "UPDATE PASSWORD SET TRY_SALT=0 WHERE ID_UTENTE = ?";
    public static String destroyUserPassword = "UPDATE PASSWORD SET PS=NULL WHERE ID_UTENTE = ?";
    public static String destroyUserSaltedPassword = "UPDATE PASSWORD SET PS_SALT=NULL WHERE ID_UTENTE = ?";
}
