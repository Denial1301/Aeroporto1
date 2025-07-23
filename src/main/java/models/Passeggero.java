package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Passeggero {
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private Documento DocumentoP;
    private String numDocumento;
    private String CF;
    public Passeggero(String nome, String cognome,Documento DocumentoP,String CF, LocalDate dataNascita) {

        this.nome = nome;
        this.cognome = cognome;
        this.DocumentoP=DocumentoP;
        this.CF=CF;
        this.dataNascita=dataNascita;
    }
    public Passeggero(String nome, String cognome,String numDocumento,String CF, LocalDate dataNascita) {

        this.nome = nome;
        this.cognome = cognome;
        this.numDocumento = numDocumento;
        this.CF=CF;
        this.dataNascita=dataNascita;
    }


    @Override
    public String toString() {
        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Passeggero{" +
                "nome=" + nome + '\'' +
                ", cognome=" + cognome + '\'' +
                ", dataNascita=" + dataNascita.format(dataTimeFormatter) +
                ", numero Documento=" + numDocumento +
                ", CF='" + CF + '\'' +
                '}';
    }
}
