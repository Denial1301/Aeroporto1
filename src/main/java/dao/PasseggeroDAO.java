package dao;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The interface Passeggero dao.
 */
public interface PasseggeroDAO {
    /**
     * Add passeggero.
     *
     * @param nome         the nome
     * @param cognome      the cognome
     * @param numDocumento the num documento
     * @param cf           the cf
     * @param dataNascita  the data nascita
     */
    public void addPasseggero(String nome, String cognome, String numDocumento, String cf, LocalDate dataNascita);

    /**
     * Gets passeggero filtrato.<p>
     * Con i parametri che vengono passati dal controller recupera i passeggeri che soddisfano tali parametri
     *
     * @param nome         nome da cercare nel DB
     * @param cognome      the cognome da cercare nel DB
     * @param cf           the cf da cercare nel CF
     * @param nomi         the nomi
     * @param cognomi      the cognomi
     * @param numDocumento the num documento
     * @param cfSQL        the cf sql
     * @param dataNascita  the data nascita
     */
    public void getPasseggeroFiltrato(String nome, String cognome, String cf,ArrayList<String> nomi, ArrayList<String> cognomi,
                                      ArrayList<String> numDocumento, ArrayList<String> cfSQL, ArrayList<LocalDate> dataNascita);

    /**
     * Gets all passeggero.
     *
     * @param nome         the nome
     * @param cognome      the cognome
     * @param numDocumento the num documento
     * @param cf           the cf
     * @param dataNascita  the data nascita
     */
    public void getAllPasseggero(ArrayList<String> nome, ArrayList<String> cognome, ArrayList<String> numDocumento, ArrayList<String> cf, ArrayList<LocalDate> dataNascita);

}
