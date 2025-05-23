package controller;
import models.*;


import java.text.ParseException;
import java.time.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;


public class Controller {
    private ArrayList<Utente> utenti;
    private ArrayList<Volo> volo;

    public Controller() {
        utenti = new ArrayList<>();
        volo = new ArrayList<>();
        utenti.add(new Utente("Daniele","Delta","Guardascione","daniele@gmail.com"));
        utenti.add(new Utente("Salvatore","Ricco","Frizzi","salvatore@amministratore.com"));
        utenti.add(new Utente("s","s","s","@amministratore"));
        volo.add(new Volo("test","test","test","test",LocalDate.of(2025,6,4),LocalTime.of(17,00),0,90));


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
                        System.out.println("Accesso eseguito come: Amministratore");
                        return;
                    }
                }
                System.out.println("Utente loggato come: " + utente.getEmail());

                return;
            }
        }throw new Exception("Errore, utente non registrato o password errata.");
    }
    public void registraVolo(String codice, String compagnia, String aereoporto_origine,
                             String aereoporto_destinazione, LocalDate datavolo,
                             LocalTime oraArrivo, int ritardo, int numPosti) throws Exception {

        volo.add(new Volo(codice, compagnia, aereoporto_origine, aereoporto_destinazione, datavolo, oraArrivo, ritardo, numPosti));
        System.out.println("Volo registrato");
    }
    public void VediVolo() {
        for (Volo index: volo){

            System.out.println(index.toString());



        }


    }






}
