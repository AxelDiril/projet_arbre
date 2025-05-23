package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Statut {
    private Db_mariadb db;
    private int iIdStatut;
    private String strLibelle;

    public M_Statut(Db_mariadb db, int iIdStatut) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM STATUT WHERE id_statut = " + iIdStatut;
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.iIdStatut = res.getInt("id_statut");
            this.strLibelle = res.getString("libelle");
        }
    }

    public M_Statut(Db_mariadb db, String strLibelle) throws SQLException {
        this.db = db;
        this.strLibelle = strLibelle;

        String strSql = "INSERT INTO STATUT (libelle) VALUES ('" + strLibelle + "')";
        
        db.sqlExec(strSql);

        ResultSet res = db.sqlLastId();
        
        if (res.first()) {
            this.iIdStatut = res.getInt(1);
        }
    }

    public int getIdStatut() {
        return iIdStatut;
    }

    public String getLibelle() {
        return strLibelle;
    }

    public void setLibelle(String libelle) {
        this.strLibelle = libelle;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE STATUT SET libelle = '" + strLibelle +
                "' WHERE id_statut = " + iIdStatut;
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM STATUT WHERE id_statut = " + iIdStatut;
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<Integer, M_Statut> getRecords(Db_mariadb db) 
            throws SQLException {
        LinkedHashMap<Integer, M_Statut> lhmLesStatuts = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM STATUT ORDER BY libelle";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            M_Statut unStatut = new M_Statut(db, res.getInt("id_statut"));
            lhmLesStatuts.put(unStatut.getIdStatut(), unStatut);
        }
        
        return lhmLesStatuts;
    }

    @Override
    public String toString() {
        return "M_Statut{" +
                "idStatut=" + iIdStatut +
                ", libelle='" + strLibelle + '\'' +
                '}';
    }
}
