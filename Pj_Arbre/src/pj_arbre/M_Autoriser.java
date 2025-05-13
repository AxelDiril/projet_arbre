package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Autoriser {
    private Db_mariadb db;
    private String codeRole;
    private String codeAction;

    // Constructeur de récupération depuis la BDD
    public M_Autoriser(Db_mariadb db, String codeRole, String codeAction) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM AUTORISER WHERE code_role = '" + codeRole + "' AND code_action = '" + codeAction + "'";
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.codeRole = codeRole;
            this.codeAction = codeAction;
        } else {
            throw new SQLException("Aucune autorisation trouvée pour le couple (" + codeRole + ", " + codeAction + ")");
        }
    }

    // Getters
    public String getCodeRole() {
        return codeRole;
    }

    public String getCodeAction() {
        return codeAction;
    }

    // Setters
    public void setCodeRole(String codeRole) {
        this.codeRole = codeRole;
    }

    public void setCodeAction(String codeAction) {
        this.codeAction = codeAction;
    }

    // Mise à jour (inutile ici car PK ne change pas — à personnaliser si besoin)
    public void update(String newCodeRole, String newCodeAction) throws SQLException {
        String sql = "UPDATE AUTORISER SET code_role = '" + newCodeRole + "', code_action = '" + newCodeAction + "'" +
                     " WHERE code_role = '" + codeRole + "' AND code_action = '" + codeAction + "'";
        db.sqlExec(sql);
        this.codeRole = newCodeRole;
        this.codeAction = newCodeAction;
    }

    // Suppression
    public void delete() throws SQLException {
        String sql = "DELETE FROM AUTORISER WHERE code_role = '" + codeRole + "' AND code_action = '" + codeAction + "'";
        db.sqlExec(sql);
    }

    // Récupération de tous les enregistrements
    public static LinkedHashMap<String, M_Autoriser> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<String, M_Autoriser> autorisations = new LinkedHashMap<>();
        String sql = "SELECT * FROM AUTORISER";
        ResultSet res = db.sqlSelect(sql);
        while (res.next()) {
            String codeRole = res.getString("code_role");
            String codeAction = res.getString("code_action");
            M_Autoriser autoriser = new M_Autoriser(db, codeRole, codeAction);
            autorisations.put(codeRole + "_" + codeAction, autoriser);
        }
        return autorisations;
    }

    // Ajout d'une autorisation
    public static void addAutorisation(Db_mariadb db, String codeRole, String codeAction) throws SQLException {
        String sql = "INSERT INTO AUTORISER (code_role, code_action) VALUES ('" + codeRole + "', '" + codeAction + "')";
        db.sqlExec(sql);
    }

    @Override
    public String toString() {
        return "M_Autoriser{" +
                "codeRole='" + codeRole + '\'' +
                ", codeAction='" + codeAction + '\'' +
                '}';
    }

    // Test main
    public static void main(String[] args) throws Exception {
        Db_mariadb db = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
        
        // Exemple d'affichage
        LinkedHashMap<String, M_Autoriser> autorisations = M_Autoriser.getRecords(db);
        for (M_Autoriser a : autorisations.values()) {
            System.out.println(a);
        }
    }
}
