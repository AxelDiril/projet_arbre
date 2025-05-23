package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Genre {
    private Db_mariadb db;
    private String strCodeGenre;
    private String strLibelle;

    public M_Genre(Db_mariadb db, String strCodeGenre) throws SQLException {
        this.db = db;
        
        String strSql = "SELECT * FROM GENRES WHERE code_genre = '" + strCodeGenre + "'";
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.strCodeGenre = res.getString("code_genre");
            this.strLibelle = res.getString("libelle");
        }
    }

    public M_Genre(Db_mariadb db, String strCodeGenre, String strLibelle) throws SQLException {
        this.db = db;
        this.strCodeGenre = strCodeGenre;
        this.strLibelle = strLibelle;

        String strSql = "INSERT INTO GENRES (code_genre, libelle) VALUES ('" + 
                strCodeGenre + "', '" + strLibelle + "')";
        
        db.sqlExec(strSql);
    }

    public String getCodeGenre() {
        return strCodeGenre;
    }

    public String getLibelle() {
        return strLibelle;
    }

    public void setLibelle(String strLibelle) {
        this.strLibelle = strLibelle;
    }

    public void update() throws SQLException {
        String strSql = "UPDATE GENRES SET libelle = '" + strLibelle + 
                "' WHERE code_genre = '" + strCodeGenre + "'";
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM GENRES WHERE code_genre = '" +
                strCodeGenre + "'" ;
        
        db.sqlExec(strSql);
    }

    public static LinkedHashMap<String, M_Genre> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<String, M_Genre> lhmLesGenres = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM GENRES ORDER BY libelle";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            M_Genre unGenre = new M_Genre(db, res.getString("code_genre"));
            lhmLesGenres.put(unGenre.getCodeGenre(), unGenre);
        }
        
        return lhmLesGenres;
    }

    @Override
    public String toString() {
        return "M_Genre{" +
                "codeGenre='" + strCodeGenre + '\'' +
                ", libelle='" + strLibelle + '\'' +
                '}';
    }
}
