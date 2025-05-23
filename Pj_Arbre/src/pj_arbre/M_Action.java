package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Action {
    private Db_mariadb db;
    private String strCodeAction;
    private String strLibelle;

    public M_Action(Db_mariadb db, String strCodeAction) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM ACTIONS WHERE code_action = '" + strCodeAction + "'";
        
        ResultSet res = db.sqlSelect(strSql);
        if (res.first()) {
            this.strCodeAction = res.getString("code_action");
            this.strLibelle = res.getString("libelle");
        }
    }

    public M_Action(Db_mariadb db, String strCodeAction, String strLibelle) 
            throws SQLException {
        this.db = db;
        this.strCodeAction = strCodeAction;
        this.strLibelle = strLibelle;

        String sql = "INSERT INTO ACTIONS (code_action, libelle) VALUES ('" + 
                strCodeAction + "', '" + strLibelle + "')";
        
        db.sqlExec(sql);
    }

    public String getCodeAction() {
        return strCodeAction;
    }

    public String getLibelle() {
        return strLibelle;
    }

    public void setLibelle(String strLibelle) {
        this.strLibelle = strLibelle;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE ACTIONS SET libelle = '" + strLibelle +
                "' WHERE code_action = '" + strCodeAction + "'";
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM ACTIONS WHERE code_action = '" +
                strCodeAction + "'";
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<String, M_Action> getRecords(Db_mariadb db) 
            throws SQLException {
        LinkedHashMap<String, M_Action> lhmLesActions = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM ACTIONS ORDER BY libelle";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            String strCodeAction = res.getString("code_action");
            M_Action uneAction = new M_Action(db, strCodeAction);
            lhmLesActions.put(strCodeAction, uneAction);
        }
        
        return lhmLesActions;
    }

    public static LinkedHashMap<String, M_Action> getActionsPourRole(Db_mariadb db, String strCodeRole) throws SQLException {
        LinkedHashMap<String, M_Action> lhmLesActions = new LinkedHashMap<>();
        
        String sql = "SELECT a.code_action FROM ACTIONS a " +
                     "JOIN AUTORISER au ON a.code_action = au.code_action " +
                     "WHERE au.code_role = '" + strCodeRole + "' " +
                     "ORDER BY a.libelle";
        
        ResultSet res = db.sqlSelect(sql);
        
        while (res.next()) {
            String strCodeRoleAction = res.getString("code_action");
            M_Action uneAction = new M_Action(db, strCodeRoleAction);
            lhmLesActions.put(strCodeRoleAction, uneAction);
        }
        
        return lhmLesActions;
    }

    @Override
    public String toString() {
        return "M_Action{" +
                "codeAction='" + strCodeAction + '\'' +
                ", libelle='" + strLibelle + '\'' +
                '}';
    }
}
