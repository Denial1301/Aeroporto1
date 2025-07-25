package implementazionePostgresDAO;

import dao.PasseggeroDAO;
import db.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PasseggeroDAOImpl implements PasseggeroDAO {
    private Connection connection;
    public PasseggeroDAOImpl()
    {

        try{
            connection = ConnessioneDatabase.getInstance();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @Override
        public void addPasseggero(String nome, String cognome, String numDocumento, String cf, LocalDate dataNascita)
        {
            String sql = "INSERT INTO PASSEGGERO (nome,cognome,numero_documento,cf,data_nascita) VALUES(?,?,?,?,?)";
            String controllaDuplicato = "SELECT 1 FROM PASSEGGERO WHERE cf=?";
            try{
                PreparedStatement ps = connection.prepareStatement(sql);
                PreparedStatement controllo = connection.prepareStatement(controllaDuplicato);
                controllo.setString(1, cf);
                ResultSet rs = controllo.executeQuery();
                if(!rs.next())
                {

                    ps.setString(1, nome);
                    ps.setString(2, cognome);
                    ps.setString(3, numDocumento);
                    ps.setString(4, cf);
                    ps.setDate(5, Date.valueOf(dataNascita));
                    ps.executeUpdate();
                }


            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void getPasseggeroFiltrato(String nome, String cognome, String cf,ArrayList<String> nomi, ArrayList<String> cognomi,
                                          ArrayList<String> numDocumento, ArrayList<String> cfSQL, ArrayList<LocalDate> dataNascita)
        {
            String sql = "SELECT * FROM PASSEGGERO";

            ArrayList<String> conditions = new ArrayList<>();

            if (nome != null && !nome.isEmpty()) {
                conditions.add("nome = '" + nome + "'");
            }
            if (cognome != null && !cognome.isEmpty()) {
                conditions.add("cognome = '" + cognome + "'");
            }
            if (cf != null && !cf.isEmpty()) {
                conditions.add("cf = '" + cf + "'");
            }

            if (!conditions.isEmpty()) {
                sql += " WHERE " + String.join(" AND ", conditions);
            }

            try (Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    nomi.add(rs.getString("nome"));
                    cognomi.add(rs.getString("cognome"));
                    numDocumento.add(rs.getString("numero_documento"));
                    cfSQL.add(rs.getString("cf"));
                    dataNascita.add(LocalDate.parse(rs.getString("data_nascita")));

                }
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    @Override
    public void getAllPasseggero(ArrayList<String> nome, ArrayList<String> cognome, ArrayList<String> numDocumento, ArrayList<String> cf, ArrayList<LocalDate> dataNascita) {
        String sql = "SELECT  PASSEGGERO.* FROM PASSEGGERO";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                nome.add(rs.getString("nome"));
                cognome.add(rs.getString("cognome"));
                numDocumento.add(rs.getString("numero_documento"));
                cf.add(rs.getString("cf"));
                dataNascita.add(rs.getDate("data_nascita").toLocalDate());

            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updatePasseggero(String nome, String cognome, String cf, String numDoc, LocalDate dataNascita, LocalDate dataEmissione, LocalDate dataScadenza, String email, String codice, String cfVecchio) {
        String sql;

        try {

            sql = "UPDATE passeggero SET nome = ?, cognome = ?,cf = ?,data_nascita = ? WHERE cf = ?";
            PreparedStatement ps= connection.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, cf);

            ps.setDate(4, Date.valueOf(dataNascita));
            ps.setString(5,cfVecchio);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
