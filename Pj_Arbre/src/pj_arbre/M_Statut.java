package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Statut {
    private Db_mariadb db;
    private int idStatut;
    private String libelle;

    public M_Statut(Db_mariadb db, int idStatut) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM STATUT WHERE id_statut = " + idStatut;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.idStatut = res.getInt("id_statut");
            this.libelle = res.getString("libelle");
        }
    }

    public M_Statut(Db_mariadb db, String libelle) throws SQLException {
        this.db = db;
        this.libelle = libelle;

        String sql = "INSERT INTO STATUT (libelle) VALUES ('" + libelle + "')";
        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.idStatut = res.getInt(1);
        }
    }

    public int getIdStatut() {
        return idStatut;
    }

    public String getLabel() {
        return libelle;
    }

    public void setLabel(String libelle) {
        this.libelle = libelle;
    }

    public void update() throws SQLException {
        String sql = "UPDATE STATUT SET libelle = '" + libelle + "' WHERE id_statut = " + idStatut;
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM STATUT WHERE id_statut = " + idStatut);
    }

    public static LinkedHashMap<Integer, M_Statut> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Statut> statuts = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM STATUT ORDER BY libelle");
        while (res.next()) {
            M_Statut statut = new M_Statut(db, res.getInt("id_statut"));
            statuts.put(statut.getIdStatut(), statut);
        }
        return statuts;
    }

    @Override
    public String toString() {
        return "M_Statut{" +
                "idStatut=" + idStatut +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
