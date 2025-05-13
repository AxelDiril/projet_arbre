package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Acceder {
    private Db_mariadb db;
    private int id;
    private int idArbre;
    private String codeAcces;

    // Constructeur de récupération depuis la BDD
    public M_Acceder(Db_mariadb db, int id, int idArbre) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM ACCEDER WHERE id = " + id + " AND id_arbre = " + idArbre;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.id = id;
            this.idArbre = idArbre;
            this.codeAcces = res.getString("code_acces");
        }
    }

    // Constructeur d’insertion
    public M_Acceder(Db_mariadb db, int id, int idArbre, String codeAcces) throws SQLException {
        this.db = db;
        this.id = id;
        this.idArbre = idArbre;
        this.codeAcces = codeAcces;

        // Préparer la requête d'insertion avec l'id_utilisateur, id_arbre et code_acces
        String sql = "INSERT INTO ACCEDER (id, id_arbre, code_acces) VALUES (" 
                        + id + ", " + idArbre + ", '" + codeAcces + "')";
        
        // Exécuter la requête d'insertion
        db.sqlExec(sql);
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getIdArbre() {
        return idArbre;
    }

    public String getCodeAcces() {
        return codeAcces;
    }

    // Setters
    public void setIdArbre(int idArbre) {
        this.idArbre = idArbre;
    }

    public void setCodeAcces(String codeAcces) {
        this.codeAcces = codeAcces;
    }

    // Mise à jour
    public void update() throws SQLException {
        String sql = "UPDATE ACCEDER SET id_arbre = " + idArbre + ", code_acces = '" + codeAcces + "' WHERE id = " + id;
        db.sqlExec(sql);
    }

    // Suppression
    public void delete() throws SQLException {
        String sql = "DELETE FROM ACCEDER WHERE id = " + id;
        db.sqlExec(sql);
    }
    
    public static LinkedHashMap<Integer, M_Acceder> getAccesUtilisateur(Db_mariadb db, int idUtilisateur) throws SQLException {
        LinkedHashMap<Integer, M_Acceder> lesAcces = new LinkedHashMap<>();

        String sql = "SELECT * FROM ACCEDER WHERE id = " + idUtilisateur + " ORDER BY id_arbre";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            int id = res.getInt("id");
            int idArbre = res.getInt("id_arbre");
            M_Acceder unAcces = new M_Acceder(db, id, idArbre);
            lesAcces.put(id, unAcces);
        }

        return lesAcces;
    }

    @Override
    public String toString() {
        return "M_Acceder{" +
                "id=" + id +
                ", idArbre=" + idArbre +
                ", codeAcces='" + codeAcces + '\'' +
                '}';
    }
}
