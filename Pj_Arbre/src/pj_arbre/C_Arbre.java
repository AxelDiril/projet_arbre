/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pj_arbre;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.util.ArrayList;

/**
 *
 * @author dirila
 */
public class C_Arbre {

    private Db_mariadb db;
    public V_Main fm_Main;
    private final V_MonCompte fm_MonCompte;
    private final V_Inscription fm_Inscription;
    private final V_Creer_Arbre fm_CreerArbre;
    private final V_MesArbres fm_MesArbres;
    private final V_Arbre fm_Arbre;
    private final V_Individus fm_Individus;
    private final V_Collaborateurs fm_Collaborateurs;
    private final V_Utilisateurs fm_Utilisateurs;
    private LinkedHashMap <Integer, M_Utilisateur> lhmLesUtilisateurs;
    private LinkedHashMap <String, M_Role> lhmLesRoles;
    private M_Utilisateur unUtilisateurConnecte;
    private M_Arbre unArbre;
    private String strCodeAcces;
    
    public C_Arbre() throws Exception{
        this.connection();
        fm_Main = new V_Main(this);
        fm_Inscription = new V_Inscription(fm_Main, true, this);
        fm_MonCompte = new V_MonCompte(fm_Main, true, this);
        fm_CreerArbre = new V_Creer_Arbre(fm_Main, true, this);
        fm_MesArbres = new V_MesArbres(fm_Main, true, this);
        fm_Arbre = new V_Arbre(fm_Main, true, this);
        fm_Individus = new V_Individus(fm_Main, true, this);
        fm_Utilisateurs = new V_Utilisateurs(fm_Main, true, this);
        fm_Collaborateurs = new V_Collaborateurs(fm_Main, true, this);
        fm_Main.afficher();
    }
    
    // Méthodes d'affichage
    
    public void aff_V_Inscription() throws SQLException{
        fm_Inscription.afficher();
    }
    
    public void aff_V_MonCompte() throws SQLException{
        fm_MonCompte.afficher(unUtilisateurConnecte);
    }
    
    public void aff_V_Creer_Arbre(){
        fm_CreerArbre.afficher(unUtilisateurConnecte);
    }
    
    public void aff_V_Utilisateurs() throws SQLException{
        lhmLesUtilisateurs = M_Utilisateur.getRecords(db);
        lhmLesRoles = M_Role.getRecords(db);
        fm_Utilisateurs.afficher(lhmLesUtilisateurs, unUtilisateurConnecte);
    }
    
    public void aff_V_Arbre(M_Arbre unArbre, String strCodeAcces) throws SQLException{
        this.unArbre = unArbre;
        this.strCodeAcces = strCodeAcces;
        
        LinkedHashMap<Integer, M_Individu> lhmLesIndividus = 
                M_Individu.getIndividusPourArbre(db, unArbre.getIdArbre());
        
        LinkedHashMap<Integer, M_Relation_Mere> lhmLesRelationsMere = 
                M_Relation_Mere.getRelationsPourArbre(db, unArbre.getIdArbre());
        
        LinkedHashMap<Integer, M_Relation_Pere> lhmLesRelationsPere = 
                M_Relation_Pere.getRelationsPourArbre(db, unArbre.getIdArbre());
        
        LinkedHashMap<Integer, M_Evenement> lhmLesEvenements = M_Evenement.getRecords(db);
        
        LinkedHashMap<Integer, ArrayList<M_Avoir_Evenement>> lhmLesEvenementsArbre =
                M_Avoir_Evenement.getEvenementsPourArbre(db, unArbre.getIdArbre());

        fm_Arbre.afficher(unArbre, lhmLesIndividus, lhmLesRelationsMere,
                lhmLesRelationsPere, lhmLesEvenements, lhmLesEvenementsArbre);
    }
    
    public void aff_V_MesArbres() throws SQLException {
        LinkedHashMap<Integer, M_Acceder> lhmLesAccesUtilisateur =
                M_Acceder.getAccesUtilisateur(db, unUtilisateurConnecte.getIdUtilisateur());
        
        LinkedHashMap<Integer, M_Arbre> lhmLesArbres = M_Arbre.getRecords(db);
        
        LinkedHashMap<String, M_Acces_Arbre> lhmLesAcces = M_Acces_Arbre.getRecords(db);

        fm_MesArbres.afficher(lhmLesAccesUtilisateur, lhmLesAcces, lhmLesArbres);
    }
    
    public void aff_V_Individus(M_Arbre unArbre) throws SQLException{
        LinkedHashMap<Integer, M_Individu> lhmLesIndividus =
                M_Individu.getIndividusPourArbre(db, unArbre.getIdArbre());
        
        LinkedHashMap<String, M_Genre> lhmLesGenres = M_Genre.getRecords(db);
        
        LinkedHashMap<Integer, M_Type_Evenement> lhmLesTypesEvenement = M_Type_Evenement.getRecords(db);
        
        LinkedHashMap<Integer, M_Relation_Mere> lhmLesRelationsMere = M_Relation_Mere.getRecords(db);
        
        LinkedHashMap<Integer, M_Relation_Pere> lhmLesRelationsPere = M_Relation_Pere.getRecords(db);
        
        fm_Individus.afficher(lhmLesIndividus, unArbre, lhmLesGenres, lhmLesTypesEvenement,
                strCodeAcces, lhmLesRelationsMere, lhmLesRelationsPere);
    }
    
    public void aff_V_Collaborateurs(M_Arbre unArbre) throws SQLException{
        LinkedHashMap<String, M_Acces_Arbre> lhmLesAcces = M_Acces_Arbre.getRecords(db);
        
        LinkedHashMap<Integer, M_Acceder> lhmLesAccesArbres =
                M_Acceder.getAccesPourArbre(db,unArbre.getIdArbre());
        
        LinkedHashMap<Integer, M_Utilisateur> lhmLesUtilisateurs = M_Utilisateur.getRecords(db);
        
        System.out.println(unUtilisateurConnecte);
        
        fm_Collaborateurs.afficher(lhmLesUtilisateurs, lhmLesAcces,
                lhmLesAccesArbres, unArbre, strCodeAcces, unUtilisateurConnecte);
    }
    
    // Méthodes individus
    
    public void insertIndividu(String strNom, String strPRenom, String strCodeGenre,
            M_Arbre unArbre) throws SQLException{
        M_Individu unIndividu = new M_Individu(db, strNom, strPRenom, strCodeGenre,
                unArbre.getIdArbre());
        
        aff_V_Individus(unArbre);
    }

    public void updateIndividu(int strIdIndividu, String strNom, String strPrenom,
            String strCodeGenre, M_Arbre unArbre) throws SQLException {
        M_Individu unIndividu = new M_Individu(db, strIdIndividu);
        
        unIndividu.setNom(strNom);
        unIndividu.setPrenom(strPrenom);
        unIndividu.setCodeGenre(strCodeGenre);
        unIndividu.setIdArbre(unArbre.getIdArbre());

        unIndividu.update();
    }
    
    public void supprimerIndividu(int iIdIndividu) throws SQLException {
        M_Individu unIndividu = new M_Individu(db,iIdIndividu);
        unIndividu.delete();
        
        aff_V_Individus(unArbre);
    }
    
    // Méthodes relations mère et père
    
    public void enregistrerMere(int iIdIndividu, int iIdMere) throws SQLException{
        M_Relation_Mere uneRelation = new M_Relation_Mere(db, iIdIndividu, iIdMere);
    }
    
    public void deleteRelationMere(int iIdIndividu) throws SQLException{
        M_Relation_Mere uneRelation = new M_Relation_Mere(db, iIdIndividu);
        uneRelation.delete();
    }
    
    public void enregistrerPere(int iIdIndividu, int iIdPere) throws SQLException{
        M_Relation_Pere uneRelation = new M_Relation_Pere(db, iIdIndividu, iIdPere);
    }
    
    public void deleteRelationPere(int iIdPere) throws SQLException{
        M_Relation_Pere uneRelation = new M_Relation_Pere(db, iIdPere);
        uneRelation.delete();
    }
    
    // Méthodes évènement
    
    public void insert_evenement(int iIdIndividu, int iIdTypeEvenement,
            String strLieu, LocalDate dDate) throws SQLException{
        M_Evenement unEvenement = new M_Evenement(db, dDate, iIdTypeEvenement, strLieu);
        
        M_Avoir_Evenement unEvenementIndividu = new M_Avoir_Evenement(db,
                iIdIndividu, unEvenement.getIdEvenement());
        
        aff_V_Individus(unArbre);
    }
    
    public void update_evenement(int iIdEvenement,int iIdTypeEvenement, String strLieu,
            LocalDate dDate) throws SQLException{
        M_Evenement unEvenement = new M_Evenement(db, iIdEvenement);
        unEvenement.setDate(dDate);
        unEvenement.setLieu(strLieu);
        unEvenement.setIdTypeEvenement(iIdTypeEvenement);
        
        unEvenement.update();
        
        aff_V_Individus(unArbre);
    }
    
    public void delete_evenement(int iIdEvenement) throws SQLException{
        M_Evenement unEvenement = new M_Evenement(db, iIdEvenement);
        unEvenement.delete();
        
        aff_V_Individus(unArbre);
    }
    
    public LinkedHashMap<Integer, M_Evenement> getEvenements(M_Individu unIndividu) throws SQLException{
        return M_Evenement.getEvenementPourIndividu(db, unIndividu.getIdIndividu());
    }
    
    // Méthodes utilisateur
    
    public void insertUtilisateur(String strLogin, String strMail, String strMdp) throws SQLException {
        LocalDateTime mailDate = LocalDateTime.now();

        M_Utilisateur unUtilisateur = new M_Utilisateur(
            db, strLogin, strMdp, strMail, "", mailDate, 1, "U",""
        );
    }
    
    public void update_Compte(int iIdIndividu ,String strLogin, String strMail) throws SQLException{
        M_Utilisateur unUtilisateur = new M_Utilisateur(db, iIdIndividu);
        unUtilisateur.setLogin(strLogin);
        unUtilisateur.setMail(strMail);
        
        unUtilisateur.update();
        
        unUtilisateurConnecte = unUtilisateur;
    }
    
    public void delete_Compte(int iIdIndividu) throws SQLException{
        M_Utilisateur unUtilisateur = new M_Utilisateur(db, iIdIndividu);
        unUtilisateur.delete();
        
        aff_V_Utilisateurs();
    }
    
    // Méthodes Accès Arbre
    
    public void enregistrerAccesArbre(int iIdIndividu, M_Arbre unArbre, String strAccesArbre) throws SQLException{
        M_Acceder unAccesArbre = new M_Acceder(db, iIdIndividu, unArbre.getIdArbre(), strAccesArbre);
        
        aff_V_Collaborateurs(unArbre);
    }
    
    public void delete_AccesArbre(int iIdIndividu, M_Arbre unArbre) throws SQLException{
        M_Acceder unAccesArbre = new M_Acceder(db, iIdIndividu, unArbre.getIdArbre());
        
        unAccesArbre.delete();
        
        aff_V_Collaborateurs(unArbre);
    }
    
    // Méthodes utilisateurs
        
    public boolean verif_Mp(int iIdIndividu, String strLogin, String strAncienMdp,
            String strNouveauMdp) throws SQLException {
        lhmLesUtilisateurs = M_Utilisateur.getRecords(db);
        M_Utilisateur unUtilisateur = lhmLesUtilisateurs.get(iIdIndividu);
        boolean resultat = false;

        String mpHash = unUtilisateur.getMdp();
        
        if (BCrypt.verifyer().verify(strAncienMdp.toCharArray(), mpHash).verified) {
            unUtilisateur.setMdp(strNouveauMdp);
            unUtilisateur.update();
            resultat = true;
        }
        
        return resultat;
    }
    
    public M_Utilisateur verif_utilisateur(String strLogin, String strMdp) throws SQLException{
        unUtilisateurConnecte = M_Utilisateur.connexion_log(db, strLogin, strMdp);
        
        System.out.println(unUtilisateurConnecte);
        
        return unUtilisateurConnecte;
    }
    
    public LinkedHashMap<String, M_Action> actions_Util(M_Utilisateur unUtilisateur) throws SQLException{
        LinkedHashMap<String, M_Action> lhmLesActions;
        
        lhmLesActions = M_Action.getActionsPourRole(db, unUtilisateur.getCodeRole());
        
        return lhmLesActions;
    }
    
    public void deconnexion(){
        unUtilisateurConnecte = null;
    }
    
    private void connection() throws Exception{
        db = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
    }
    
    // Méthodes arbre
    
    public void insertArbre(String strNom, int iIdUtilisateur) throws SQLException{
        M_Arbre unArbre = new M_Arbre(db,strNom,iIdUtilisateur);
        M_Acceder unAcces = new M_Acceder(db,iIdUtilisateur,unArbre.getIdArbre(),"C");
    }
    
    public void delete_Arbre(int iIdArbre) throws SQLException{
        M_Arbre unArbre = new M_Arbre(db,iIdArbre);
        unArbre.delete();
        
        aff_V_MesArbres();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        C_Arbre leControleur = new C_Arbre();
    }

    
}
