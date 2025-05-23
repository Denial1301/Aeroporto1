package models;
import java.time.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;


public class Volo
{
    private String codice;
    private String compagnia;
    private String aereoporto_origine;
    private String aereoporto_destinazione;
    private LocalDate datavolo;
    private LocalTime oraArrivo;
    private int ritardo ;
    private int numPosti;
    private StatoVolo registro;


    public Volo(String codice, String compagnia, String aereoporto_origine,
                String aereoporto_destinazione, LocalDate datavolo,
                LocalTime oraArrivo, int ritardo, int numPosti) {

        this.codice = codice;
        this.compagnia = compagnia;
        this.aereoporto_origine = aereoporto_origine;
        this.aereoporto_destinazione = aereoporto_destinazione;
        this.datavolo = datavolo;
        this.oraArrivo = oraArrivo;
        this.ritardo = ritardo;
        this.numPosti = numPosti;
        Object StatoVolo;
    }

    // Getter e Setter
    public String getCodice() {
        return codice;
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

    public String getAereoporto_origine() {
        return aereoporto_origine;
    }

    public void setAereoporto_origine(String aereoporto_origine) {
        this.aereoporto_origine = aereoporto_origine;
    }

    public String getAereoporto_destinazione() {
        return aereoporto_destinazione;
    }

    public void setAereoporto_destinazione(String aereoporto_destinazione) {
        this.aereoporto_destinazione = aereoporto_destinazione;
    }


    public LocalDate getDatavolo() {
        return datavolo;
    }

    public void setDatavolo(LocalDate datavolo) {
        this.datavolo = datavolo;
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

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return "Volo{" +
                "codice='" + codice + '\'' +
                ", compagnia='" + compagnia + '\'' +
                ", aereoporto_origine='" + aereoporto_origine + '\'' +
                ", aereoporto_destinazione='" + aereoporto_destinazione + '\'' +
                ", datavolo=" + datavolo.format(dateFormatter) +
                ", oraArrivo=" + oraArrivo.format(timeFormatter) +
                ", ritardo=" + ritardo + " min" +
                ", numPosti=" + numPosti + "}";
    }

}
