package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Statut {
    private Db_mariadb db;
    private int idStatut;
    private String label;

    // Constructeur de récupération depuis la BDD
    public M_Statut(Db_mariadb db, int idStatut) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM STATUT WHERE id_statut = " + idStatut;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.idStatut = res.getInt("id_statut");
            this.label = res.getString("label");
        }
    }

    // Constructeur d’insertion
    public M_Statut(Db_mariadb db, String label) throws SQLException {
        this.db = db;
        this.label = label;

        String sql = "INSERT INTO STATUT (label) VALUES ('" + label + "')";
        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.idStatut = res.getInt(1);
        }
    }

    // Getters
    public int getIdStatut() {
        return idStatut;
    }

    public String getLabel() {
        return label;
    }

    // Setter uniquement pour label
    public void setLabel(String label) {
        this.label = label;
    }

    // Mise à jour
    public void update() throws SQLException {
        String sql = "UPDATE STATUT SET label = '" + label + "' WHERE id_statut = " + idStatut;
        db.sqlExec(sql);
    }

    // Suppression
    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM STATUT WHERE id_statut = " + idStatut);
    }

    // Récupération de tous les statuts
    public static LinkedHashMap<Integer, M_Statut> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Statut> statuts = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM STATUT ORDER BY label");
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
                ", label='" + label + '\'' +
                '}';
    }
}
