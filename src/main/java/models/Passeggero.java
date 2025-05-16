package models;

public class Passeggero {
    private String nome;
    private String cognome;
    private Documento DocumentoP;
    public Passeggero(String nome, String cognome,Documento DocumentoP) {
        this.nome = nome;
        this.cognome = cognome;
        this.DocumentoP=DocumentoP;
    }
}
