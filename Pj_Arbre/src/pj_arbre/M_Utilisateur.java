package pj_arbre;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class M_Utilisateur {
    private Db_mariadb db;
    private int idUtilisateur;
    private String login;
    private String mdp;
    private String mail;
    private String mailToken;
    private LocalDateTime mailDate;
    private LocalDateTime dateCreation;
    private int idStatut;
    private String codeRole;
    private String comment;

    public M_Utilisateur(Db_mariadb db, int idUtilisateur) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM UTILISATEURS WHERE id_utilisateur = " + idUtilisateur;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.idUtilisateur = idUtilisateur;
            this.login = res.getString("login");
            this.mdp = res.getString("mdp");
            this.mail = res.getString("mail");
            this.mailToken = res.getString("mail_token");
            this.mailDate = res.getObject("mail_date", LocalDateTime.class);
            this.dateCreation = res.getObject("date_creation", LocalDateTime.class);
            this.idStatut = res.getInt("id_statut");
            this.codeRole = res.getString("code_role");
            this.comment = res.getString("comment");
        }
    }

    public M_Utilisateur(Db_mariadb db, String login, String mdp, String mail, String mailToken, LocalDateTime mailDate, int idStatut, String codeRole, String comment) throws SQLException {
        this.db = db;
        this.login = login;
        this.mdp = BCrypt.withDefaults().hashToString(12, mdp.toCharArray());
        this.mail = mail;
        this.mailToken = mailToken;
        this.mailDate = mailDate;
        this.dateCreation = LocalDateTime.now();
        this.idStatut = idStatut;
        this.codeRole = codeRole;
        this.comment = comment;

        String sql = "INSERT INTO UTILISATEURS (login, mdp, mail, mail_token, mail_date, date_creation, id_statut, code_role, comment) VALUES ('"
        + login + "', '" + this.mdp + "', '" + mail + "', '" + mailToken + "', '"
        + mailDate + "', '" + dateCreation + "', " + idStatut + ", '" + codeRole + "', '" + comment + "')";

        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.idUtilisateur = res.getInt("id");
        }
    }

    public int getId() {
        return idUtilisateur;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public String getMail() {
        return mail;
    }

    public String getMailToken() {
        return mailToken;
    }

    public LocalDateTime getMailDate() {
        return mailDate;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public int getIdStatut() {
        return idStatut;
    }

    public String getCodeRole() {
        return codeRole;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMailToken(String mailToken) {
        this.mailToken = mailToken;
    }

    public void setMailDate(LocalDateTime mailDate) {
        this.mailDate = mailDate;
    }

    public void setIdStatut(int idStatut) {
        this.idStatut = idStatut;
    }

    public void setCodeRole(String codeRole) {
        this.codeRole = codeRole;
    }

    public void setMdp(String nouveauMp) {
        this.mdp = BCrypt.withDefaults().hashToString(12, nouveauMp.toCharArray());
    }

    public void update() throws SQLException {
        String sql = "UPDATE UTILISATEURS SET login='" + login + "', mdp='" + mdp + "', mail='" + mail +
        "', mail_token='" + mailToken + "', mail_date='" + mailDate + "', date_creation='" + dateCreation + "', " +
        "id_statut=" + idStatut + ", code_role='" + codeRole + "', comment='" + comment + "' WHERE id_utilisateur=" + idUtilisateur;
    }

    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM UTILISATEURS WHERE id_utilisateur = " + idUtilisateur);
    }

    public static M_Utilisateur connexion_log(Db_mariadb db, String login, String mdp) throws SQLException {
        String sql = "SELECT * FROM UTILISATEURS WHERE login= '" + login + "'";
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            String hash = res.getString("mdp");
            if (BCrypt.verifyer().verify(mdp.toCharArray(), hash).verified) {
                return new M_Utilisateur(db, res.getInt("id_utilisateur"));
            }
        }
        return null;
    }

    public static LinkedHashMap<Integer, M_Utilisateur> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Utilisateur> utilisateurs = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM UTILISATEURS ORDER BY login");
        while (res.next()) {
            M_Utilisateur u = new M_Utilisateur(db, res.getInt("id_utilisateur"));
            utilisateurs.put(u.getId(), u);
        }
        return utilisateurs;
    }

    @Override
    public String toString() {
        return "M_Utilisateur{" +
                "id_utilisateur=" + idUtilisateur +
                ", login='" + login + '\'' +
                ", mail='" + mail + '\'' +
                ", mailToken='" + mailToken + '\'' +
                ", mailDate=" + mailDate +
                ", dateCreation=" + dateCreation +
                ", idStatut=" + idStatut +
                ", codeRole='" + codeRole + '\'' +
                '}';
    }
}
