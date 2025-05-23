package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Acceder {
    private Db_mariadb db;
    private int iIdUtilisateur;
    private int iIdArbre;
    private String strCodeAcces;

    public M_Acceder(Db_mariadb db, int iIdUtilisateur, int iIdArbre) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM ACCEDER WHERE id_utilisateur = " + 
                iIdUtilisateur + " AND id_arbre = " + iIdArbre;
        
        ResultSet res = db.sqlSelect(strSql);
        if (res.first()) {
            this.iIdUtilisateur = iIdUtilisateur;
            this.iIdArbre = iIdArbre;
            this.strCodeAcces = res.getString("code_acces");
        }
    }

    public M_Acceder(Db_mariadb db, int iIdUtilisateur, int iIdArbre,
            String strCodeAcces) throws SQLException {
        this.db = db;
        this.iIdUtilisateur = iIdUtilisateur;
        this.iIdArbre = iIdArbre;
        this.strCodeAcces = strCodeAcces;
        
        String strSql = "INSERT INTO ACCEDER (id_utilisateur, id_arbre, code_acces) VALUES (" 
           + iIdUtilisateur + ", " + iIdArbre + ", '" + strCodeAcces + "') "
           + "ON DUPLICATE KEY UPDATE code_acces = VALUES(code_acces)";
        
        db.sqlExec(strSql);
    }

    public int getIdUtilisateur() {
        return iIdUtilisateur;
    }

    public int getIdArbre() {
        return iIdArbre;
    }

    public String getCodeAcces() {
        return strCodeAcces;
    }
    
    public void setIdUtilisateur(int iIdArbre) {
        this.iIdArbre = iIdArbre;
    }

    public void setIdArbre(int iIdArbre) {
        this.iIdArbre = iIdArbre;
    }

    public void setCodeAcces(String strCodeAcces) {
        this.strCodeAcces = strCodeAcces;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE ACCEDER SET id_arbre = " + iIdArbre + 
                ", code_acces = '" + strCodeAcces + 
                "' WHERE id_utilisateur = " + iIdUtilisateur;
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM ACCEDER WHERE id_utilisateur = " + iIdUtilisateur;
        
        db.sqlExec(strSql);
    }
    
    public static LinkedHashMap<Integer, M_Acceder> getAccesUtilisateur(Db_mariadb db, 
            int iIdUtilisateur) throws SQLException {
        LinkedHashMap<Integer, M_Acceder> lhmLesAcces = new LinkedHashMap<>();

        String strSql = "SELECT * FROM ACCEDER WHERE id_utilisateur = " + 
                iIdUtilisateur + " ORDER BY id_arbre";
        
        ResultSet res = db.sqlSelect(strSql);

        while (res.next()) {
            int iIdUtilisateurAcces = res.getInt("id_utilisateur");
            int iIdArbreAcces = res.getInt("id_arbre");
            M_Acceder unAcces = new M_Acceder(db, iIdUtilisateurAcces, iIdArbreAcces);
            lhmLesAcces.put(iIdArbreAcces, unAcces);
        }

        return lhmLesAcces;
    }
    
    public static LinkedHashMap<Integer, M_Acceder> getAccesPourArbre(Db_mariadb db, 
            int iIdArbre) throws SQLException {
        LinkedHashMap<Integer, M_Acceder> lhmLesAcces = new LinkedHashMap<>();

        String strSql = "SELECT * FROM ACCEDER WHERE id_arbre = " + iIdArbre + 
                " ORDER BY id_utilisateur";
        
        ResultSet res = db.sqlSelect(strSql);

        while (res.next()) {
            int iIdUtilisateur = res.getInt("id_utilisateur");
            M_Acceder unAcces = new M_Acceder(db, iIdUtilisateur, iIdArbre);
            lhmLesAcces.put(iIdUtilisateur, unAcces);
        }

        return lhmLesAcces;
    }


    @Override
    public String toString() {
        return "M_Acceder{" +
                "idUtilisateur=" + iIdUtilisateur +
                ", idArbre=" + iIdArbre +
                ", codeAcces='" + strCodeAcces + '\'' +
                '}';
    }
}
