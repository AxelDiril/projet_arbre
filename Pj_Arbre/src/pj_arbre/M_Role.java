package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Role {
    private Db_mariadb db;
    private String codeRole;
    private String libelle;

    // Constructeur de récupération
    public M_Role(Db_mariadb db, String codeRole) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM ROLES WHERE code_role = '" + codeRole + "'";
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.codeRole = res.getString("code_role");
            this.libelle = res.getString("libelle");
        }
    }

    // Constructeur d’insertion
    public M_Role(Db_mariadb db, String codeRole, String libelle) throws SQLException {
        this.db = db;
        this.codeRole = codeRole;
        this.libelle = libelle;

        String sql = "INSERT INTO ROLES (code_role, libelle) VALUES ('" + codeRole + "', '" + libelle + "')";
        db.sqlExec(sql);
    }

    public String getCodeRole() {
        return codeRole;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void update() throws SQLException {
        String sql = "UPDATE ROLES SET libelle = '" + libelle + "' WHERE code_role = '" + codeRole + "'";
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM ROLES WHERE code_role = '" + codeRole + "'");
    }

    public static LinkedHashMap<String, M_Role> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<String, M_Role> roles = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM ROLES ORDER BY code_role");
        while (res.next()) {
            M_Role role = new M_Role(db, res.getString("code_role"));
            roles.put(role.getCodeRole(), role);
        }
        return roles;
    }

    @Override
    public String toString() {
        return "M_Role{" +
                "codeRole='" + codeRole + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
