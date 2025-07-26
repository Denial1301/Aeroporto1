package implementazionePostgresDAO;


import dao.VoliDAO;
import db.ConnessioneDatabase;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;

public class VoliDAOImpl implements VoliDAO {
private Connection connection;

    public VoliDAOImpl() {

    try {
        connection = ConnessioneDatabase.getInstance();
    }catch(SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void getAllVoli(ArrayList<String> codice, ArrayList<String> compagnia, ArrayList<String> origine, ArrayList<String> destinazioni, ArrayList<String> gate,
                           ArrayList<LocalDate> dateVoli, ArrayList<LocalTime> orePartenza,ArrayList<LocalTime> oreArrivi ,ArrayList<String> statoVolo, ArrayList<Integer> posti,
                           ArrayList<Integer> ritardi) {
        String sql ="SELECT * FROM volo ORDER BY codice";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                codice.add(rs.getString("codice"));
                compagnia.add(rs.getString("compagnia"));
                origine.add(rs.getString("aeroporto_origine"));
                destinazioni.add(rs.getString("aeroporto_destinazione"));
                gate.add(rs.getString("gate"));
                dateVoli.add(rs.getDate("data_volo").toLocalDate());
                orePartenza.add(rs.getTime("ora_partenza").toLocalTime());
                oreArrivi.add(rs.getTime("ora_volo").toLocalTime());
                statoVolo.add(rs.getString("stato"));
                posti.add(rs.getInt("numeri_posti"));
                ritardi.add(rs.getInt("ritardo"));
            }

            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void eliminaVoliScaduti() {
        String sql = "SELECT rimuovi_voli_scaduti()"; 
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateVoli(boolean isPartenza, String codice, String compagnia, LocalDate data, LocalTime arrivo, LocalTime partenza, String stato, int ritardo,String gate)
    {
        try {
            PreparedStatement ps;
            String sql = "";


            if (stato.equals("Programmato") || stato.equals("Decollato") || stato.equals("Atterrato")) {
                if (gate.equals("-")) {

                    sql = "UPDATE VOLO SET compagnia = ?, data_volo = ?, ora_volo = ?, ora_partenza = ?, stato = ?::stato_volo, ritardo = ? WHERE codice = ?";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, compagnia);
                    ps.setDate(2, Date.valueOf(data));
                    ps.setTime(3, Time.valueOf(arrivo));
                    ps.setTime(4, Time.valueOf(partenza));
                    ps.setString(5, stato);
                    ps.setInt(6, ritardo);
                    ps.setString(7, codice);
                } else {
                    sql = "UPDATE VOLO SET compagnia = ?, data_volo = ?, ora_volo = ?, ora_partenza = ?, stato = ?::stato_volo, ritardo = ?, gate = ? WHERE codice = ?";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, compagnia);
                    ps.setDate(2, Date.valueOf(data));
                    ps.setTime(3, Time.valueOf(arrivo));
                    ps.setTime(4, Time.valueOf(partenza));
                    ps.setString(5, stato);
                    ps.setInt(6, ritardo);
                    ps.setString(7, gate);
                    ps.setString(8, codice);
                }
                ps.executeUpdate();



            } else if (stato.equals("Cancellato")) {
                sql = "UPDATE VOLO SET stato = ?::stato_volo WHERE codice = ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, stato);
                ps.setString(2, codice);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getVoliFiltrati(String codice, String compagnia, String stato,
                                ArrayList<String> codici, ArrayList<String> compagnie, ArrayList<String> origini,
                                ArrayList<String> destinazioni, ArrayList<String> gates,
                                ArrayList<LocalDate> dateVoli, ArrayList<LocalTime> orePartenza,
                                ArrayList<LocalTime> oreArrivi, ArrayList<String> stati,
                                ArrayList<Integer> posti, ArrayList<Integer> ritardi) {

        String sql = "SELECT codice, compagnia, aeroporto_origine, aeroporto_destinazione, data_volo, ora_volo, stato, ora_partenza, gate, numeri_posti, ritardo FROM volo";

        ArrayList<String> conditions = new ArrayList<>();

        if (codice != null && !codice.isEmpty()) {
            conditions.add("codice = '" + codice + "'");
        }
        if (compagnia != null && !compagnia.isEmpty()) {
            conditions.add("compagnia = '" + compagnia + "'");
        }
        if (stato != null && !stato.equals("-")) {
            conditions.add("stato = '" + stato + "'");
        }

        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                codici.add(rs.getString("codice"));
                compagnie.add(rs.getString("compagnia"));
                origini.add(rs.getString("aeroporto_origine"));
                destinazioni.add(rs.getString("aeroporto_destinazione"));
                dateVoli.add(rs.getDate("data_volo").toLocalDate());
                orePartenza.add(rs.getTime("ora_partenza").toLocalTime());
                oreArrivi.add(rs.getTime("ora_volo").toLocalTime());
                stati.add(rs.getString("stato"));
                gates.add(rs.getString("gate"));
                posti.add(rs.getInt("numeri_posti"));
                ritardi.add(rs.getInt("ritardo"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addVoli(String codice, String compagnia, String origine, String destinazione, LocalDate dataVolo,
                        LocalTime oraPartenza, LocalTime oraArrivo, String stato, int posti, int ritardo) throws IllegalArgumentException {
        String sql = "INSERT INTO VOLO (codice, compagnia, aeroporto_origine, aeroporto_destinazione, data_volo, ora_volo, ora_partenza, stato, numeri_posti, ritardo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?::stato_volo, ?, ?)";
        String checkCodice = "SELECT 1 FROM VOLO WHERE codice = ?";

        try {
            PreparedStatement psCodice = connection.prepareStatement(checkCodice);
            psCodice.setString(1, codice);
            ResultSet rsCodice = psCodice.executeQuery();
            if(rsCodice.next()) {
                throw new IllegalArgumentException("Codice già presente nel sistema.");

            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, codice);
            ps.setString(2, compagnia);
            ps.setString(3, origine);
            ps.setString(4, destinazione);
            ps.setDate(5, java.sql.Date.valueOf(dataVolo));
            ps.setTime(6, java.sql.Time.valueOf(oraArrivo));
            ps.setTime(7, java.sql.Time.valueOf(oraPartenza));
            ps.setString(8, stato);
            ps.setInt(9, posti);
            ps.setInt(10, ritardo);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
