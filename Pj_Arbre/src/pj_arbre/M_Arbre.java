package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Arbre {
    private Db_mariadb db;
    private int idArbre;
    private String nom;
    private int idCreateur;

    // Constructeur de récupération depuis la BDD
    public M_Arbre(Db_mariadb db, int idArbre) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM ARBRES WHERE id_arbre = " + idArbre;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.idArbre = idArbre;
            this.nom = res.getString("nom");
            this.idCreateur = res.getInt("id_createur");
        }
    }

    // Constructeur d’insertion
    public M_Arbre(Db_mariadb db, String nom, int idCreateur) throws SQLException {
        this.db = db;
        this.nom = nom;
        this.idCreateur = idCreateur;

        String sql = "INSERT INTO ARBRES (nom, id_createur) VALUES ('" + nom + "', " + idCreateur + ")";
        db.sqlExec(sql);
        
        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.idArbre = res.getInt("id");
        }
    }

    // Getters
    public int getIdArbre() {
        return idArbre;
    }

    public String getNom() {
        return nom;
    }

    public int getIdCreateur() {
        return idCreateur;
    }

    // Setters (sauf pour la clé primaire)
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    // Mise à jour
    public void update() throws SQLException {
        String sql = "UPDATE ARBRES SET nom = '" + nom + "', id_createur = " + idCreateur +
                     " WHERE id_arbre = " + idArbre;
        db.sqlExec(sql);
    }

    // Suppression
    public void delete() throws SQLException {
        String sql = "DELETE FROM ARBRES WHERE id_arbre = " + idArbre;
        db.sqlExec(sql);
    }
    
    public static LinkedHashMap<Integer, M_Arbre> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Arbre> arbres = new LinkedHashMap<>();
        String sql = "SELECT * FROM ARBRES ORDER BY nom";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            int idArbre = res.getInt("id_arbre");
            M_Arbre arbre = new M_Arbre(db, idArbre);  // utilise ton constructeur de récupération
            arbres.put(idArbre, arbre);
        }

        return arbres;
    }

    @Override
    public String toString() {
        return "M_Arbre{" +
                "idArbre=" + idArbre +
                ", nom='" + nom + '\'' +
                ", idCreateur=" + idCreateur +
                '}';
    }
}
