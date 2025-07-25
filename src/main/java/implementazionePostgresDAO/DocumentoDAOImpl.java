package implementazionePostgresDAO;

import dao.DocumentoDAO;
import db.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;

public class DocumentoDAOImpl implements DocumentoDAO {
    private Connection connection;
    public DocumentoDAOImpl() {
        try{
            connection = ConnessioneDatabase.getInstance();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    @Override
    public void addDocumento(String numDocumento, boolean isPassaporto, LocalDate dataEmissione, LocalDate dataScadenza) {
        String sql = "INSERT INTO documento (numero_documento,tipo,data_emissione,data_scadenza) VALUES (?,?,?,?)";
        String controllo ="SELECT 1 FROM documento WHERE numero_documento=?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            PreparedStatement controlloDocumento = connection.prepareStatement(controllo);
            controlloDocumento.setString(1,numDocumento);
            ResultSet rs = controlloDocumento.executeQuery();
            if(!rs.next())
            {
                ps.setString(1, numDocumento);
                ps.setBoolean(2, isPassaporto);
                ps.setDate(3, Date.valueOf(dataEmissione));
                ps.setDate(4, Date.valueOf(dataScadenza));
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateDocumento(String numDoc, LocalDate dataEmissione, LocalDate dataScadenza,String cfVecchio) {

        String sql = "UPDATE DOCUMENTO  SET numero_documento = ?, data_emissione = ?, data_scadenza = ?  WHERE numero_documento = (SELECT numero_documento FROM PASSEGGERO " +
                " WHERE cf = ?) ";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, numDoc);
            ps.setDate(2, Date.valueOf(dataEmissione));
            ps.setDate(3, Date.valueOf(dataScadenza));
            ps.setString(4, cfVecchio);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
