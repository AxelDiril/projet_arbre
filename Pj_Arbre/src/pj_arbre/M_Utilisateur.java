package pj_arbre;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class M_Utilisateur {
    private Db_mariadb db;
    private int iIdUtilisateur;
    private String strLogin;
    private String strMdp;
    private String strMail;
    private String strMailToken;
    private LocalDateTime dtMailDate;
    private LocalDateTime dtDateCreation;
    private int iIdStatut;
    private String strCodeRole;
    private String strComment;

    public M_Utilisateur(Db_mariadb db, int iIdUtilisateur) throws SQLException {
        this.db = db;
        
        String strSql =  "SELECT * FROM UTILISATEURS WHERE id_utilisateur = " + iIdUtilisateur;
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            this.iIdUtilisateur = iIdUtilisateur;
            this.strLogin = res.getString("login");
            this.strMdp = res.getString("mdp");
            this.strMail = res.getString("mail");
            this.strMailToken = res.getString("mail_token");
            this.dtMailDate = res.getObject("mail_date", LocalDateTime.class);
            this.dtDateCreation = res.getObject("date_creation", LocalDateTime.class);
            this.iIdStatut = res.getInt("id_statut");
            this.strCodeRole = res.getString("code_role");
            this.strComment = res.getString("comment");
        }
    }

    public M_Utilisateur(Db_mariadb db, String strLogin, String strMdp, String strMail,
            String strMailToken, LocalDateTime dtMailDate, int iIdStatut,
            String strCodeRole, String strComment) throws SQLException {
        this.db = db;
        this.strLogin = strLogin;
        this.strMdp = BCrypt.withDefaults().hashToString(12, strMdp.toCharArray());
        this.strMail = strMail;
        this.strMailToken = strMailToken;
        this.dtMailDate = dtMailDate;
        this.dtDateCreation = LocalDateTime.now();
        this.iIdStatut = iIdStatut;
        this.strCodeRole = strCodeRole;
        this.strComment = strComment;

        String strSql = "INSERT INTO UTILISATEURS (login, mdp, mail," + ""
                + "mail_token, mail_date, date_creation, id_statut," +
                "code_role, comment) VALUES ('"
        + strLogin + "', '" + this.strMdp + "', '" + strMail + "', '" + strMailToken + "', '"
        + dtMailDate + "', '" + dtDateCreation + "', " + iIdStatut + ", '" +
                strCodeRole + "', '" + strComment + "')";

        db.sqlExec(strSql);

        ResultSet res = db.sqlLastId();
        
        if (res.first()) {
            this.iIdUtilisateur = res.getInt("id");
        }
    }

    public int getIdUtilisateur() {
        return iIdUtilisateur;
    }

    public String getLogin() {
        return strLogin;
    }

    public String getMdp() {
        return strMdp;
    }

    public String getMail() {
        return strMail;
    }

    public String getMailToken() {
        return strMailToken;
    }

    public LocalDateTime getMailDate() {
        return dtMailDate;
    }

    public LocalDateTime getDateCreation() {
        return dtDateCreation;
    }

    public int getIdStatut() {
        return iIdStatut;
    }

    public String getCodeRole() {
        return strCodeRole;
    }

    public void setLogin(String strLogin) {
        this.strLogin = strLogin;
    }

    public void setMail(String strMail) {
        this.strMail = strMail;
    }

    public void setMailToken(String strMailToken) {
        this.strMailToken = strMailToken;
    }

    public void setMailDate(LocalDateTime dtMailDate) {
        this.dtMailDate = dtMailDate;
    }

    public void setIdStatut(int iIdStatut) {
        this.iIdStatut = iIdStatut;
    }

    public void setCodeRole(String strCodeRole) {
        this.strCodeRole = strCodeRole;
    }

    public void setMdp(String strNouveauMdp) {
        this.strMdp = BCrypt.withDefaults().hashToString(12, strNouveauMdp.toCharArray());
    }

    public void update() throws SQLException {
        String strSql = "UPDATE UTILISATEURS SET login='" + strLogin + "', mdp='" +
                strMdp + "', mail='" + strMail + "', mail_token='" + strMailToken +
                "', mail_date='" + dtMailDate + "', date_creation='" + dtDateCreation +
                "', " + "id_statut=" + iIdStatut + ", code_role='" + strCodeRole +
                "', comment='" + strComment + "' WHERE id_utilisateur=" + iIdUtilisateur;
        
        db.sqlExec(strSql);
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM UTILISATEURS WHERE id_utilisateur =" + iIdUtilisateur;
        
        db.sqlExec(strSql);
    }

    public static M_Utilisateur connexion_log(Db_mariadb db, String strLogin, String strMdp)
            throws SQLException {
        M_Utilisateur unUtilisateur = null;
        String strSql = "SELECT * FROM UTILISATEURS WHERE login= '" + strLogin + "'";
        
        ResultSet res = db.sqlSelect(strSql);
        
        if (res.first()) {
            String hash = res.getString("mdp");
            if (BCrypt.verifyer().verify(strMdp.toCharArray(), hash).verified) {
                unUtilisateur = new M_Utilisateur(db, res.getInt("id_utilisateur"));
            }
        }
        
        return unUtilisateur;
    }

    public static LinkedHashMap<Integer, M_Utilisateur> getRecords(Db_mariadb db)
            throws SQLException {
        LinkedHashMap<Integer, M_Utilisateur> lhmLesUtilisateurs = new LinkedHashMap<>();
        
        String strSql = "SELECT * FROM UTILISATEURS ORDER BY login";
        
        ResultSet res = db.sqlSelect(strSql);
        
        while (res.next()) {
            M_Utilisateur unUtilisateur = new M_Utilisateur(db, res.getInt("id_utilisateur"));
            lhmLesUtilisateurs.put(unUtilisateur.getIdUtilisateur(), unUtilisateur);
        }
        return lhmLesUtilisateurs;
    }

    @Override
    public String toString() {
        return "M_Utilisateur{" +
                "id_utilisateur=" + iIdUtilisateur +
                ", login='" + strLogin + '\'' +
                ", mail='" + strMail + '\'' +
                ", mailToken='" + strMailToken + '\'' +
                ", mailDate=" + dtMailDate +
                ", dateCreation=" + dtDateCreation +
                ", idStatut=" + iIdStatut +
                ", codeRole='" + strCodeRole + '\'' +
                '}';
    }
}
