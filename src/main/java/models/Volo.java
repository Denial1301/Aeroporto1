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


    public String getCodice() {
        return codice;
    }
    public LocalTime getOraPartenza() {
        return oraPartenza;
    }
    public String getCompagnia() {
        return compagnia;
    }
    public String getAeroportoOrigine() {
        return aeroportoOrigine;
    }
    public String getAeroportoDestinazione() {
        return aeroportoDestinazione;
    }
    public LocalDate getDataVolo() {
        return dataVolo;
    }
    public LocalTime getOraArrivo() {
        return oraArrivo;
    }
    public int getRitardo() {
        return ritardo;
    }
    public StatoVolo getRegistro() {
        return registro;
    }
    public int getNumPosti() {
        return numPosti;
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
