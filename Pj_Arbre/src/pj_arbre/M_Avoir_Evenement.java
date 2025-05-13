package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class M_Avoir_Evenement {
    private Db_mariadb db;
    private int idMembre;
    private int idEvenement;

    // Constructeur depuis base
    public M_Avoir_Evenement(Db_mariadb db, int idMembre, int idEvenement) {
        this.db = db;
        this.idMembre = idMembre;
        this.idEvenement = idEvenement;
    }

    // Constructeur d’insertion
    public M_Avoir_Evenement(Db_mariadb db, int idMembre, int idEvenement, boolean insert) throws SQLException {
        this(db, idMembre, idEvenement);
        if (insert) {
            String sql = "INSERT INTO AVOIR_EVENEMENT (id_membre, id_evenement) VALUES (" + idMembre + ", " + idEvenement + ")";
            db.sqlExec(sql);
        }
    }

    // Getters
    public int getIdMembre() {
        return idMembre;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    // Suppression
    public void delete() throws SQLException {
        String sql = "DELETE FROM AVOIR_EVENEMENT WHERE id_membre = " + idMembre + " AND id_evenement = " + idEvenement;
        db.sqlExec(sql);
    }

    // Récupération des événements pour un membre
    public static List<Integer> getEvenementsPourMembre(Db_mariadb db, int idMembre) throws SQLException {
        List<Integer> evenements = new LinkedList<>();
        String sql = "SELECT id_evenement FROM AVOIR_EVENEMENT WHERE id_membre = " + idMembre;
        ResultSet res = db.sqlSelect(sql);
        while (res.next()) {
            evenements.add(res.getInt("id_evenement"));
        }
        return evenements;
    }

    // Récupération des membres pour un événement
    public static List<Integer> getMembresPourEvenement(Db_mariadb db, int idEvenement) throws SQLException {
        List<Integer> membres = new LinkedList<>();
        String sql = "SELECT id_membre FROM AVOIR_EVENEMENT WHERE id_evenement = " + idEvenement;
        ResultSet res = db.sqlSelect(sql);
        while (res.next()) {
            membres.add(res.getInt("id_membre"));
        }
        return membres;
    }

    @Override
    public String toString() {
        return "M_Avoir_Evenement{" +
                "idMembre=" + idMembre +
                ", idEvenement=" + idEvenement +
                '}';
    }
}
