package implementazionePostgresDAO;

import dao.GateDAO;
import db.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GateDAOImpl implements GateDAO
{
    private Connection connection;
    public GateDAOImpl()
    {

        try{
            connection = ConnessioneDatabase.getInstance();
        }catch(SQLException e){
            e.getStackTrace();
        }
    }


    @Override

    public void getGate(ArrayList<String> gateList) {
        String sql = "SELECT gate_id " +
                "FROM gate  WHERE stato = 'Libero'  " +
                "ORDER BY gate_id ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String gateId = rs.getString("gate_id");


                gateList.add(gateId);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void liberaGate(String codiceVolo) throws IllegalArgumentException {
        String gate = null;

        String selectSQL = "SELECT gate FROM volo WHERE codice = ?";
        String aggiornaGateSQL = "UPDATE gate SET stato = 'Libero' WHERE gate_id = ?";
        String aggiornaVoloSQL = "UPDATE volo SET gate = NULL WHERE codice = ?";

        try  {
            PreparedStatement psCheck = connection.prepareStatement(selectSQL);
            psCheck.setString(1, codiceVolo);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                gate = rs.getString("gate");
            }
            if (gate == null || gate.isEmpty() || gate.equals("-")) {
                throw new IllegalArgumentException("Errore: il volo non ha un gate assegnato.");
            }
            PreparedStatement ps = connection.prepareStatement(aggiornaGateSQL);
            PreparedStatement ps2 = connection.prepareStatement(aggiornaVoloSQL);
            ps2.setString(1, codiceVolo);
            ps.setString(1, gate);
            ps.executeUpdate();
            ps2.executeUpdate();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }


}
