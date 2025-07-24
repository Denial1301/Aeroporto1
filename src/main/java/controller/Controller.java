package controller;
import dao.VoliDAO;
import implementazionePostgresDAO.*;
import models.*;
import db.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;


/**
 * The type Controller.
 */
public class Controller {

    private ArrayList<Volo> volo;

    private Connection connection;

    private VoliDAOImpl voliDAO = new VoliDAOImpl();

    private PrenotazioneDAOImpl prenotazioneDAO = new PrenotazioneDAOImpl();

    private PasseggeroDAOImpl passeggeroDAO = new PasseggeroDAOImpl();

    private DocumentoDAOImpl documentoDAO = new DocumentoDAOImpl();

    private GateDAOImpl gateDAO = new GateDAOImpl();
    /**
     * The Utente dao.
     */
    UtenteDAOImpl utenteDAO = new UtenteDAOImpl();
    private String emailLogin;
    private final int postiTotali = 45;
    private Random rand = new Random();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Instantiates a new Controller.
     */
    public Controller() {
    try {
        connection = ConnessioneDatabase.getInstance();

    }catch (SQLException e){
        e.printStackTrace();
    }
}

    /**
     * Reigstra utente.
     *
     * @param email       the email
     * @param nome        the nome
     * @param cognome     the cognome
     * @param password    the password
     * @param dataNascita the data nascita
     */
    public void reigstraUtente(String email, String nome, String cognome, String password,String dataNascita) {


        LocalDate dataNascitaSQL = LocalDate.parse(dataNascita, formatter);
        utenteDAO.addUtente(email,nome,cognome,password,dataNascitaSQL);

    }

    /**
     * Login1 <p>
     * Esegue il login dell'utente verificando email e password.
     * Se le credenziali sono corrette inoltre, salva l'email dell'utente autenticato in modo da poterla usare per operazioni future.
     * <p>
     * Lancia un'eccezione se le credenziali sono errate o l'utente non esiste sul DB.
     *
     * @param email    L'indirizzo email dell'utente.
     * @param password La password dell'utente.
     * @throws IllegalArgumentException se le credenziali non sono valide oppure se l'utente non è presente nel DB.
     */
    public void login(String email, String password) throws IllegalArgumentException {


        boolean isUtente = utenteDAO.getUtenteByEmailAndPassword(email, password);

        if (!isUtente) {
            throw new IllegalArgumentException("Errore: utente non trovato o credenziali errate.");
        }
        this.emailLogin = email;
        System.out.println("Loggato come: "+emailLogin);

    }

    /**
     * Registra volo.
     *
     * @param codice                the codice
     * @param compagnia             the compagnia
     * @param aeroportoOrigine      the aeroporto origine
     * @param aeroportoDestinazione the aeroporto destinazione
     * @param dataVolo              the data volo
     * @param oraPartenza           the ora partenza
     * @param oraArrivo             the ora arrivo
     * @param ritardo               the ritardo
     * @param numPosti              the num posti
     * @param stato                 the stato
     */
    public void registraVolo(String codice, String compagnia, String aeroportoOrigine,
                             String aeroportoDestinazione, LocalDate dataVolo, LocalTime oraPartenza,
                             LocalTime oraArrivo, int ritardo, int numPosti,String stato) {


        voliDAO.addVoli(codice,compagnia,aeroportoOrigine,aeroportoDestinazione,dataVolo,oraPartenza,oraArrivo,stato,numPosti,ritardo);

    }

    /**
     * Gets lista voli.
     * <p>
     * Prende i parametri dal database e li inserisce nelle varie {@code ArrayList<String>} per costruire {@code ArrayList<Volo>} quando il metodo viene invocato.
     * <p>
     * Utilizza il polimorfismo, inserendo in {@code ArrayList<Volo>} le istanze figlie {@code VoloPartenza} e {@code VoloArrivo}  a seconda dell'origine dell'aeroporto.
     *
     * @return la lista voli
     */
    public ArrayList<Volo> getListaVoli()
    {
        ArrayList<String> codici = new ArrayList<>();
        ArrayList<String> compagnie = new ArrayList<>();
        ArrayList<String> origini = new ArrayList<>();
        ArrayList<String> destinazioni = new ArrayList<>();
        ArrayList<String> gates = new ArrayList<>();
        ArrayList<LocalDate> dateVoli = new ArrayList<>();
        ArrayList<LocalTime> orePartenza = new ArrayList<>();
        ArrayList<LocalTime> oreArrivi = new ArrayList<>();
        ArrayList<String> stati = new ArrayList<>();
        ArrayList<Integer> posti = new ArrayList<>();
        ArrayList<Integer> ritardi = new ArrayList<>();

        voliDAO.getAllVoli(codici, compagnie, origini, destinazioni, gates, dateVoli, orePartenza,oreArrivi, stati, posti, ritardi);

        ArrayList<Volo> listaVoli = new ArrayList<>();

        for (int i = 0; i < codici.size(); i++)
        {
            StatoVolo stato = StatoVolo.valueOf(stati.get(i));

            if ("NAP".equalsIgnoreCase(origini.get(i)))
            {

                VoloPartenza v = new VoloPartenza(codici.get(i), compagnie.get(i), origini.get(i), destinazioni.get(i), dateVoli.get(i), orePartenza.get(i),
                                                    oreArrivi.get(i), ritardi.get(i), posti.get(i), stato, gates.get(i));
                listaVoli.add(v);

            } else {

                VoloArrivo v = new VoloArrivo(codici.get(i), compagnie.get(i), origini.get(i), destinazioni.get(i), dateVoli.get(i), orePartenza.get(i),
                                                oreArrivi.get(i), ritardi.get(i), posti.get(i), stato);
                listaVoli.add(v);
            }
        }

        return listaVoli;
    }


    /**
     * getVoliPrenotati array list.
     * <p>
     * Questo metodo prende dal DB tutti i codici dell'utente loggato e, tramite un foreach, costruisce {@code ArrayList<String>voliPrenotati} inserendo SOLO i voli con lo stesso codice.
     * Se l'utente ha efettuato più prenotazioni sullo stesso codice verrà salvato solo una volta.
     *
     * @return voliPrenotati voli prenotati
     */
    public ArrayList<String> getVoliPrenotati() {
        ArrayList<String> codiciPrenotati = new ArrayList<>();

        prenotazioneDAO.getVoliPrenotati(emailLogin, codiciPrenotati);

        ArrayList<Volo> tuttiVoli = getListaVoli();
        ArrayList<String> voliPrenotati = new ArrayList<>();

        for (Volo v : tuttiVoli) {
            if (codiciPrenotati.contains(v.getCodice())) {
                voliPrenotati.add(v.allToString());
            }
        }

        return voliPrenotati;
    }


    /**
     * Voli partenza array list.
     * <p>
     * Viene utilizzato per convertire in {@code String} i campi di {@code tuttiVoli} in {@code VoliPartenza} sfruttando il polimorfismo per identificare le istanze {@code VoliPartenza}.
     *
     * @param voliPrenotabiliPartenza the voli prenotabili partenza
     * @param voliPrenotabiliArrivo   the voli prenotabili arrivo
     * @return la lista dei voli in partenza
     */
    public void voliPrenotabili(ArrayList<String> voliPrenotabiliPartenza, ArrayList<String> voliPrenotabiliArrivo)
    {
        ArrayList<Volo> tuttiVoli = getListaVoli();


        for (Volo v : tuttiVoli) {
            if(v instanceof VoloPartenza)
            {
                voliPrenotabiliPartenza.add(v.toString());
            }else if(v instanceof VoloArrivo)
            {
                voliPrenotabiliArrivo.add(v.toString());
            }



        }


    }


    /**
     * Get dati voli per gui string [ ] [ ].
     * <p>
     * Viene utilizzato per generare una matrice {@code String[][]} dei campi di voli per poter popolare {@code JTable} nella GUI.
     * Grazie al comando {@code istanceof}, riconosce da {@code voli} le istanze {@code VoliPartenza} e {@code VoliArrivo}, utilizzando {@code isPartenza} inserisce in
     * modo coretto i dati nella matrice.
     *
     * @param colonne    colonne della tabella
     * @param isPartenza serve a riconoscere un volo in partenza da un volo in arrivo(true = partenza, false = arrivo)
     * @return the string [ ] [ ]
     */
    public String[][] getDatiVoliPerGUI(String[] colonne,boolean isPartenza)
    {
        ArrayList<Volo> voli = getListaVoli();
        ArrayList<Volo> voliFiltrati = new ArrayList<>();
        for (Volo v : voli) {
            if (isPartenza && v instanceof VoloPartenza || !isPartenza && v instanceof VoloArrivo) {
                voliFiltrati.add(v);
            }
        }
        String[][] matriceVoli = new String[voliFiltrati.size()][colonne.length];
        for (int i = 0; i < voliFiltrati.size(); i++)
        {
            Volo v = voliFiltrati.get(i);
            for(int j = 0; j < colonne.length; j++)
            {
                String colonna = colonne[j];
                switch(colonna)
                {
                    case "Codice":
                        matriceVoli[i][j] = v.getCodice();
                        break;
                    case "Compagnia":
                        matriceVoli[i][j] = v.getCompagnia();
                    break;
                    case "Origine":
                        matriceVoli[i][j] = v.getAeroportoOrigine();
                        break;
                    case "Destinazione":
                        matriceVoli[i][j] = v.getAeroportoDestinazione();
                        break;
                    case "Data":
                        matriceVoli[i][j] = v.getDataVolo().toString();
                        break;
                    case "Partenza":
                        matriceVoli[i][j] = v.getOraPartenza().toString();
                        break;
                    case "Arrivo":
                        matriceVoli[i][j] = v.getOraArrivo().toString();
                        break;
                    case "Ritardo":
                        matriceVoli[i][j] = v.getRitardo() + " min";
                        break;
                    case "Posti":
                        matriceVoli[i][j] = String.valueOf(v.getNumPosti());
                        break;
                    case "Stato":
                        matriceVoli[i][j] = String.valueOf(v.getRegistro());
                        break;
                    case "Gate":
                        if (isPartenza && v instanceof VoloPartenza vP ) {
                            if (vP.getGateImbarco() != null) {
                                matriceVoli[i][j] = vP.getGateImbarco();
                            } else {
                                matriceVoli[i][j] = "-";
                            }

                        }else {
                            matriceVoli[i][j] = "-";
                        }
                        break;
                    default:
                        matriceVoli[i][j] = "-";
                        break;
                }
            }
        }
        return matriceVoli;
    }


    /**
     * Is vuoto boolean.
     * <p>
     * Serve per verificare se i campi sono vuoti.
     *
     * @param campi the campi
     * @return un valore booleano
     */
    public boolean isVuoto(String[] campi)
    {
        for (String campo : campi) {
            if (campo == null || campo.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets email login.
     *
     * @return emailLogin email login
     */
    public String getEmailLogin()
    {
        return emailLogin;
    }

    /**
     * addPrenotazione void
     * <p>
     * Questo metodo viene utilizzato per aggiungere una prenotazione nel DB, prima di inserire la prenotazione viene generato:<p>
     * - il numero della prenotazione dalla classe {@code PrenotazioneDAOImpl} con il metodo
     * {@code generaCodicePrenotazione()}; <p>
     * - un {@code ArrayList<String>} di tutti i posti liberi per quel volo sempre con la stessa classe DAO utilizzando il metodo {@code generaPostiLiberi()}; <p>
     * - un posto preso dall' {@code ArrayList<String>} precedente in modo casuale.
     *
     * @param cf     the cf
     * @param codice the codice
     * @throws SQLException the sql exception
     */
    public void addPrenotazione (String cf,String codice) throws SQLException
    {


        String numPrenotazione = prenotazioneDAO.generaCodicePrenotazione();


        ArrayList<String> postiDisponibili = prenotazioneDAO.generaPostiLiberi(codice,postiTotali);
        String posto = postiDisponibili.get(rand.nextInt(postiDisponibili.size()));
        prenotazioneDAO.addPrenotazione(posto,cf,emailLogin,numPrenotazione,codice);
    }

    /**
     * addPasseggero void
     * <p>
     * Questo metodo aggiunge un passeggero nel DB.
     *
     * @param nome         the nome
     * @param cognome      the cognome
     * @param numDocumento the num documento
     * @param cf           the cf
     * @param dataNascita  the data nascita
     */
    public void addPasseggero (String nome, String cognome, String numDocumento, String cf,String dataNascita) {
        LocalDate dataNascitaSQL = LocalDate.parse(dataNascita, formatter);
        passeggeroDAO.addPasseggero(nome,cognome,numDocumento,cf,dataNascitaSQL);
    }

    /**
     * addDocumento void
     * <p>
     * Questo metodo aggiunge un documento nel DB, prima di essere aggiunto controlla se deve aggiungere come tipo un passaporto o una carta d'identità: <p>
     * <p>
     * {@code isPassaporto} = true: aggiunge un passaporto; <p>
     * {@code isPassaporto} = false: aggiunge una carta d'identità.
     *
     * @param numDocumento  the num documento
     * @param tipo          the tipo
     * @param dataEmissione the data emissione
     * @param dataScadenza  the data scadenza
     */
    public void addDocumento(String numDocumento,  String tipo, String dataEmissione,String dataScadenza)
    {

        LocalDate dataEmissioneSQL = LocalDate.parse(dataEmissione, formatter);
        LocalDate dataScadenzaSQL = LocalDate.parse(dataScadenza, formatter);
        boolean isPassaporto;
        if(tipo.equals("Passaporto"))
        {
            isPassaporto = true;
        }else{
            isPassaporto = false;
        }
        documentoDAO.addDocumento(numDocumento, isPassaporto, dataEmissioneSQL, dataScadenzaSQL);

    }

    /**
     * Cerca prenotazioni array list.
     * Questo metodo cerca nel DB le prenotazioni che soddisfano il parametro {@code ricerca}. Se nessuna prenotazione viene trovata, lancia un eccezione per avvertire
     * l'utente che non è stata trovata nessuna prenotazione.
     *
     * @param ricerca the ricerca
     * @return the array list
     * @throws IllegalArgumentException quando nessuna prenotazione viene trovata.
     */
    public ArrayList<String> cercaPrenotazioni(String ricerca) throws IllegalArgumentException {
        ArrayList<String> nome = new ArrayList<>();
        ArrayList<String> cognome = new ArrayList<>();
        ArrayList<String> numPrenotazione = new ArrayList<>();
        ArrayList<String> posto = new ArrayList<>();
        ArrayList<String> stato = new ArrayList<>();
        ArrayList<String> codiceVolo = new ArrayList<>();
        ArrayList<String> cf= new ArrayList<>();
        prenotazioneDAO.cercaPrenotazioni(nome,cognome,emailLogin,ricerca,numPrenotazione,posto,stato,codiceVolo,cf);
        if(numPrenotazione.isEmpty())
        {
           throw new IllegalArgumentException("Nessuna prenotazione trovata.");
        }
        ArrayList<String> prenotazioniUtenteToString = new ArrayList<>();
        ArrayList<Prenotazione> prenotazioniUtente = new ArrayList<>();
        for(int i = 0; i<codiceVolo.size(); i++)
        {
            StatoPrenotazione statoPrenotazione = StatoPrenotazione.valueOf(stato.get(i));
            Prenotazione prenotazione = new Prenotazione(nome.get(i), cognome.get(i),numPrenotazione.get(i),posto.get(i),statoPrenotazione,codiceVolo.get(i),cf.get(i));
            prenotazioniUtente.add(prenotazione);
        }
        for (Prenotazione prenotazione : prenotazioniUtente)
        {
            prenotazioniUtenteToString.add(prenotazione.toStringPrenotazione());
        }
        return prenotazioniUtenteToString;
    }

    /**
     * Elimina i voli scaduti.
     */
    public void eliminaVoliScaduti()
    {
        voliDAO.eliminaVoliScaduti();
    }

    /**
     * Gets prenotazioni utenti.<p>
     * Questo metodo genera le prenotazioni per gli amministratori.
     *
     * @param isNuove serve per poter distinguere le prenotazioni nuove da quelle già nel sistema.
     * @return the prenotazioni utenti
     */
    public ArrayList<String> getPrenotazioniUtenti(boolean isNuove)
    {
        ArrayList<String> nome = new ArrayList<>();
        ArrayList<String> cognome = new ArrayList<>();
        ArrayList<String> numPrenotazione = new ArrayList<>();
        ArrayList<String> posto = new ArrayList<>();
        ArrayList<String> stato = new ArrayList<>();
        ArrayList<String> codiceVolo = new ArrayList<>();

        ArrayList<String> cf= new ArrayList<>();
        prenotazioneDAO.getPrenotazioni(nome,cognome,numPrenotazione,posto,stato,codiceVolo,cf);
        ArrayList<String> prenotazione = new ArrayList<>();
        for(int i = 0; i<codiceVolo.size(); i++)
        {
            StatoPrenotazione stati = StatoPrenotazione.valueOf(stato.get(i));
            Prenotazione p = new Prenotazione(nome.get(i),cognome.get(i),numPrenotazione.get(i),posto.get(i),stati,codiceVolo.get(i),cf.get(i));
            if(isNuove && stati == StatoPrenotazione.In_Attesa || !isNuove && stati == StatoPrenotazione.Confermata)
            {
                prenotazione.add(p.toStringPrenotazione());
            }
        }
        return prenotazione;
    }

    /**
     * Conferma prenotazione. <p>
     * Con questo metodo, gli amministratori possono cambiare lo stato della prenotazione in "Confermata" oppure "Cancellata"
     *
     * @param isConfermata    serve per cambiare lo stato della prenotazione in "Confermata" o "Cancellata".
     * @param numPrenotazione the num prenotazione
     */
    public void confermaPrenotazione(boolean isConfermata,String numPrenotazione)
    {
        prenotazioneDAO.confermaPrenotazione(numPrenotazione,isConfermata);

    }

    /**
     * Update prenotazione.
     *
     * @param nome    the nome
     * @param cognome the cognome
     * @param email   the email
     * @param codice  the codice
     * @param cf      the cf
     */
    public void updatePrenotazione(String nome, String cognome, String email,String codice,String cf)
    {
        prenotazioneDAO.updatePrenotazione(nome,cognome,email,codice,cf);
    }

    /**
     * Delete prenotazione.
     *
     * @param email  the email
     * @param codice the codice
     * @param cf     the cf
     */
    public void deletePrenotazione(String email,String codice,String cf)
    {

            prenotazioneDAO.cancellaPrenotazione(email,codice,cf);
    }

    /**
     * Gets gate.
     *
     * @param gate the gate
     */
    public void getGate(ArrayList<String> gate)
    {
        gateDAO.getGate(gate);
    }

    /**
     * Update volo.
     *
     * @param isPartenza the is partenza
     * @param codice     the codice
     * @param compagnia  the compagnia
     * @param data       the data
     * @param arrivo     the arrivo
     * @param partenza   the partenza
     * @param stato      the stato
     * @param ritardo    the ritardo
     * @param gate       the gate
     */
    public void updateVolo(boolean isPartenza,String codice, String compagnia,LocalDate data,LocalTime arrivo,LocalTime partenza,String stato,int ritardo,String gate)
    {

        voliDAO.updateVoli(isPartenza,codice,compagnia,data,arrivo,partenza,stato,ritardo,gate);

    }

    /**
     * Voli filtrati array list.<p>
     *In base a vari parametri, filtra i voli e crea un nuovo ArrayList della classe Volo.
     * @param codice    the codice
     * @param compagnia the compagnia
     * @param stato     the stato
     * @return the array list
     */
    public ArrayList<Volo>voliFiltrati(String codice, String compagnia, String stato)
    {
        ArrayList<String> codici = new ArrayList<>();
        ArrayList<String> compagnie = new ArrayList<>();
        ArrayList<String> origini = new ArrayList<>();
        ArrayList<String> destinazioni = new ArrayList<>();
        ArrayList<String> gates = new ArrayList<>();
        ArrayList<LocalDate> dateVoli = new ArrayList<>();
        ArrayList<LocalTime> orePartenza = new ArrayList<>();
        ArrayList<LocalTime> oreArrivi = new ArrayList<>();
        ArrayList<String> stati = new ArrayList<>();
        ArrayList<Integer> posti = new ArrayList<>();
        ArrayList<Integer> ritardi = new ArrayList<>();
        voliDAO.getVoliFiltrati(codice,compagnia,stato,codici,compagnie,origini,destinazioni,gates,dateVoli,orePartenza,oreArrivi,stati,posti,ritardi);
        ArrayList<Volo> listaVoli = new ArrayList<>();

        for (int i = 0; i < codici.size(); i++) {
            StatoVolo statoVolo= StatoVolo.valueOf(stati.get(i));

            if ("NAP".equalsIgnoreCase(origini.get(i))) {
                VoloPartenza v = new VoloPartenza(codici.get(i), compagnie.get(i), origini.get(i), destinazioni.get(i),
                        dateVoli.get(i), orePartenza.get(i), oreArrivi.get(i),
                        ritardi.get(i), posti.get(i), statoVolo, gates.get(i));
                listaVoli.add(v);
            } else {
                VoloArrivo v = new VoloArrivo(codici.get(i), compagnie.get(i), origini.get(i), destinazioni.get(i),
                        dateVoli.get(i), orePartenza.get(i), oreArrivi.get(i),
                        ritardi.get(i), posti.get(i), statoVolo);
                listaVoli.add(v);
            }
        }
        return listaVoli;
    }

    /**
     * Cerca voli.
     *
     * @param partenza  the partenza
     * @param arrivo    the arrivo
     * @param codice    the codice
     * @param compagnia the compagnia
     * @param stato     the stato
     */
    public void cercaVoli(ArrayList<String> partenza,ArrayList<String> arrivo,String codice, String compagnia, String stato)
    {
        ArrayList<Volo>voliFiltrati = voliFiltrati(codice,compagnia,stato);
        if(voliFiltrati.isEmpty())
        {
            throw new IllegalArgumentException("Nessun volo trovato con questi criteri.");
        }
        for (Volo v : voliFiltrati) {
        if (v instanceof VoloPartenza) {
            partenza.add(v.toString());
        } else if (v instanceof VoloArrivo) {
            arrivo.add(v.toString());
        }
    }



    }

    /**
     * Gets passeggero.<p>
     *     Serve per creare un arrayList della classe Passeggero in modo da poter efettuare il toString negli altri metodi.
     *
     * @return the passeggero
     */
    public ArrayList<Passeggero> getPasseggero()
    {
        ArrayList<String> nomePasseggero = new ArrayList<>();
        ArrayList<String> cognomePasseggero = new ArrayList<>();
        ArrayList<String> numDocumento = new ArrayList<>();
        ArrayList<LocalDate> dataNascita = new ArrayList<>();
        ArrayList<String> cf = new ArrayList<>();
        passeggeroDAO.getAllPasseggero(nomePasseggero,cognomePasseggero,numDocumento,cf,dataNascita);
        ArrayList<Passeggero> listaPasseggero = new ArrayList<>();
        for (int i = 0; i < nomePasseggero.size(); i++) {
            Passeggero passeggero = new Passeggero(nomePasseggero.get(i),cognomePasseggero.get(i),numDocumento.get(i),cf.get(i),dataNascita.get(i));
            listaPasseggero.add(passeggero);


        }
        return listaPasseggero;
    }

    /**
     * Lista passeggero array list.<p>
     *     Serve per creare una lista di stringhe dei passeggeri.
     *
     * @return the array list
     */
    public ArrayList<String> listaPasseggero()
    {
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<Passeggero> listaPasseggero = getPasseggero();
        for(Passeggero p : listaPasseggero)
        {
            lista.add(p.toString());
        }
        return lista;
    }

    /**
     * Lista passeggero filtrato array list.<p>
     *     Filtra i passeggeri in base al nome, cognome e codice fiscale.
     *
     * @param nome    the nome
     * @param cognome the cognome
     * @param cf      the cf
     * @return ArrayList dei passeggeri filtrati.
     */
    public ArrayList<String> listaPasseggeroFiltrato(String nome, String cognome, String cf)
    {
        ArrayList<String> nomePasseggero = new ArrayList<>();
        ArrayList<String> cognomePasseggero = new ArrayList<>();
        ArrayList<String> numDocumento = new ArrayList<>();
        ArrayList<LocalDate> dataNascita = new ArrayList<>();
        ArrayList<String> cfSQL = new ArrayList<>();
        ArrayList<String> lista = new ArrayList<>();

            passeggeroDAO.getPasseggeroFiltrato(nome,cognome,cf,nomePasseggero,cognomePasseggero,numDocumento,cfSQL,dataNascita);
            if(nomePasseggero.isEmpty()) {
                throw new IllegalArgumentException("Nessun passeggero con questi criteri.");
            }

            ArrayList<Passeggero> listaPasseggeroFiltrato = new ArrayList<>();
            for(int i = 0; i < nomePasseggero.size(); i++)
            {
                Passeggero passeggero = new Passeggero(nomePasseggero.get(i),cognomePasseggero.get(i),numDocumento.get(i),cfSQL.get(i),dataNascita.get(i));
                listaPasseggeroFiltrato.add(passeggero);
            }

            for(Passeggero p : listaPasseggeroFiltrato)
            {
                lista.add(p.toString());
            }
            return lista;
    }

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isEmailValid(String email)
    {
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return false;
        }
        String dominio = email.substring(atIndex + 1).toLowerCase();

       if(dominio.equals("gmail.com") || dominio.equals("email.com"))
       {
           return true;
       }
       return false;

    }

    public void liberaGate(String codice) throws IllegalArgumentException {
        try{
            gateDAO.liberaGate(codice);
        }catch (IllegalArgumentException e){
            throw e;
        }
    }

    }



