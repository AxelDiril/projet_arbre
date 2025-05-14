/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pj_arbre;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Set;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author dirila
 */
public class C_Arbre {

    private Db_mariadb baseFU;
    public V_Main fm_Main;
    private V_MonCompte fm_MonCompte;
    private V_Inscription fm_Inscription;
    private V_Creer_Arbre fm_CreerArbre;
    private V_MesArbres fm_MesArbres;
    private V_Arbre fm_Arbre;
    private V_Individus fm_Individus;
    private LinkedHashMap <Integer, M_Utilisateur> lesUtilisateurs;
    private LinkedHashMap <String, M_Role> lesRoles;
    private M_Utilisateur unUtilisateur;
    private M_Utilisateur utilConnecte;
    
    public C_Arbre() throws Exception{
        this.connection();
        fm_Main = new V_Main(this);
        fm_Inscription = new V_Inscription(fm_Main, true, this);
        fm_MonCompte = new V_MonCompte(fm_Main, true, this);
        fm_CreerArbre = new V_Creer_Arbre(fm_Main, true, this);
        fm_MesArbres = new V_MesArbres(fm_Main, true, this);
        fm_Arbre = new V_Arbre(fm_Main, true, this);
        fm_Individus = new V_Individus(fm_Main, true, this);
        fm_Main.afficher();
    }
    
    public void aff_V_Inscription() throws SQLException{
        fm_Inscription.afficher();
    }
    
    public void aff_V_MonCompte() throws SQLException{
        fm_MonCompte.afficher(utilConnecte);
    }
    
    public void aff_V_Creer_Arbre(){
        fm_CreerArbre.afficher(utilConnecte);
    }
    
    public void aff_V_Arbre(M_Arbre unArbre) throws SQLException{
        LinkedHashMap<Integer, M_Individu> lesIndividus = M_Individu.getIndividusPourArbre(baseFU, unArbre.getIdArbre());
        fm_Arbre.afficher(unArbre, lesIndividus);
    }
    
    public void aff_V_MesArbres() throws SQLException {
        System.out.println("Afficher mes arbres");
        LinkedHashMap<Integer, M_Acceder> lesAccesUtilisateur = M_Acceder.getAccesUtilisateur(baseFU, utilConnecte.getId());
        LinkedHashMap<Integer, M_Arbre> lesArbres = M_Arbre.getRecords(baseFU);
        LinkedHashMap<String, M_Acces_Arbre> lesAcces = M_Acces_Arbre.getRecords(baseFU);

        fm_MesArbres.afficher(lesAccesUtilisateur, lesAcces, lesArbres);
    }
    
    public void aff_V_Individus(M_Arbre unArbre) throws SQLException{
        LinkedHashMap<Integer, M_Individu> lesIndividus = M_Individu.getIndividusPourArbre(baseFU, unArbre.getIdArbre());
        LinkedHashMap<String, M_Genre> lesGenres = M_Genre.getRecords(baseFU);
        LinkedHashMap<Integer, M_Type_Evenement> lesTypesEvenements = M_Type_Evenement.getRecords(baseFU);
        fm_Individus.afficher(lesIndividus, unArbre, lesGenres, lesTypesEvenements);
    }
    
    public void insertIndividu(String nom, String prenom, String codeGenre, M_Arbre unArbre) throws SQLException{
        M_Individu unIndividu = new M_Individu(baseFU, nom, prenom, "", codeGenre, unArbre.getIdArbre());
    }

    public void updateIndividu(int idIndividu, String nom, String prenom, String codeGenre, M_Arbre unArbre) throws SQLException {
        M_Individu unIndividu = new M_Individu(baseFU, idIndividu);

        unIndividu.setNom(nom);
        unIndividu.setPrenom(prenom);
        unIndividu.setCodeGenre(codeGenre);
        unIndividu.setIdArbre(unArbre.getIdArbre());

        unIndividu.update();
    }
    
    public void supprimerIndividu(int idIndividu) throws SQLException {
        M_Individu unIndividu = new M_Individu(baseFU,idIndividu);
        unIndividu.delete();
    }
    
    public LinkedHashMap<Integer, M_Evenement> getEvenements(M_Individu unIndividu) throws SQLException{
        return M_Evenement.getEvenementPourIndividu(baseFU, unIndividu.getIdMembre());
    }
    
    public void insertUtilisateur(String login, String mail, String mdp) throws SQLException {
        LocalDateTime mailDate = LocalDateTime.now();

        M_Utilisateur unUtilisateur = new M_Utilisateur(
            baseFU, login, mdp, mail, "", mailDate, 1, "U",""
        );
    }
    
    public void update_Compte(int id, String login, String mail) throws SQLException{
        lesUtilisateurs = M_Utilisateur.getRecords(baseFU);
        utilConnecte = lesUtilisateurs.get(id);
        utilConnecte.setLogin(login);
        utilConnecte.setMail(mail);
        utilConnecte.update();
        
        aff_V_MonCompte();
    }
    
    public boolean verif_Mp(int id, String login, String ancienMp, String nouveauMp) throws SQLException {
        lesUtilisateurs = M_Utilisateur.getRecords(baseFU);
        M_Utilisateur unUtil = lesUtilisateurs.get(id);

        String mpHash = unUtil.getMdp();
        
        if (BCrypt.verifyer().verify(ancienMp.toCharArray(), mpHash).verified) {
            unUtil.setMdp(nouveauMp);
            unUtil.update();
            return true;
        }
        return false;
    }
    
    public M_Utilisateur verif_utilisateur(String login, String mdp) throws SQLException{
        utilConnecte = M_Utilisateur.connexion_log(baseFU, login, mdp);
        return utilConnecte;
    }
    
    public LinkedHashMap<String, M_Action> actions_Util(M_Utilisateur unUtil) throws SQLException{
        LinkedHashMap<String, M_Action> lesActions;
        lesActions = M_Action.getActionsPourRole(baseFU, unUtil.getCodeRole());
        return lesActions;
    }
    
    public void deconnexion(){
        utilConnecte = null;
    }
    
    private void connection() throws Exception{
        baseFU = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
    }
    
    public void insertArbre(String nom, int idUtilisateur) throws SQLException{
        M_Arbre unArbre = new M_Arbre(baseFU,nom,idUtilisateur);
        M_Acceder unAcces = new M_Acceder(baseFU,idUtilisateur,unArbre.getIdArbre(),"C");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        C_Arbre leControleur = new C_Arbre();
    }

    
}
