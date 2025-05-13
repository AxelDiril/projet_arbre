package pj_arbre;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class M_Utilisateur {
    private Db_mariadb db;
    private int id;
    private String login;
    private String mdp;
    private String mail;
    private String mailToken;
    private LocalDateTime mailDate;
    private LocalDateTime dateCreation;
    private int idStatut;
    private String codeRole;

    // Constructeur de récupération depuis la BDD
    public M_Utilisateur(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        String sql = "SELECT * FROM UTILISATEURS WHERE id = " + id;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            this.id = id;
            this.login = res.getString("login");
            this.mdp = res.getString("mdp");
            this.mail = res.getString("mail");
            this.mailToken = res.getString("mail_token");
            this.mailDate = res.getObject("mail_date", LocalDateTime.class);
            this.dateCreation = res.getObject("date_creation", LocalDateTime.class);
            this.idStatut = res.getInt("id_statut");
            this.codeRole = res.getString("code_role");
        }
    }

    // Constructeur d’insertion
    public M_Utilisateur(Db_mariadb db, String login, String mdp, String mail, String mailToken, LocalDateTime mailDate, int idStatut, String codeRole) throws SQLException {
        this.db = db;
        this.login = login;
        this.mdp = BCrypt.withDefaults().hashToString(12, mdp.toCharArray());
        this.mail = mail;
        this.mailToken = mailToken;
        this.mailDate = mailDate;
        this.dateCreation = LocalDateTime.now();
        this.idStatut = idStatut;
        this.codeRole = codeRole;

        String sql = "INSERT INTO UTILISATEURS (login, mdp, mail, mail_token, mail_date, date_creation, id_statut, code_role) VALUES ('"
                + login + "', '" + this.mdp + "', '" + mail + "', '" + mailToken + "', '"
                + mailDate + "', '" + dateCreation + "', " + idStatut + ", '" + codeRole + "')";
        db.sqlExec(sql);

        ResultSet res = db.sqlLastId();
        if (res.first()) {
            this.id = res.getInt("id");
        }
    }

    // Getters
    public int getId() {
        return id;
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

    // Setters
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

    // Modification du mot de passe
    public void setMdp(String nouveauMp) {
        this.mdp = BCrypt.withDefaults().hashToString(12, nouveauMp.toCharArray());
    }

    // Mise à jour de l'utilisateur dans la BDD
    public void update() throws SQLException {
        String sql = "UPDATE UTILISATEURS SET login='" + login + "', mdp='" + mdp + "', mail='" + mail +
                "', mail_token='" + mailToken + "', mail_date='" + mailDate + "', date_creation='" + dateCreation + "', " +
                "id_statut=" + idStatut + ", code_role='" + codeRole + "' WHERE id=" + id;
        db.sqlExec(sql);
    }

    // Suppression de l'utilisateur
    public void delete() throws SQLException {
        db.sqlExec("DELETE FROM UTILISATEURS WHERE id = " + id);
    }

    // Connexion d'un utilisateur
    public static M_Utilisateur connexion_log(Db_mariadb db, String login, String mdp) throws SQLException {
        String sql = "SELECT * FROM UTILISATEURS WHERE login= '" + login + "'";
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            String hash = res.getString("mdp");
            if (BCrypt.verifyer().verify(mdp.toCharArray(), hash).verified) {
                return new M_Utilisateur(db, res.getInt("id"));
            }
        }
        return null;
    }

    // Récupérer tous les utilisateurs
    public static LinkedHashMap<Integer, M_Utilisateur> getRecords(Db_mariadb db) throws SQLException {
        LinkedHashMap<Integer, M_Utilisateur> utilisateurs = new LinkedHashMap<>();
        ResultSet res = db.sqlSelect("SELECT * FROM UTILISATEURS ORDER BY login");
        while (res.next()) {
            M_Utilisateur u = new M_Utilisateur(db, res.getInt("id"));
            utilisateurs.put(u.getId(), u);
        }
        return utilisateurs;
    }
    
    // Méthode pour obtenir tous les enfants de cet individu en une seule requête
    public LinkedHashMap<Integer, M_Utilisateur> getEnfants() throws SQLException {
        LinkedHashMap<Integer, M_Utilisateur> enfants = new LinkedHashMap<>();

        // UNION pour avoir le résultat des deux requêtes
        String sql = "SELECT id_membre FROM RELATIONS_MERE WHERE id_mere = ? "
                    + "UNION "
                    + "SELECT id_membre FROM RELATIONS_PERE WHERE id_pere = ?";

        // Exécuter la requête en passant l'id de l'individu pour la mère et le père
        ResultSet res = db.sqlSelect(sql);
        while (res.next()) {
            int enfantId = res.getInt("id_membre");
            M_Utilisateur enfant = new M_Utilisateur(db, enfantId);
            enfants.put(enfantId, enfant);
        }

        return enfants;
    }
    
    // Obtenir l'a mère d'un individu
    public M_Utilisateur getMere() throws SQLException {
        String sql = "SELECT id_mere FROM RELATIONS_MERE WHERE id_membre = " + this.id;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            int mereId = res.getInt("id_mere");
            return new M_Utilisateur(db, mereId);
        }
        return null;
    }

    // Obtenir le père d'un individu
    public M_Utilisateur getPere() throws SQLException {
        String sql = "SELECT id_pere FROM RELATIONS_PERE WHERE id_membre = " + this.id;
        ResultSet res = db.sqlSelect(sql);
        if (res.first()) {
            int pereId = res.getInt("id_pere");
            return new M_Utilisateur(db, pereId);
        }
        return null;
    }

    @Override
    public String toString() {
        return "M_Utilisateur{" +
                "id=" + id +
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
