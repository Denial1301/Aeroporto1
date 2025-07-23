package dao;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The interface Voli dao.
 */
public interface VoliDAO {
    /**
     * Gets all voli.
     *
     * @param codice       the codice
     * @param compagnia    the compagnia
     * @param origine      the origine
     * @param destinazioni the destinazioni
     * @param gate         the gate
     * @param dateVoli     the date voli
     * @param orePartenza  the ore partenza
     * @param oreArrivi    the ore arrivi
     * @param statoVolo    the stato volo
     * @param posti        the posti
     * @param ritardi      the ritardi
     */
    public void getAllVoli(ArrayList<String> codice, ArrayList<String> compagnia, ArrayList<String> origine, ArrayList<String> destinazioni,
                           ArrayList<String> gate, ArrayList<LocalDate> dateVoli, ArrayList<LocalTime> orePartenza,
                           ArrayList<LocalTime> oreArrivi, ArrayList<String> statoVolo, ArrayList<Integer> posti, ArrayList<Integer> ritardi);

    /**
     * Elimina voli scaduti. <p>
     * Funzione del DB, elimina: <p>
     *     I voli cancellati;<p>
     *     I voli con data < di data corrente;<p>
     *     I voli con orario di arrivo < di orario corrente e data volo = a data corrente.
     */
    public void eliminaVoliScaduti();

    /**
     * Update voli.
     *
     * @param isPartenza the is partenza
     * @param codice     the codice
     * @param compagnia  the compagnia
     * @param data       the data
     * @param arrivo     the arrivo
     * @param partenza   the partenza
     * @param stato      the stato
     * @param ritardo    the ritardo
     * @param gate       the gate
     */
    public void updateVoli(boolean isPartenza,String codice, String compagnia,LocalDate data,LocalTime arrivo,LocalTime partenza,String stato,int ritardo,String gate);


    /**
     * Gets voli filtrati.<p>
     * Cerca nel DB quali voli soddisfano quei criteri.
     *
     * @param codice       the codice da cercare
     * @param compagnia    the compagnia da cercare
     * @param stato        the stato da cercare
     * @param codici       the codici
     * @param compagnie    the compagnie
     * @param origini      the origini
     * @param destinazioni the destinazioni
     * @param gates        the gates
     * @param dateVoli     the date voli
     * @param orePartenza  the ore partenza
     * @param oreArrivi    the ore arrivi
     * @param stati        the stati
     * @param posti        the posti
     * @param ritardi      the ritardi
     */
    public void getVoliFiltrati(String codice,String compagnia,String stato, ArrayList<String> codici, ArrayList<String> compagnie, ArrayList<String> origini,
                                ArrayList<String> destinazioni, ArrayList<String> gates, ArrayList<LocalDate> dateVoli, ArrayList<LocalTime> orePartenza,
                                ArrayList<LocalTime> oreArrivi, ArrayList<String> stati, ArrayList<Integer> posti, ArrayList<Integer> ritardi);

    /**
     * Add voli.
     *
     * @param codice       the codice
     * @param compagnia    the compagnia
     * @param origine      the origine
     * @param destinazione the destinazione
     * @param dataVolo     the data volo
     * @param oraPartenza  the ora partenza
     * @param oraArrivo    the ora arrivo
     * @param stato        the stato
     * @param posti        the posti
     * @param ritardo      the ritardo
     */
    public void addVoli(String codice, String compagnia, String origine, String destinazione,LocalDate dataVolo,LocalTime oraPartenza,LocalTime oraArrivo,String stato, int posti,
                        int ritardo);


}
