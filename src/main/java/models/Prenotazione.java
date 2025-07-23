package models;

public class Prenotazione  {
    private String numPrenotazione;
    private String posto;
    private StatoPrenotazione stato;
    private String emailUtente;
    private String codiceVolo;
    private String cf;
    private String nome;
    private String cognome;
public Prenotazione(String nome, String cognome,String numPrenotazione, String posto, StatoPrenotazione stato, String codiceVolo, String cf) {
    this.nome = nome;
    this.cognome = cognome;
    this.numPrenotazione = numPrenotazione;
    this.posto = posto;
    this.stato = stato;
    this.codiceVolo = codiceVolo;
    this.cf = cf;
    }
    public Prenotazione(String numPrenotazione, String posto, StatoPrenotazione stato, String codiceVolo, String cf)
    {
        this.numPrenotazione = numPrenotazione;
        this.posto = posto;
        this.stato = stato;
        this.codiceVolo = codiceVolo;
        this.cf = cf;
    }


    @Override
    public String toString() {
        return "Prenotazione{numero:'" + numPrenotazione + "'\'"+
                ", posto: '" + posto + "'\'" +
                ", stato: '" + stato.toString() + "'\'" +
                ", codice volo: '" + codiceVolo + "'\'" +
                ", cf: '" + cf + "'}";
    }
    public String toStringPrenotazione() {
        return "Prenotazione{" +
                "nome: '" + nome + '\'' +
                ", cognome: '" + cognome +'\''+
                "numero:'" + numPrenotazione + '\'' +
                ", posto='" + posto + '\'' +
                ", stato: '" + stato.toString() + '\'' +
                ", codice volo: '" + codiceVolo + '\'' +
                ", cf: '" + cf + '\'' +
                '}';
    }

}
