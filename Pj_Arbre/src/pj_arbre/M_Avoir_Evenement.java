package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class M_Avoir_Evenement {
    private Db_mariadb db;
    private int idIndividu;
    private int idEvenement;

    public M_Avoir_Evenement(Db_mariadb db, int idMembre, int idEvenement) {
        this.db = db;
        this.idIndividu = idMembre;
        this.idEvenement = idEvenement;
    }

    public M_Avoir_Evenement(Db_mariadb db, int idMembre, int idEvenement, boolean insert) throws SQLException {
        this(db, idMembre, idEvenement);
        if (insert) {
            String sql = "INSERT INTO AVOIR_EVENEMENT (id_individu, id_evenement) VALUES (" + idMembre + ", " + idEvenement + ")";
            db.sqlExec(sql);
        }
    }

    public int getIdMembre() {
        return idIndividu;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM AVOIR_EVENEMENT WHERE id_individu = " + idIndividu + " AND id_evenement = " + idEvenement;
        db.sqlExec(sql);
    }

    public static List<Integer> getEvenementsPourMembre(Db_mariadb db, int idMembre) throws SQLException {
        List<Integer> evenements = new LinkedList<>();
        String sql = "SELECT id_evenement FROM AVOIR_EVENEMENT WHERE id_individu = " + idMembre;
        ResultSet res = db.sqlSelect(sql);
        while (res.next()) {
            evenements.add(res.getInt("id_evenement"));
        }
        return evenements;
    }

    public static List<Integer> getMembresPourEvenement(Db_mariadb db, int idEvenement) throws SQLException {
        List<Integer> membres = new LinkedList<>();
        String sql = "SELECT id_individu FROM AVOIR_EVENEMENT WHERE id_evenement = " + idEvenement;
        ResultSet res = db.sqlSelect(sql);
        while (res.next()) {
            membres.add(res.getInt("id_individu"));
        }
        return membres;
    }

    @Override
    public String toString() {
        return "M_Avoir_Evenement{" +
                "idMembre=" + idIndividu +
                ", idEvenement=" + idEvenement +
                '}';
    }
}
