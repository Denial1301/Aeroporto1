package models;

import java.time.LocalDate;


public class Utente  {
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataNascita;
    private String password;

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public Utente(String nome, String password, String cognome, String email, LocalDate dataNascita) { // va eliminata se necessario

    this.cognome = cognome;
    this.email = email;
    this.password = password;
    this.dataNascita = dataNascita;

}
public Utente(String email, String Password)
{
    this.email = email;
    this.password = Password;
}


}
