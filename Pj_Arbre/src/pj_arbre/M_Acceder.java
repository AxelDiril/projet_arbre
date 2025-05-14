package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Acceder {
    private Db_mariadb db;
    private int idUtilisateur;
    private int idArbre;
    private String codeAcces;

    public M_Acceder(Db_mariadb db, int idUtilisateur, int idArbre) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM ACCEDER WHERE id_utilisateur = " + idUtilisateur + " AND id_arbre = " + idArbre;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.idUtilisateur = idUtilisateur;
            this.idArbre = idArbre;
            this.codeAcces = res.getString("code_acces");
        }
    }

    public M_Acceder(Db_mariadb db, int idUtilisateur, int idArbre, String codeAcces) throws SQLException {
        this.db = db;
        this.idUtilisateur = idUtilisateur;
        this.idArbre = idArbre;
        this.codeAcces = codeAcces;

        String sql = "INSERT INTO ACCEDER (id_utilisateur, id_arbre, code_acces) VALUES (" 
                        + idUtilisateur + ", " + idArbre + ", '" + codeAcces + "')";
        
        db.sqlExec(sql);
    }

    public int getId() {
        return idUtilisateur;
    }

    public int getIdArbre() {
        return idArbre;
    }

    public String getCodeAcces() {
        return codeAcces;
    }

    public void setIdArbre(int idArbre) {
        this.idArbre = idArbre;
    }

    public void setCodeAcces(String codeAcces) {
        this.codeAcces = codeAcces;
    }

    public void update() throws SQLException {
        String sql = "UPDATE ACCEDER SET id_arbre = " + idArbre + ", code_acces = '" + codeAcces + "' WHERE id_utilisateur = " + idUtilisateur;
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM ACCEDER WHERE id_utilisateur = " + idUtilisateur;
        db.sqlExec(sql);
    }
    
    public static LinkedHashMap<Integer, M_Acceder> getAccesUtilisateur(Db_mariadb db, int idUtilisateur) throws SQLException {
        LinkedHashMap<Integer, M_Acceder> lesAcces = new LinkedHashMap<>();

        String sql = "SELECT * FROM ACCEDER WHERE id_utilisateur = " + idUtilisateur + " ORDER BY id_arbre";
        ResultSet res = db.sqlSelect(sql);

        while (res.next()) {
            int id = res.getInt("id_utilisateur");
            int idArbre = res.getInt("id_arbre");
            M_Acceder unAcces = new M_Acceder(db, id, idArbre);
            lesAcces.put(id, unAcces);
        }

        return lesAcces;
    }

    @Override
    public String toString() {
        return "M_Acceder{" +
                "idUtilisateur=" + idUtilisateur +
                ", idArbre=" + idArbre +
                ", codeAcces='" + codeAcces + '\'' +
                '}';
    }
}
