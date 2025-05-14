package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class M_Genre {
    private Db_mariadb db;
    private String codeGenre;
    private String libelle;

    public M_Genre(Db_mariadb db, String codeGenre) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM GENRES WHERE code_genre = '" + codeGenre + "'";
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.codeGenre = res.getString("code_genre");
            this.libelle = res.getString("libelle");
        }
    }

    public M_Genre(Db_mariadb db, String codeGenre, String libelle) throws SQLException {
        this.db = db;
        this.codeGenre = codeGenre;
        this.libelle = libelle;

        String sql = "INSERT INTO GENRES (code_genre, libelle) VALUES ('" + codeGenre + "', '" + libelle + "')";
        db.sqlExec(sql);
    }

    public String getCodeGenre() {
        return codeGenre;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void update() throws SQLException {
        String sql = "UPDATE GENRES SET libelle = '" + libelle + "' WHERE code_genre = '" + codeGenre + "'";
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM GENRES WHERE code_genre = '" + codeGenre + "'");
    }

    public static LinkedHashMap<String, M_Genre> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<String, M_Genre> genres = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM GENRES ORDER BY libelle");
        while (res.next()) {
            M_Genre g = new M_Genre(db, res.getString("code_genre"));
            genres.put(g.getCodeGenre(), g);
        }
        return genres;
    }

    @Override
    public String toString() {
        return "M_Genre{" +
                "codeGenre='" + codeGenre + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
