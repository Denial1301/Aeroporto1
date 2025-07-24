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

}
