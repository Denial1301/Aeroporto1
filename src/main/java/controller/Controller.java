package controller;
import models.*;

import java.util.ArrayList;

public class Controller {
    private ArrayList<Utente> utenti;

    public Controller() {
        utenti = new ArrayList<>();
        utenti.add(new Utente("Daniele","Daniele2005","Guardascione","daniele@gmail.com"));

    }
    public void reigstraUtente(String nome_utente, String password,String Cognome,String Email){
        utenti.add(new Utente(nome_utente,password,Cognome,Email));
    }
    public void login(String email, String password) throws Exception {
        for (Utente utente : utenti) {
            if (email.equals(utente.getEmail()) && password.equals(utente.getPassword())) {
                System.out.println("Utente loggato come: " + utente.getEmail());
                return;
            }
        }

        throw new Exception("Errore, utente non registrato o password errata.");
    }
   public void registraUtente(String nome_utente, String password, String cognome, String email) throws Exception {

   }





}
