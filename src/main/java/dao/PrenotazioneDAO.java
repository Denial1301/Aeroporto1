package dao;




import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Prenotazione dao.
 */
public interface PrenotazioneDAO {
    /**
     * Gets prenotazioni.
     *<p>
     * Serve per poter prendere tutte le prenotazioni dal DB.
     * @param nome            the nome
     * @param cognome         the cognome
     * @param numPrenotazione the num prenotazione
     * @param posto           the posto
     * @param stato           the stato
     * @param codiceVolo      the codice volo
     * @param cf              the cf
     */
    public void getPrenotazioni(ArrayList<String> nome,ArrayList<String> cognome,ArrayList<String> numPrenotazione, ArrayList<String> posto, ArrayList<String> stato,
                                ArrayList<String> codiceVolo, ArrayList<String> cf);

    /**
     * Cerca prenotazioni. <p>
     * Serve per cercare le prenotazioni associate a quell'utente.
     *
     * @param nome            the nome
     * @param cognome         the cognome
     * @param email           l'email di login dell'utente per poter cercare nel DB solo le sue prenotazioni
     * @param ricerca         Può essere il codice del volo oppure il nome del passeggero, se vuota, restutisce tutte le prenotazioni associate a quell'utente.
     * @param numPrenotazione the num prenotazione
     * @param posto           the posto
     * @param stato           the stato
     * @param codiceVolo      the codice volo
     * @param cf              the cf
     */
    public void cercaPrenotazioni(ArrayList<String> nome, ArrayList<String> cognome,String email,String ricerca, ArrayList<String> numPrenotazione, ArrayList<String> posto, ArrayList<String> stato, ArrayList<String> codiceVolo,
                                  ArrayList<String> cf );

    /**
     * Gets voli prenotati.
     *
     * @param emailDaCercare the email da cercare
     * @param codice         the codice
     */
    public void getVoliPrenotati(String emailDaCercare, ArrayList<String> codice);

    /**
     * Genera codice prenotazione string.
     * <p>
     * * Genera un codice progressivo per le prenotazioni, con formato "PR000".
     * @return the string
     */
    public String generaCodicePrenotazione();

    /**
     * Add prenotazione.
     * <p>
     * Aggiunge la prenotazione effettuate nel DB.
     * @param posto           the posto
     * @param cf              the cf
     * @param email           the email
     * @param numPrenotazione the num prenotazione
     * @param codice          the codice
     */
    public void addPrenotazione(String posto,String cf, String email,String numPrenotazione,String codice);

    /**
     * Genera posti liberi array list.
     * <p>
     * Genera tutti i posti disponibili per quel volo calcolando la fila(A, B, C) e il numero(da 1 a 45).
     * @param codice      the codice
     * @param postiTotali the posti totali
     * @return the array list
     */
    public ArrayList<String> generaPostiLiberi(String codice,int postiTotali);

    /**
     * Conferma prenotazione.
     * <p>
     * Serve per cambiare lo stato della prenotazione.
     *
     * @param numPrenotazione the num prenotazione
     * @param isConfermata    in base al suo valore, lo stato diventa "Confermata" oppure "Cancellata"
     */
    public void confermaPrenotazione(String numPrenotazione,boolean isConfermata);
    public void updatePrenotazione(String nome, String cognome, String email,String codice,String cf);
    public void cancellaPrenotazione(String email,String codice,String cf);
}
