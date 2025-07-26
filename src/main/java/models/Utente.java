package models;

import java.time.LocalDate;


public class Utente  {
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataNascita;
    private String password;


    public Utente(String nome, String password, String cognome, String email, LocalDate dataNascita) {
    this.nome = nome;
    this.cognome = cognome;
    this.email = email;
    this.password = password;
    this.dataNascita = dataNascita;

}



}
