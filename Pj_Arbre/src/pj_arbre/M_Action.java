package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Action {
    private Db_mariadb db;
    private String codeAction;
    private String libelle;

    public M_Action(Db_mariadb db, String codeAction) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM ACTIONS WHERE code_action = '" + codeAction + "'";
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.codeAction = res.getString("code_action");
            this.libelle = res.getString("libelle");
        } else {
            throw new SQLException("Aucune action trouvée avec le code : " + codeAction);
        }
    }

    public M_Action(Db_mariadb db, String codeAction, String libelle) throws SQLException {
        this.db = db;
        this.codeAction = codeAction;
        this.libelle = libelle;

        String sql = "INSERT INTO ACTIONS (code_action, libelle) VALUES ('" + codeAction + "', '" + libelle + "')";
        db.sqlExec(sql);
    }

    public String getCodeAction() {
        return codeAction;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void update() throws SQLException {
        String sql = "UPDATE ACTIONS SET libelle = '" + libelle + "' WHERE code_action = '" + codeAction + "'";
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM ACTIONS WHERE code_action = '" + codeAction + "'");
    }

    public static LinkedHashMap<String, M_Action> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<String, M_Action> actions = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM ACTIONS ORDER BY libelle");
        while (res.next()) {
            String codeAction = res.getString("code_action");
            M_Action action = new M_Action(db, codeAction);
            actions.put(codeAction, action);
        }
        return actions;
    }

    public static LinkedHashMap<String, M_Action> getActionsPourRole(Db_mariadb db, String codeRole) throws SQLException {
        LinkedHashMap<String, M_Action> actions = new LinkedHashMap<>();
        String sql = "SELECT a.code_action FROM ACTIONS a " +
                     "JOIN AUTORISER au ON a.code_action = au.code_action " +
                     "WHERE au.code_role = '" + codeRole + "' " +
                     "ORDER BY a.libelle";
        ResultSet res = db.sqlSelect(sql);
        while (res.next()) {
            String codeAction = res.getString("code_action");
            M_Action action = new M_Action(db, codeAction);
            actions.put(codeAction, action);
        }
        return actions;
    }

    @Override
    public String toString() {
        return "M_Action{" +
                "codeAction='" + codeAction + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
