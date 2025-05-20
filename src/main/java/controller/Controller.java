package controller;
import models.*;

import java.util.ArrayList;

public class Controller {
    private ArrayList<Utente> utenti;

    public Controller() {
        utenti = new ArrayList<>();
        utenti.add(new Utente("Daniele","Daniele2005","Guardascione","daniele@gmail.com"));
        utenti.add(new Utente("Salvatore","Ricco","Frizzi","salvatore@amministratore.com"));

    }
    public void reigstraUtente(String nome_utente, String password,String Cognome,String Email){
        utenti.add(new Utente(nome_utente,password,Cognome,Email));
    }
    public void login(String email, String password) throws Exception {
        for (Utente utente : utenti) {

            if (email.equals(utente.getEmail()) && password.equals(utente.getPassword())) {
                int index = utente.getEmail().indexOf("@");
                if (index != -1) {
                    String check = utente.getEmail().substring(index+1);
                    if (check.startsWith("amministratore")) {
                        System.out.println("Amministratore");
                        return;
                    }
                }
                System.out.println("Utente loggato come: " + utente.getEmail());

                return;
            }
        }throw new Exception("Errore, utente non registrato o password errata.");
    }






}
