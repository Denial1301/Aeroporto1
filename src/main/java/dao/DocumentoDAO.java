package dao;

import java.time.LocalDate;

public interface DocumentoDAO {
    /**
     * Add documento.
     *
     * @param numDocumento  the num documento
     * @param isPassaporto  the is passaporto
     * @param dataEmissione the data emissione
     * @param dataScadenza  the data scadenza
     */
    public void addDocumento(String numDocumento, boolean isPassaporto, LocalDate dataEmissione, LocalDate dataScadenza);

}
