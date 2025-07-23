package models;

public class Amministratore {
        private StatoVolo VoliAmministrati;
        private String nome_amministratore;
        private String password;
public Amministratore(StatoVolo VoliAmministrati, String nome_amministratore, String password) {
        this.VoliAmministrati = VoliAmministrati;
        this.nome_amministratore = nome_amministratore;
        this.password = password;
    }

}
