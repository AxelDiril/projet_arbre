package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Acces_Arbre {
    private Db_mariadb db;
    private String codeAcces;
    private String libelle;

    public M_Acces_Arbre(Db_mariadb db, String codeAcces) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM ACCES_ARBRE WHERE code_acces = '" + codeAcces + "'";
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.codeAcces = res.getString("code_acces");
            this.libelle = res.getString("libelle");
        }
    }

    public M_Acces_Arbre(Db_mariadb db, String codeAcces, String libelle) throws SQLException {
        this.db = db;
        this.codeAcces = codeAcces;
        this.libelle = libelle;

        String sql = "INSERT INTO ACCES_ARBRE (code_acces, libelle) VALUES ('" + codeAcces + "', '" + libelle + "')";
        db.sqlExec(sql);
    }

    public String getCodeAcces() {
        return codeAcces;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void update() throws SQLException {
        String sql = "UPDATE ACCES_ARBRE SET libelle = '" + libelle + "' WHERE code_acces = '" + codeAcces + "'";
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM ACCES_ARBRE WHERE code_acces = '" + codeAcces + "'");
    }

    public static LinkedHashMap<String, M_Acces_Arbre> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<String, M_Acces_Arbre> acces = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM ACCES_ARBRE ORDER BY libelle");
        while (res.next()) {
            M_Acces_Arbre a = new M_Acces_Arbre(db, res.getString("code_acces"));
            acces.put(a.getCodeAcces(), a);
        }
        return acces;
    }

    @Override
    public String toString() {
        return "M_AccesArbre{" +
                "codeAcces='" + codeAcces + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
