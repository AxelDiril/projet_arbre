package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Arbre {
    private Db_mariadb db;
    private int iIdArbre;
    private String strNom;
    private int iIdUtilisateur;

    public M_Arbre(Db_mariadb db, int iIdArbre) throws SQLException {
        this.db = db;
        
        String sql = "SELECT * FROM ARBRES WHERE id_arbre = " + iIdArbre;
        
        ResultSet res = db.sqlSelect(sql);
        
        if (res.first()) {
            this.iIdArbre = iIdArbre;
            this.strNom = res.getString("nom");
            this.iIdUtilisateur = res.getInt("id_createur");
        }
    }

    public M_Arbre(Db_mariadb db, String strNom, int iIdUtilisateur) throws SQLException {
        this.db = db;
        this.strNom = strNom;
        this.iIdUtilisateur = iIdUtilisateur;

        String strSql = "INSERT INTO ARBRES (nom, id_createur) VALUES ('" 
                + strNom + "', " + iIdUtilisateur + ")";
        db.sqlExec(strSql);
        
        ResultSet res = db.sqlLastId();
        
        if (res.first()) {
            this.iIdArbre = res.getInt("id");
        }
    }

    public int getIdArbre() {
        return iIdArbre;
    }

    public String getNom() {
        return strNom;
    }

    public int getIdCreateur() {
        return iIdUtilisateur;
    }

    public void setNom(String strNom) {
        this.strNom = strNom;
    }

    public void setIdCreateur(int iIdUtilisateur) {
        this.iIdUtilisateur = iIdUtilisateur;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE ARBRES SET nom = '" + strNom 
                + "', id_createur = "  + iIdUtilisateur +
                     " WHERE id_arbre = " + iIdArbre;
        db.sqlExec(strSql);
    }

    // Suppression
    public void delete() throws SQLException {
        String strSql = "DELETE FROM ARBRES WHERE id_arbre = " + iIdArbre;
        
        db.sqlExec(strSql);
    }
    
    public static LinkedHashMap<Integer, M_Arbre> getRecords(Db_mariadb db) 
            throws SQLException {
        LinkedHashMap<Integer, M_Arbre> lhmLesArbres = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM ARBRES ORDER BY nom";
        
        ResultSet res = db.sqlSelect(strSql);

        while (res.next()) {
            int iIdArbre = res.getInt("id_arbre");
            M_Arbre unArbre = new M_Arbre(db, iIdArbre);
            lhmLesArbres.put(iIdArbre, unArbre);
        }

        return lhmLesArbres;
    }

    @Override
    public String toString() {
        return "M_Arbre{" +
                "idArbre=" + iIdArbre +
                ", nom='" + strNom + '\'' +
                ", idCreateur=" + iIdUtilisateur +
                '}';
    }
}
