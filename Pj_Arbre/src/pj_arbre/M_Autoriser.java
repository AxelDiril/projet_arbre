package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Autoriser {
    private Db_mariadb db;
    private String strCodeRole;
    private String strCodeAction;

    public M_Autoriser(Db_mariadb db, String strCodeRole, String strCodeAction)
            throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM AUTORISER WHERE code_role = '" 
                + strCodeRole  + "' AND code_action = '" + strCodeAction + "'";
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.strCodeRole = strCodeRole;
            this.strCodeAction = strCodeAction;
        }
    }

    public String getCodeRole() {
        return strCodeRole;
    }

    public String getCodeAction() {
        return strCodeAction;
    }

    public void setCodeRole(String strCodeRole) {
        this.strCodeRole = strCodeRole;
    }

    public void setCodeAction(String strCodeAction) {
        this.strCodeAction = strCodeAction;
    }

    public void update(String strCodeRole, String strCodeAction) 
            throws SQLException {
        String strSql = "UPDATE AUTORISER SET code_role = '" + strCodeRole + 
                "', code_action = '" + strCodeAction + "'" +
                     " WHERE code_role = '" + strCodeRole +
                "' AND code_action = '" + strCodeAction + "'";
        
        db.sqlExec(strSql);
        
        this.strCodeRole = strCodeRole;
        this.strCodeAction = strCodeAction;
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM AUTORISER WHERE code_role = '" + strCodeRole +
                "' AND code_action = '" + strCodeAction + "'";
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<String, M_Autoriser> getRecords(Db_mariadb db) 
            throws SQLException {
        LinkedHashMap<String, M_Autoriser> lhmLesAutorisations = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM AUTORISER";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            String strCodeRole = res.getString("code_role");
            String strCodeAction = res.getString("code_action");
            M_Autoriser uneAutorisation = new M_Autoriser(db, strCodeRole, strCodeAction);
            lhmLesAutorisations.put(strCodeRole + "_" + strCodeAction, uneAutorisation);
        }
        
        return lhmLesAutorisations;
    }

    public static void M_Autorisation(Db_mariadb db, String strCodeRole, 
            String strCodeAction) throws SQLException {
        String strSql = "INSERT INTO AUTORISER (code_role, code_action) VALUES ('" 
                + strCodeRole + "', '" + strCodeAction + "')";
        
        db.sqlExec(strSql);
    }

    @Override
    public String toString() {
        return "M_Autoriser{" +
                "codeRole='" + strCodeRole + '\'' +
                ", codeAction='" + strCodeAction + '\'' +
                '}';
    }
}
