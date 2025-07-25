package implementazionePostgresDAO;

import dao.UtenteDAO;
import db.ConnessioneDatabase;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.*;

public class UtenteDAOImpl implements UtenteDAO {
    private Connection connection;


    public UtenteDAOImpl() {

        try {
            connection = ConnessioneDatabase.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUtente(String email, String nome, String cognome, String password, LocalDate dataNascita) throws IllegalArgumentException {

        String checkEmail = "SELECT 1 FROM utenti WHERE email = ?";
        try {
            PreparedStatement psCheck = connection.prepareStatement(checkEmail);
            psCheck.setString(1, email);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                throw new IllegalArgumentException("L'email è già registrata.");
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        String sql = "INSERT INTO utenti(email,nome,cognome,password,data_nascita) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, nome);
            ps.setString(3, cognome);
            ps.setString(4, password);
            ps.setDate(5,Date.valueOf(dataNascita));
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean getUtenteByEmailAndPassword(String email, String password) {

        String sql;
        if(email.contains("amministratoreaeroportonapoli")) {
            sql = "SELECT * FROM amministratori WHERE email = ? AND password = ?";
        }else{
            sql = "SELECT email, password FROM utenti WHERE email = ? AND password = ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    return true;
                } else {

                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}



