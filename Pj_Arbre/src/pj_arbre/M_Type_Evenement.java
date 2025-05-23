package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Type_Evenement {
    private Db_mariadb db;
    private int iIdTypeEvenement;
    private String strLibelle;

    public M_Type_Evenement(Db_mariadb db, int iIdTypeEvenement) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM TYPE_EVENEMENT WHERE id_type_evenement = " + iIdTypeEvenement;
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.iIdTypeEvenement = iIdTypeEvenement;
            this.strLibelle = res.getString("libelle");
        }
    }

    public M_Type_Evenement(Db_mariadb db, String strLibelle) throws SQLException {
        this.db = db;
        this.strLibelle = strLibelle;

        String strSql = "INSERT INTO TYPE_EVENEMENT (libelle) VALUES ('" + strLibelle + ")";
        
        db.sqlExec(strSql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.iIdTypeEvenement = res.getInt("id_type_evenement");
        }
    }

    public int getIdTypeEvenement() {
        return iIdTypeEvenement;
    }

    public String getLibelle() {
        return strLibelle;
    }

    public void setLibelle(String strLibelle) {
        this.strLibelle = strLibelle;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE TYPE_EVENEMENT SET libelle = '" + strLibelle +
                " WHERE id_type_evenement = " + iIdTypeEvenement;
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM TYPE_EVENEMENT WHERE id_type_evenement = " + iIdTypeEvenement;
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<Integer, M_Type_Evenement> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Type_Evenement> lhmLesTypesEvenement = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM TYPE_EVENEMENT ORDER BY libelle";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            M_Type_Evenement unTypeEvenement = new M_Type_Evenement(db, res.getInt("id_type_evenement"));
            lhmLesTypesEvenement.put(unTypeEvenement.getIdTypeEvenement(), unTypeEvenement);
        }
        
        return lhmLesTypesEvenement;
    }

    @Override
    public String toString() {
        return "M_TypeEvenement{" +
                "idTypeEvenement=" + iIdTypeEvenement +
                ", libelle='" + strLibelle +
                '}';
    }
}
