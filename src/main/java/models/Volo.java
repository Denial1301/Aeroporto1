package models;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class Volo
{
    private String codice;
    private String compagnia;
    private String aeroportoOrigine;
    private String aeroportoDestinazione;
    private LocalDate dataVolo;
    private LocalTime oraPartenza;
    private LocalTime oraArrivo;
    private int ritardo;
    private int numPosti;
    private StatoVolo registro;


    public Volo(String codice, String compagnia, String aeroportoOrigine,
                String aeroportoDestinazione, LocalDate dataVolo, LocalTime oraPartenza,
                LocalTime oraArrivo, int ritardo, int numPosti, StatoVolo registro) {

        this.codice = codice;
        this.compagnia = compagnia;
        this.aeroportoOrigine = aeroportoOrigine;
        this.aeroportoDestinazione = aeroportoDestinazione;
        this.dataVolo = dataVolo;
        this.oraArrivo = oraArrivo;
        this.oraPartenza = oraPartenza;
        this.ritardo = ritardo;
        this.numPosti = numPosti;
        this.registro = registro;
    }

    // Getter e Setter
    public String getCodice() {
        return codice;
    }

    public LocalTime getOraPartenza() {
        return oraPartenza;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getCompagnia() {
        return compagnia;
    }

    public void setCompagnia(String compagnia) {
        this.compagnia = compagnia;
    }

    public String getAeroportoOrigine() {
        return aeroportoOrigine;
    }

    public void setAeroportoOrigine(String aeroportoOrigine) {
        this.aeroportoOrigine = aeroportoOrigine;
    }

    public String getAeroportoDestinazione() {
        return aeroportoDestinazione;
    }

    public void setAeroportoDestinazione(String aeroportoDestinazione) {
        this.aeroportoDestinazione = aeroportoDestinazione;
    }


    public LocalDate getDataVolo() {
        return dataVolo;
    }

    public void setDataVolo(LocalDate dataVolo) {
        this.dataVolo = dataVolo;
    }

    public LocalTime getOraArrivo() {
        return oraArrivo;
    }

    public void setOraArrivo(LocalTime oraArrivo) {
        this.oraArrivo = oraArrivo;
    }

    public int getRitardo() {
        return ritardo;
    }

    public void setRitardo(int ritardo) {
        this.ritardo = ritardo;
    }

    public StatoVolo getRegistro() {
        return registro;
    }

    public void setRegistro(StatoVolo registro) {
        this.registro = registro;
    }



    public int getNumPosti() {
        return numPosti;
    }

    public void setNumPosti(int numPosti) {
        this.numPosti = numPosti;
    }


    public String allToString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return "Volo{" +
                "codice='" + codice + '\'' +
                ", compagnia='" + compagnia + '\'' +
                ", origine='" + aeroportoOrigine + '\'' +
                ", destinazione='" + aeroportoDestinazione + '\'' +
                ", data=" + dataVolo.format(dateFormatter) + '\'' +
                ", Partenza=" + oraPartenza.format(timeFormatter) + '\'' +
                ", Arrivo=" + oraArrivo.format(timeFormatter) +
                ", ritardo=" + ritardo + " min" +
                ", Posti disponibili=" + numPosti + "}";
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Volo: " +
                "codice='" + codice + '\'' +
                ", compagnia='" + compagnia + '\'' +
                ", dataVolo=" + dataVolo.format(dateFormatter) +'\'';
    }
}
