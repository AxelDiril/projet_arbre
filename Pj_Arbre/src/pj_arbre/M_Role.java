package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Role {
    private Db_mariadb db;
    private String strCodeRole;
    private String strLibelle;

    public M_Role(Db_mariadb db, String strCodeRole) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM ROLES WHERE code_role = '" + strCodeRole + "'";
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.strCodeRole = res.getString("code_role");
            this.strLibelle = res.getString("libelle");
        }
    }

    public M_Role(Db_mariadb db, String strCodeRole, String strLibelle) throws SQLException {
        this.db = db;
        this.strCodeRole = strCodeRole;
        this.strLibelle = strLibelle;

        String strSql = "INSERT INTO ROLES (code_role, libelle) VALUES ('" + 
                strCodeRole + "', '" + strLibelle + "')";
        db.sqlExec(strSql);
    }

    public String getCodeRole() {
        return strCodeRole;
    }

    public String getLibelle() {
        return strLibelle;
    }

    public void setLibelle(String libelle) {
        this.strLibelle = libelle;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE ROLES SET libelle = '" + strLibelle +
                "' WHERE code_role = '" + strCodeRole + "'";
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM ROLES WHERE code_role = '" + strCodeRole + "'";
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<String, M_Role> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<String, M_Role> lhmLesRoles = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM ROLES ORDER BY code_role";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            M_Role unRole = new M_Role(db, res.getString("code_role"));
            lhmLesRoles.put(unRole.getCodeRole(), unRole);
        }
        
        return lhmLesRoles;
    }

    @Override
    public String toString() {
        return "M_Role{" +
                "codeRole='" + strCodeRole + '\'' +
                ", libelle='" + strLibelle + '\'' +
                '}';
    }
}
