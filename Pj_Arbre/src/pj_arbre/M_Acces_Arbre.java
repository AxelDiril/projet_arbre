package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Acces_Arbre {
    private Db_mariadb db;
    private String strCodeAcces;
    private String strLibelle;

    public M_Acces_Arbre(Db_mariadb db, String strCodeAcces) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM ACCES_ARBRE WHERE code_acces = '" 
                + strCodeAcces + "'";
        
        ResultSet res = db.sqlSelect(strSql);
        if (res.first()) {
            this.strCodeAcces = res.getString("code_acces");
            this.strLibelle = res.getString("libelle");
        }
    }

    public M_Acces_Arbre(Db_mariadb db, String strCodeAcces, String strLibelle) 
            throws SQLException {
        this.db = db;
        this.strCodeAcces = strCodeAcces;
        this.strLibelle = strLibelle;

        String strSql = "INSERT INTO ACCES_ARBRE (code_acces, libelle) VALUES ('" +
                strCodeAcces + "', '" + strLibelle + "')";
        
        db.sqlExec(strSql);
    }

    public String getCodeAcces() {
        return strCodeAcces;
    }

    public String getLibelle() {
        return strLibelle;
    }

    public void setLibelle(String strLibelle) {
        this.strLibelle = strLibelle;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE ACCES_ARBRE SET libelle = '" + strLibelle +
                "' WHERE code_acces = '" + strCodeAcces + "'";
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM ACCES_ARBRE WHERE code_acces = '"
                + strCodeAcces + "'";
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<String, M_Acces_Arbre> getRecords(Db_mariadb db) 
            throws SQLException {
        LinkedHashMap<String, M_Acces_Arbre> lhmLesAcces = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM ACCES_ARBRE ORDER BY libelle"; 
        
        ResultSet res = db.sqlSelect(strSql);
        while (res.next()) {
            M_Acces_Arbre unAcces = new M_Acces_Arbre(db, res.getString("code_acces"));
            lhmLesAcces.put(unAcces.getCodeAcces(), unAcces);
        }
        
        return lhmLesAcces;
    }

    @Override
    public String toString() {
        return "M_AccesArbre{" +
                "codeAcces='" + strCodeAcces + '\'' +
                ", libelle='" + strLibelle + '\'' +
                '}';
    }
}
