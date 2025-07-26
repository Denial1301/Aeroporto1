package implementazionePostgresDAO;

import dao.PrenotazioneDAO;
import db.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PrenotazioneDAOImpl implements PrenotazioneDAO {
    private Connection connection;
    public PrenotazioneDAOImpl() {

        try {
            connection = ConnessioneDatabase.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPrenotazioni(ArrayList<String> nome,ArrayList<String> cognome,ArrayList<String> numPrenotazione, ArrayList<String> posto, ArrayList<String> stato,
                                 ArrayList<String> codiceVolo, ArrayList<String> cf) {
        String sql = "SELECT P.*,PA.nome,PA.cognome FROM prenotazioni P JOIN Passeggero PA ON PA.cf=P.CF ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
            nome.add(rs.getString("nome"));
            cognome.add(rs.getString("cognome"));
            numPrenotazione.add(rs.getString("n_prenotazioni"));
            posto.add(rs.getString("posto"));
            stato.add(rs.getString("stato_prenotazioni"));

            codiceVolo.add(rs.getString("codice"));
            cf.add(rs.getString("cf"));

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cercaPrenotazioni(ArrayList<String> nome,ArrayList<String> cognome,String email,String ricerca, ArrayList<String> numPrenotazione, ArrayList<String> posto,ArrayList<String> stato,
                                  ArrayList<String> codiceVolo,ArrayList<String> cf)
    {
    String sql;

    try{
        PreparedStatement ps;

        if(ricerca.isEmpty())
        {
            sql =   "SELECT P.n_prenotazioni, P.posto,P.stato_prenotazioni,P.codice,P.cf,PA.nome, PA.cognome FROM PRENOTAZIONI p " +
                    " JOIN PASSEGGERO PA ON P.cf = PA.cf" +
                    " JOIN VOLO V ON P.codice = V.codice"+
                    " WHERE P.email_utente = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);

        } else {
            sql = "SELECT P.n_prenotazioni, P.posto, P.stato_prenotazioni, P.codice, P.cf, PA.nome, PA.cognome " +
                    " FROM PRENOTAZIONI P " +
                    " JOIN PASSEGGERO PA ON P.cf = PA.cf " +
                    " JOIN VOLO V ON P.codice = V.codice " +
                    " WHERE (LOWER(PA.nome) LIKE LOWER(?) OR LOWER(V.codice) = LOWER(?)) " +
                    " AND P.email_utente = ?";

            ps = connection.prepareStatement(sql);
            ps.setString(1, ricerca);
            ps.setString(2, ricerca);
            ps.setString(3, email);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            nome.add(rs.getString("nome"));
            cognome.add(rs.getString("cognome"));
            numPrenotazione.add(rs.getString("n_prenotazioni"));
            posto.add(rs.getString("posto"));
            stato.add(rs.getString("stato_prenotazioni"));
            codiceVolo.add(rs.getString("codice"));
            cf.add(rs.getString("cf"));
        }

        rs.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    }
    @Override
    public void getVoliPrenotati(String emailDaCercare, ArrayList<String> codice) {
        String sql = "SELECT DISTINCT  V.codice FROM prenotazioni P JOIN Volo V ON V.codice = P.codice  WHERE email_utente = ? and stato_prenotazioni <> 'Cancellata'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, emailDaCercare);

            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
            codice.add(rs.getString("codice"));
            }
            rs.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public String generaCodicePrenotazione() {
        String sql = "SELECT n_prenotazioni FROM PRENOTAZIONI WHERE n_prenotazioni LIKE 'PR%' ORDER BY n_prenotazioni DESC LIMIT 1";
        String nuovaPrenotazione = "PR000";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String ultimo = rs.getString("n_prenotazioni");
                int numero = Integer.parseInt(ultimo.substring(2));
                numero++;
                nuovaPrenotazione = String.format("PR%03d", numero);
            } else {

                nuovaPrenotazione = "PR001";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nuovaPrenotazione;
    }



    @Override
    public void addPrenotazione(String posto, String cf, String email, String numPrenotazione,String codice) {
        String controllaPosti = "SELECT numeri_posti FROM VOLO WHERE codice = ? ";
        String sql = "INSERT INTO PRENOTAZIONI(cf,email_utente,n_prenotazioni,posto,stato_prenotazioni,codice) VALUES(?,?,?,?,?::stato_prenotazione,?)";
        String updatePosti = "UPDATE volo SET numeri_posti = numeri_posti - 1 WHERE codice = ?";


        try{
            PreparedStatement psControllaPosti = connection.prepareStatement(controllaPosti);
            psControllaPosti.setString(1, codice);
            ResultSet rs = psControllaPosti.executeQuery();
            if(rs.next()){
                int disponibili = rs.getInt("numeri_posti");
                if(disponibili <= 0)
                {
                    throw new SQLException("posti terminatti per il volo: " + codice);
                }
            }
            PreparedStatement psUpdate = connection.prepareStatement(updatePosti);

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, email);
            ps.setString(3, numPrenotazione);
            ps.setString(4, posto);
            ps.setString(5, "In_Attesa");
            ps.setString(6, codice);

            psUpdate.setString(1, codice);
            ps.executeUpdate();
            psUpdate.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<String> generaPostiLiberi(String codice,int postiTotali) {
        ArrayList<String> tuttiPosti = new ArrayList<>();
        char fila = 'A';
        for (int i = 0; i<2; i++) {
            fila+= (char) i;
            for (int numero = 1; numero <= postiTotali; numero++) {
                tuttiPosti.add(fila+String.valueOf(numero));
            }
        }


        ArrayList<String> occupati = new ArrayList<>();

        String sql = "SELECT posto FROM PRENOTAZIONI WHERE codice = ?";
        try  {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, codice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                occupati.add(rs.getString("posto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<String> disponibili = new ArrayList<>();
        for (String posto : tuttiPosti) {
            if (!occupati.contains(posto)) {
                disponibili.add(posto);
            }
        }
        return disponibili;
    }

    @Override
    public void cancellaPrenotazione(String email, String codice, String cf)
    {
        String sql = "DELETE FROM PRENOTAZIONI P USING PASSEGGERO PA WHERE P.cf=PA.cf AND PA.cf = ? AND P.codice = ? AND P.email_utente = ?";
        String postiUpdate = "UPDATE VOLO SET numeri_posti = numeri_posti +1 WHERE codice = ?";
        String contaPasseggero = "SELECT COUNT(*) FROM PRENOTAZIONI WHERE cf = ?";
        String eliminaPasseggero = "DELETE FROM PASSEGGERO WHERE cf = ?";

        try {
            PreparedStatement psUpdate = connection.prepareStatement(postiUpdate);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, codice);
            ps.setString(3, email);
            int righeEliminate = ps.executeUpdate();
            psUpdate.setString(1, codice);
            psUpdate.executeUpdate();
            if(righeEliminate == 0)
            {
                throw new SQLException("Errore nella cancellazione della prenotazione.");
            }
            PreparedStatement psConta = connection.prepareStatement(contaPasseggero);
            psConta.setString(1, cf);
            ResultSet rs = psConta.executeQuery();
            if(rs.next() && rs.getInt(1) == 0)
            {
                PreparedStatement psElimina = connection.prepareStatement(eliminaPasseggero);
                psElimina.setString(1, cf);
                psElimina.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        }

    @Override
    public void confermaPrenotazione(String numPrenotazione, boolean isConfermata)
    {
        String sql;
        if(isConfermata)
        {
            sql = "UPDATE prenotazioni p  SET stato_prenotazioni = 'Confermata'  FROM utenti u  WHERE p.email_utente = u.email  AND p.n_prenotazioni = ?";

        }
        else
        {
            sql = "UPDATE prenotazioni p  SET stato_prenotazioni = 'Cancellata'  FROM utenti u  WHERE p.email_utente = u.email  AND p.n_prenotazioni = ?";

        }
        try{

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, numPrenotazione);
            ps.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePrenotazione(String cf,String cfVecchio)
    {

        String sql;

        try {

            sql = "UPDATE PRENOTAZIONI SET cf = ? WHERE cf = ?";
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, cf);
            ps.setString(2, cfVecchio);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}




