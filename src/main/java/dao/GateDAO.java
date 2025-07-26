package dao;

import java.util.ArrayList;

/**
 * The interface Gate dao.
 */
public interface GateDAO
{
    /**
     * Gets gate.
     *
     * @param gate the gate
     */
    public void getGate(ArrayList<String> gate);

    /**
     * Libera gate.
     *
     * @param codice the codice
     * @throws IllegalArgumentException quando il volo a cui si deve cancellare il gate è null
     */
    public void liberaGate(String codice) throws IllegalArgumentException;
}
