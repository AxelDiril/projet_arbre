package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class M_Evenement {
    private Db_mariadb db;
    private int iIdEvenement;
    private LocalDate dDate;
    private int iIdTypeEvenement;
    private String strLieu;

    public M_Evenement(Db_mariadb db, int iIdEvenement) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM EVENEMENTS WHERE id_evenement = " + iIdEvenement;
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.iIdEvenement = iIdEvenement;
            this.dDate = res.getObject("date", LocalDate.class);
            this.iIdTypeEvenement = res.getInt("id_type_evenement");
            this.strLieu = res.getString("lieu");
        }
    }

    public M_Evenement(Db_mariadb db, LocalDate dDate, int iIdTypeEvenement, String strLieu)
            throws SQLException {
        this.db = db;
        this.dDate = dDate;
        this.iIdTypeEvenement = iIdTypeEvenement;
        this.strLieu = strLieu;

        String strSql = "INSERT INTO EVENEMENTS (date, id_type_evenement, lieu) VALUES ('" +
                dDate + "', '" + iIdTypeEvenement + "', '" + strLieu + "')";
        
        db.sqlExec(strSql);
        
        ResultSet res = db.sqlLastId();
        
        if (res.first()) {
            this.iIdEvenement = res.getInt(1);
        }
    }

    public int getIdEvenement() {
        return iIdEvenement;
    }

    public LocalDate getDate() {
        return dDate;
    }

    public int getIdTypeEvenement() {
        return iIdTypeEvenement;
    }

    public String getLieu() {
        return strLieu;
    }

    public void setDate(LocalDate dDate) {
        this.dDate = dDate;
    }

    public void setIdTypeEvenement(int iIdTypeEvenement) {
        this.iIdTypeEvenement = iIdTypeEvenement;
    }

    public void setLieu(String strLieu) {
        this.strLieu = strLieu;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE EVENEMENTS SET " +
                "date = " + dDate + ", " + "id_type_evenement = " + 
                iIdTypeEvenement + ", lieu = " + strLieu +
                " WHERE id_evenement = " + iIdEvenement;
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM EVENEMENTS WHERE id_evenement = "
                + iIdEvenement;
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<Integer, M_Evenement> getRecords(Db_mariadb db) 
            throws SQLException {
        LinkedHashMap<Integer, M_Evenement> lhmLesEvenements = new LinkedHashMap<>();
        
        String strSql = "SELECT id_evenement FROM EVENEMENTS ORDER BY date";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            int iIdEvenement = res.getInt("id_evenement");
            M_Evenement unEvenement = new M_Evenement(db, iIdEvenement);
            lhmLesEvenements.put(iIdEvenement, unEvenement);
        }
        
        return lhmLesEvenements;
    }

    public static LinkedHashMap<Integer, M_Evenement> getEvenementPourIndividu
        (Db_mariadb db, int iIdIndividu) throws SQLException {
        LinkedHashMap<Integer, M_Evenement> lhmLesEvenements = new LinkedHashMap<>();

        String strSql = "SELECT e.id_evenement " +
                     "FROM EVENEMENTS e " +
                     "JOIN AVOIR_EVENEMENT ae ON e.id_evenement = ae.id_evenement " +
                     "WHERE ae.id_individu = " + iIdIndividu + " " +
                     "ORDER BY e.date";

        ResultSet res = db.sqlSelect(strSql);

        while (res.next()) {
            int iIdEvenement = res.getInt("id_evenement");
            M_Evenement unEvenement = new M_Evenement(db, iIdEvenement);
            lhmLesEvenements.put(iIdEvenement, unEvenement);
        }

        return lhmLesEvenements;
    }
    
    @Override
    public String toString() {
        return "M_Evenement{" +
                "idEvenement=" + iIdEvenement +
                ", date=" + dDate +
                ", idTypeEvenement=" + iIdTypeEvenement +
                ", lieu='" + strLieu + '\'' +
                '}';
    }
}
