package models;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class VoloArrivo extends Volo
{


    public VoloArrivo(String codice, String compagnia, String aeroportoOrigine, String aeroportoDestinazione, LocalDate dataVolo, LocalTime oraPartenza,LocalTime oraArrivo,
                      int ritardo, int numPosti,StatoVolo registro) {
        super(codice, compagnia, aeroportoOrigine, aeroportoDestinazione, dataVolo, oraPartenza,oraArrivo, ritardo, numPosti,registro);
    }

    public String toString(){

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return super.toString() + ", origine ='" + super.getAeroportoOrigine() + '\'' +

                ", Partenza=" + super.getOraPartenza().format(timeFormatter) +
                ", Arrivo=" + super.getOraArrivo().format(timeFormatter) +
                ", Stato=" + super.getRegistro().toString() +

                ".";


    }
}
