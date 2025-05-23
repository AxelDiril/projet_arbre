package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Individu {
    private Db_mariadb db;
    private int iIdIndividu;
    private String strNom;
    private String strPrenom;
    private String strCodeGenre;
    private int iIdArbre;

    public M_Individu(Db_mariadb db, int iIdIndividu) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM INDIVIDUS WHERE id_individu = " + iIdIndividu;
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.iIdIndividu = iIdIndividu;
            this.strNom = res.getString("nom");
            this.strPrenom = res.getString("prenom");
            this.strCodeGenre = res.getString("code_genre");
            this.iIdArbre = res.getInt("id_arbre");
        }
    }

    public M_Individu(Db_mariadb db, String strNom, String strPrenom,
            String strCodeGenre, int iIdArbre) throws SQLException {
        this.db = db;
        this.strNom = strNom;
        this.strPrenom = strPrenom;
        this.strCodeGenre = strCodeGenre;
        this.iIdArbre = iIdArbre;

        String strSql = "INSERT INTO INDIVIDUS (nom, prenom, code_genre, id_arbre) VALUES ('" +
                strNom + "', '" + strPrenom + "', '" + strCodeGenre + "', " + iIdArbre + ")";
        
        db.sqlExec(strSql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.iIdIndividu = res.getInt(1);
        }
    }

    public int getIdIndividu() {
        return iIdIndividu;
    }

    public String getNom() {
        return strNom;
    }

    public String getPrenom() {
        return strPrenom;
    }

    public String getCodeGenre() {
        return strCodeGenre;
    }

    public int getIdArbre() {
        return iIdArbre;
    }

    public void setNom(String strNom) {
        this.strNom = strNom;
    }

    public void setPrenom(String strPrenom) {
        this.strPrenom = strPrenom;
    }

    public void setCodeGenre(String strCodeGenre) {
        this.strCodeGenre = strCodeGenre;
    }

    public void setIdArbre(int iIdArbre) {
        this.iIdArbre = iIdArbre;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE INDIVIDUS SET " +
                "nom = '" + strNom + "', " +
                "prenom = '" + strPrenom + "', " +
                "code_genre = '" + strCodeGenre + "', " +
                "id_arbre = " + iIdArbre + " " +
                "WHERE id_individu = " + iIdIndividu;
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM INDIVIDUS WHERE id_individu = " + iIdIndividu;
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<Integer, M_Individu> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Individu> lhmLesIndividus = new LinkedHashMap<>();
        
        String strSql = "SELECT id_individu FROM INDIVIDUS ORDER BY nom";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            int iIdIndividu = res.getInt("id_individu");
            M_Individu unIndividu = new M_Individu(db, iIdIndividu);
            lhmLesIndividus.put(iIdIndividu, unIndividu);
        }
        
        return lhmLesIndividus;
    }
    
    public static LinkedHashMap<Integer, M_Individu> getIndividusPourArbre
        (Db_mariadb db, int iIdArbre) throws SQLException {
        LinkedHashMap<Integer, M_Individu> lhmLesIndividus = new LinkedHashMap<>();

        String strSql = "SELECT id_individu FROM INDIVIDUS WHERE id_arbre = " +
                iIdArbre + " ORDER BY nom";

        ResultSet res = db.sqlSelect(strSql);

        while (res.next()) {
            int iIdIndividu = res.getInt("id_individu");
            M_Individu unIndividu = new M_Individu(db, iIdIndividu);
            lhmLesIndividus.put(iIdIndividu, unIndividu);
        }
    
        return lhmLesIndividus;
    }


    @Override
    public String toString() {
        return "M_Individu{" +
                "idIndividu=" + iIdIndividu +
                ", nom='" + strNom + '\'' +
                ", prenom='" + strPrenom + '\'' +
                ", codeGenre='" + strCodeGenre + '\'' +
                ", idArbre=" + iIdArbre +
                '}';
    }
}
