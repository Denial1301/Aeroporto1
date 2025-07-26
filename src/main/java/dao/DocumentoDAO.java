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

    /**
     * Update documento.
     *
     * @param numDoc        the num doc
     * @param dataEmissione the data emissione
     * @param dataScadenza  the data scadenza
     * @param cfVecchio     the cf vecchio
     */
    public void updateDocumento(String numDoc,
                                LocalDate dataEmissione,LocalDate dataScadenza,String cfVecchio);

}
