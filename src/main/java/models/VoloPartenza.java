package models;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class VoloPartenza extends Volo {
    private String gateImbarco;


    public VoloPartenza(String codice, String compagnia, String aereoportoOrigine, String aereoportoDestinazione, LocalDate dataVolo, LocalTime oraPartenza,LocalTime oraArrivo,
                        int ritardo, int numPosti,StatoVolo registro,String gateImbarco) {
        super(codice, compagnia, aereoportoOrigine, aereoportoDestinazione, dataVolo, oraPartenza,oraArrivo, ritardo, numPosti,registro);
        this.gateImbarco = gateImbarco;
    }




    public String getGateImbarco() {
        return gateImbarco;
    }



    @Override
    public String toString(){

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return super.toString() + ", destinazione ='" + super.getAeroportoDestinazione() + '\'' +

                ", Partenza=" + super.getOraPartenza().format(timeFormatter) +
                ", Arrivo=" + super.getOraArrivo().format(timeFormatter) +
                ", Gate=" + gateImbarco +
                " , Stato=" + super.getRegistro().toString() +
                ".";


    }

}
