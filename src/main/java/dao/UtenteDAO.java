package dao;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The interface Utente dao.
 */
public interface UtenteDAO {
    /**
     * Add utente.
     *
     * @param email       the email
     * @param nome        the nome
     * @param cognome     the cognome
     * @param password    the password
     * @param dataNascita the data nascita
     * @throws IllegalArgumentException se l'email è già presente nel DB
     */
    public void addUtente(String email,String nome, String cognome,String password, LocalDate dataNascita) throws IllegalArgumentException;

    /**
     * Gets utente by email and password. <p>
     *
     *
     * @param email    the email
     * @param password the password
     * @return restituisce un valore booleano in base a se l'utente è presente o meno nel DB<p>
     *  {@code true} = l'utente è presente nel DB; <p>
     *  {@code false} = l'utente non è presente nel DB. <p>
     */
    public boolean getUtenteByEmailAndPassword(String email, String password);
}
