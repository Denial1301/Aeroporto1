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


    public boolean getPassaporto() {
        return isPassaporto;
    }

    public void setPassaporto(boolean isPassaporto) {
        this.isPassaporto = isPassaporto;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }
}
