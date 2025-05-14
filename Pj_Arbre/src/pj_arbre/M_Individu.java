package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Individu {
    private Db_mariadb db;
    private int idIndividu;
    private String nom;
    private String prenom;
    private String derModif;
    private String codeGenre;
    private int idArbre;

    public M_Individu(Db_mariadb db, int idIndividu) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM INDIVIDUS WHERE id_individu = " + idIndividu;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.idIndividu = idIndividu;
            this.nom = res.getString("nom");
            this.prenom = res.getString("prenom");
            this.derModif = res.getString("der_modif");
            this.codeGenre = res.getString("code_genre");
            this.idArbre = res.getInt("id_arbre");
        }
    }

    public M_Individu(Db_mariadb db, String nom, String prenom, String derModif, String codeGenre, int idArbre) throws SQLException {
        this.db = db;
        this.nom = nom;
        this.prenom = prenom;
        this.derModif = derModif;
        this.codeGenre = codeGenre;
        this.idArbre = idArbre;

        String sql = "INSERT INTO INDIVIDUS (nom, prenom, der_modif, code_genre, id_arbre) VALUES ('" +
                nom + "', '" + prenom + "', '" + derModif + "', '" + codeGenre + "', " + idArbre + ")";
        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.idIndividu = res.getInt(1); // assuming first column is id_individu
        }
    }

    public int getIdMembre() {
        return idIndividu;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDerModif() {
        return derModif;
    }

    public String getCodeGenre() {
        return codeGenre;
    }

    public int getIdArbre() {
        return idArbre;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDerModif(String derModif) {
        this.derModif = derModif;
    }

    public void setCodeGenre(String codeGenre) {
        this.codeGenre = codeGenre;
    }

    public void setIdArbre(int idArbre) {
        this.idArbre = idArbre;
    }

    // Mise à jour
    public void update() throws SQLException {
        String sql = "UPDATE INDIVIDUS SET " +
                "nom = '" + nom + "', " +
                "prenom = '" + prenom + "', " +
                "der_modif = '" + derModif + "', " +
                "code_genre = '" + codeGenre + "', " +
                "id_arbre = " + idArbre + " " +
                "WHERE id_individu = " + idIndividu;
        db.sqlExec(sql);
    }

    // Suppression
    public void delete() throws SQLException {
        String sql = "DELETE FROM INDIVIDUS WHERE id_individu = " + idIndividu;
        db.sqlExec(sql);
    }

    public static LinkedHashMap<Integer, M_Individu> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Individu> individus = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT id_individu FROM INDIVIDUS ORDER BY nom");
        while (res.next()) {
            int id = res.getInt("id_individu");
            M_Individu individu = new M_Individu(db, id);
            individus.put(id, individu);
        }
        return individus;
    }
    
    public static LinkedHashMap<Integer, M_Individu> getIndividusPourArbre(Db_mariadb db, int idArbre) throws SQLException {
    LinkedHashMap<Integer, M_Individu> individus = new LinkedHashMap<>();
    String sql = "SELECT id_individu FROM INDIVIDUS WHERE id_arbre = " + idArbre + " ORDER BY nom";
    ResultSet res = db.sqlSelect(sql);
    while (res.next()) {
        int id = res.getInt("id_individu");
        M_Individu individu = new M_Individu(db, id);
        individus.put(id, individu);
    }
    return individus;
}


    @Override
    public String toString() {
        return "M_Individu{" +
                "idIndividu=" + idIndividu +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", derModif='" + derModif + '\'' +
                ", codeGenre='" + codeGenre + '\'' +
                ", idArbre=" + idArbre +
                '}';
    }
}
