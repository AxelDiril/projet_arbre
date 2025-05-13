package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Type_Evenement {
    private Db_mariadb db;
    private int idTypeEvenement;
    private String label;

    // Constructeur de récupération depuis la BDD
    public M_Type_Evenement(Db_mariadb db, int idTypeEvenement) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM TYPE_EVENEMENT WHERE id_type_evenement = " + idTypeEvenement;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.idTypeEvenement = idTypeEvenement;
            this.label = res.getString("label");
        }
    }

    // Constructeur d’insertion
    public M_Type_Evenement(Db_mariadb db, String label, int nbPersonnesMaxi) throws SQLException {
        this.db = db;
        this.label = label;

        String sql = "INSERT INTO TYPE_EVENEMENT (label) VALUES ('" + label + ")";
        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.idTypeEvenement = res.getInt("id_type_evenement");
        }
    }

    // Getters
    public int getIdTypeEvenement() {
        return idTypeEvenement;
    }

    public String getLabel() {
        return label;
    }

    // Setter pour label et nbPersonnesMaxi
    public void setLabel(String label) {
        this.label = label;
    }

    // Mise à jour
    public void update() throws SQLException {
        String sql = "UPDATE TYPE_EVENEMENT SET label = '" + label + " WHERE id_type_evenement = " + idTypeEvenement;
        db.sqlExec(sql);
    }

    // Suppression
    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM TYPE_EVENEMENT WHERE id_type_evenement = " + idTypeEvenement);
    }

    // Récupération de tous les types d'événements
    public static LinkedHashMap<Integer, M_Type_Evenement> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Type_Evenement> typesEvenement = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM TYPE_EVENEMENT ORDER BY label");
        while (res.next()) {
            M_Type_Evenement t = new M_Type_Evenement(db, res.getInt("id_type_evenement"));
            typesEvenement.put(t.getIdTypeEvenement(), t);
        }
        return typesEvenement;
    }

    @Override
    public String toString() {
        return "M_TypeEvenement{" +
                "idTypeEvenement=" + idTypeEvenement +
                ", label='" + label +
                '}';
    }
}
