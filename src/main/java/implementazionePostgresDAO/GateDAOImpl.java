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
        String sql = "SELECT g.gate_id, v.codice " +
                "FROM gate g LEFT JOIN volo v ON v.gate = g.gate_id " +
                "ORDER BY g.gate_id ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String gateId = rs.getString("gate_id");
                String codiceVolo = rs.getString("codice");
                if (codiceVolo == null) {
                    codiceVolo = "-";
                }
                // esempio: "A7(ABC123)" oppure "A5(-)"
                gateList.add(gateId + "(" + codiceVolo + ")");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
