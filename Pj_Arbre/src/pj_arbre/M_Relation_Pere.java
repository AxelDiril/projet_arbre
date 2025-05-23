package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Relation_Pere {
    private Db_mariadb db;
    private int iIdIndividu;
    private int iIdPere;

    public M_Relation_Pere(Db_mariadb db, int iIdIndividu) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM RELATIONS_PERE WHERE id_individu = " + iIdIndividu;
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.iIdIndividu = res.getInt("id_individu");
            this.iIdPere = res.getInt("id_pere");
        }
    }

    public M_Relation_Pere(Db_mariadb db, int iIdIndividu, int iIdPere) throws SQLException {
        this.db = db;
        this.iIdIndividu = iIdIndividu;
        this.iIdPere = iIdPere;

        String strSql = "INSERT INTO RELATIONS_PERE (id_individu, id_pere)" +
                "VALUES (" + iIdIndividu + "," + iIdPere + ")" +
                "ON DUPLICATE KEY UPDATE id_pere = VALUES(id_pere)";
        
        db.sqlExec(strSql);
    }

    public int getIdIndividu() {
        return iIdIndividu;
    }

    public int getIdPere() {
        return iIdPere;
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM RELATIONS_PERE WHERE id_individu = " + iIdIndividu; 
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<Integer, M_Relation_Pere> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Relation_Pere> lhmLesRelations = new LinkedHashMap<>();
        
        String sqlStr = "SELECT * FROM RELATIONS_PERE";
        
        ResultSet res = db.sqlSelect(sqlStr);
        
        while (res.next()) {
            M_Relation_Pere uneRelation = new M_Relation_Pere(db, res.getInt("id_individu"));
            
            lhmLesRelations.put(uneRelation.getIdIndividu(), uneRelation);
        }
        
        return lhmLesRelations;
    }
    
    public static LinkedHashMap<Integer, M_Relation_Pere> getRelationsPourArbre
        (Db_mariadb db, int iIdArbre) throws SQLException {
        LinkedHashMap<Integer, M_Relation_Pere> lhmLesRelations = new LinkedHashMap<>();

        String strSql = """
            SELECT r.id_individu, r.id_pere
            FROM RELATIONS_PERE r
            INNER JOIN INDIVIDUS i ON r.id_individu = i.id_individu
            WHERE i.id_arbre = """ + iIdArbre;

        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            int iIdIndividu = res.getInt("id_individu");
            int iIdPere = res.getInt("id_pere");
            M_Relation_Pere uneRelation = new M_Relation_Pere(db, iIdIndividu, iIdPere);
            lhmLesRelations.put(iIdIndividu, uneRelation);
        }

        return lhmLesRelations;
    }

    @Override
    public String toString() {
        return "M_RelationPere{" +
                "idIndividu=" + iIdIndividu +
                ", idPere=" + iIdPere +
                '}';
    }
}
