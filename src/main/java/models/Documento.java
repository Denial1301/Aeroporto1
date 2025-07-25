package models;

import java.time.LocalDate;

public class Documento {
    private boolean isPassaporto;
    private LocalDate dataScadenza;
    private String numeroDocumento;
    private LocalDate dataEmissione;


    public Documento(boolean isPassaporto, LocalDate dataScadenza, String numeroDocumento, LocalDate dataEmissione) {
        this.isPassaporto = isPassaporto;
        this.dataScadenza = dataScadenza;
        this.numeroDocumento = numeroDocumento;
        this.dataEmissione = dataEmissione;
    }


}
