package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class M_Evenement {
    private Db_mariadb db;
    private int idEvenement;
    private LocalDate date;
    private int idTypeEvenement;
    private String lieu; // <-- changement ici

    // Constructeur depuis BDD
    public M_Evenement(Db_mariadb db, int idEvenement) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM EVENEMENTS WHERE id_evenement = " + idEvenement;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.idEvenement = idEvenement;
            this.date = res.getObject("date", LocalDate.class);
            this.idTypeEvenement = res.getInt("id_type_evenement");
            this.lieu = res.getString("lieu"); // <-- ici aussi
        }
    }

    // Constructeur d’insertion
    public M_Evenement(Db_mariadb db, LocalDate date, int idTypeEvenement, String lieu) throws SQLException {
        this.db = db;
        this.date = date;
        this.idTypeEvenement = idTypeEvenement;
        this.lieu = lieu;

        String sql = "INSERT INTO EVENEMENTS (date, id_type_evenement, lieu) VALUES (" +
                (date != null ? "'" + date + "'" : "NULL") + ", " +
                idTypeEvenement + ", " +
                (lieu != null ? "'" + lieu.replace("'", "''") + "'" : "NULL") + ")";
        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.idEvenement = res.getInt(1);
        }
    }

    // Getters
    public int getIdEvenement() {
        return idEvenement;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getIdTypeEvenement() {
        return idTypeEvenement;
    }

    public String getLieu() {
        return lieu;
    }

    // Setters
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setIdTypeEvenement(int idTypeEvenement) {
        this.idTypeEvenement = idTypeEvenement;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    // Mise à jour
    public void update() throws SQLException {
        String sql = "UPDATE EVENEMENTS SET " +
                "date = " + (date != null ? "'" + date + "'" : "NULL") + ", " +
                "id_type_evenement = " + idTypeEvenement + ", " +
                "lieu = " + (lieu != null ? "'" + lieu.replace("'", "''") + "'" : "NULL") +
                " WHERE id_evenement = " + idEvenement;
        db.sqlExec(sql);
    }

    // Suppression
    public void delete() throws SQLException {
        String sql = "DELETE FROM EVENEMENTS WHERE id_evenement = " + idEvenement;
        db.sqlExec(sql);
    }

    public static LinkedHashMap<Integer, M_Evenement> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Evenement> evenements = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT id_evenement FROM EVENEMENTS ORDER BY date");
        while (res.next()) {
            int id = res.getInt("id_evenement");
            M_Evenement e = new M_Evenement(db, id);
            evenements.put(id, e);
        }
        return evenements;
    }

    public static LinkedHashMap<Integer, M_Evenement> getEvenementPourIndividu(Db_mariadb db, int idMembre) throws SQLException {
        LinkedHashMap<Integer, M_Evenement> lesEvenements = new LinkedHashMap<>();

        String sql = "SELECT e.id_evenement " +
                     "FROM EVENEMENTS e " +
                     "JOIN AVOIR_EVENEMENT ae ON e.id_evenement = ae.id_evenement " +
                     "WHERE ae.id_membre = " + idMembre + " " +
                     "ORDER BY e.date";

        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            int id = res.getInt("id_evenement");
            M_Evenement unEvenement = new M_Evenement(db, id);
            lesEvenements.put(id, unEvenement);
        }

        return lesEvenements;
    }

    
    @Override
    public String toString() {
        return "M_Evenement{" +
                "idEvenement=" + idEvenement +
                ", date=" + date +
                ", idTypeEvenement=" + idTypeEvenement +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
