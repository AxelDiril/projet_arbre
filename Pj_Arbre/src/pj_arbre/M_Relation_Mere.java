package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Relation_Mere {
    private Db_mariadb db;
    private int iIdIndividu;
    private int iIdMere;

    public M_Relation_Mere(Db_mariadb db, int iIdIndividu) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM RELATIONS_MERE WHERE id_individu = " + iIdIndividu;
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.iIdIndividu = res.getInt("id_individu");
            this.iIdMere = res.getInt("id_mere");
        }
    }

    public M_Relation_Mere(Db_mariadb db, int iIdIndividu, int iIdMere) throws SQLException {
        this.db = db;
        this.iIdIndividu = iIdIndividu;
        this.iIdMere = iIdMere;

        String strSql = "INSERT INTO RELATIONS_MERE (id_individu, id_mere)" +
                "VALUES (" + iIdIndividu + "," + iIdMere + ")" +
                "ON DUPLICATE KEY UPDATE id_mere = VALUES(id_mere)";
        
        db.sqlExec(strSql);
    }

    public int getIdIndividu() {
        return iIdIndividu;
    }

    public int getIdMere() {
        return iIdMere;
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM RELATIONS_MERE WHERE id_individu = " + iIdIndividu; 
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<Integer, M_Relation_Mere> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Relation_Mere> lhmLesRelations = new LinkedHashMap<>();
        
        String sqlStr = "SELECT * FROM RELATIONS_MERE";
        
        ResultSet res = db.sqlSelect(sqlStr);
        
        while (res.next()) {
            M_Relation_Mere uneRelation = new M_Relation_Mere(db, res.getInt("id_individu"));
            
            lhmLesRelations.put(uneRelation.getIdIndividu(), uneRelation);
        }
        
        return lhmLesRelations;
    }
    
    public static LinkedHashMap<Integer, M_Relation_Mere> getRelationsPourArbre
        (Db_mariadb db, int iIdArbre) throws SQLException {
        LinkedHashMap<Integer, M_Relation_Mere> lhmLesRelations = new LinkedHashMap<>();

        String strSql = """
            SELECT r.id_individu, r.id_mere
            FROM RELATIONS_MERE r
            INNER JOIN INDIVIDUS i ON r.id_individu = i.id_individu
            WHERE i.id_arbre = """ + iIdArbre;

        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            int iIdIndividu = res.getInt("id_individu");
            int iIdMere = res.getInt("id_mere");
            M_Relation_Mere uneRelation = new M_Relation_Mere(db, iIdIndividu, iIdMere);
            lhmLesRelations.put(iIdIndividu, uneRelation);
        }

        return lhmLesRelations;
    }

    @Override
    public String toString() {
        return "M_RelationMere{" +
                "idIndividu=" + iIdIndividu +
                ", idMere=" + iIdMere +
                '}';
    }
}
