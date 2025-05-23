/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package pj_arbre;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeParseException;

/**
 *
 * @author dirila
 */
public class V_Individus extends javax.swing.JDialog {

    /**
     * Creates new form V_Utilisateurs_Admin
     */
    private C_Arbre leControl;
    private M_Arbre unArbre;
    private boolean bIsModification = false;
    private boolean bIsModificationEvenement = false;
    private LinkedHashMap <Integer, M_Individu> lhmLesIndividus;
    private LinkedHashMap<Integer, M_Evenement> lhmLesEvenements;
    private LinkedHashMap<String, M_Genre> lhmLesGenres;
    private LinkedHashMap<Integer, M_Type_Evenement> lhmLesTypesEvenement;
    private LinkedHashMap<Integer, M_Relation_Mere> lhmLesRelationsMere;
    private LinkedHashMap<Integer, M_Relation_Pere> lhmLesRelationsPere;
    private DefaultTableModel dm_tb_individus;
    private DefaultTableModel dm_tb_evenements;
    private Object[] objTableauClesIndividus;
    private Object[] objTableauClesEvenements;
    private Integer iLigneIndividu;
    private Integer iLigneEvenement;
    private Integer iIndividuSelection;
    private Integer iEvenementSelection;
    private int iCle;
    private String strCodeAcces;
    
    public V_Individus(java.awt.Frame parent, boolean modal, C_Arbre leControl) {           
        super(parent, modal);
        this.leControl = leControl;
        initComponents();
    }
    
    public void afficher(LinkedHashMap<Integer, M_Individu> lhmLesIndividus,
            M_Arbre unArbre, LinkedHashMap<String, M_Genre> lhLesGenres,
            LinkedHashMap<Integer, M_Type_Evenement> lhmLesTypesEvenements,
            String strCodeAcces, LinkedHashMap<Integer, M_Relation_Mere> lhmLesRelationsMere, 
            LinkedHashMap<Integer, M_Relation_Pere> lhmLesRelationsPere) {
        this.lhmLesIndividus = lhmLesIndividus;
        this.unArbre = unArbre;
        this.lhmLesGenres = lhLesGenres;
        this.lhmLesTypesEvenement = lhmLesTypesEvenements;
        this.lhmLesRelationsMere = lhmLesRelationsMere;
        this.lhmLesRelationsPere = lhmLesRelationsPere;
        this.strCodeAcces = strCodeAcces;
        
        iLigneIndividu = -1;
        iLigneEvenement = -1;
        
        iIndividuSelection = null;
        iEvenementSelection = null;

        bIsModification = false;
        bIsModificationEvenement = false;
        
        modeSaisie(false);
        modeSaisieEvenement(false);
        
        // Retirer les fonctions d'édition pour l'accès en lecture
        
        if(strCodeAcces.equals("L")){
            bt_ajout.setEnabled(false);
            bt_modifier.setEnabled(false);
            bt_supprimer.setEnabled(false);

            bt_ajout_evenement.setEnabled(false);
            bt_modifier_evenement.setEnabled(false);
            bt_supprimer_evenement.setEnabled(false);
        }
        
        dm_tb_individus = new DefaultTableModel(new Object[]{"Nom", "Prénom"}, 0);
        tb_individus.setModel(dm_tb_individus);
        
        dm_tb_evenements = new DefaultTableModel(new Object[]{"Evènement", "Date", "Lieu"}, 0);
        tb_evenements.setModel(dm_tb_evenements);
        
        objTableauClesIndividus = lhmLesIndividus.keySet().toArray();

        for (Integer uneCle : lhmLesIndividus.keySet()) {
            M_Individu unIndividu = lhmLesIndividus.get(uneCle);
            
            dm_tb_individus.addRow(new Object[]{
                unIndividu.getNom(),
                unIndividu.getPrenom()
            });
        }
        
        // Remplissage des ComboBox
        
        // Genre
        
        co_genre.removeAllItems();
        co_genre.addItem("Sélectionnez un genre");
        
        for (String codeGenre : lhLesGenres.keySet()) {
            M_Genre genre = lhLesGenres.get(codeGenre);
            
            co_genre.addItem(genre.getLibelle());
        }
        
        // Type Evènement
        
        co_types.removeAllItems();
        co_types.addItem("Sélectionnez un type d'évènement");

        for (M_Type_Evenement unType : lhmLesTypesEvenement.values()) {
            co_types.addItem(unType.getLibelle());
        }
        
        // Mère et père
        
        co_mere.removeAllItems();
        co_pere.removeAllItems();
        co_mere.addItem("Aucun");
        co_pere.addItem("Aucun");
        
        for (M_Individu unIndividu : lhmLesIndividus.values()) {
            co_mere.addItem(unIndividu.getPrenom() + " " + unIndividu.getNom());
            co_pere.addItem(unIndividu.getPrenom() + " " + unIndividu.getNom());
        }

        this.setSize(1000, 670);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void modeSaisie(boolean active) {
        ed_nom.setEditable(active);
        ed_prenom.setEditable(active);
        co_genre.setEnabled(active);

        tb_individus.setEnabled(!active);
        tb_evenements.setEnabled(!active);

        pn_am.setVisible(active);
        pn_cms.setVisible(!active);
        
        bt_ajout.setEnabled(!active);
        bt_modifier.setEnabled(!active);
        bt_supprimer.setEnabled(!active);
        
        bt_ajout_evenement.setEnabled(!active);
        bt_modifier_evenement.setEnabled(!active);
        bt_supprimer_evenement.setEnabled(!active);
        
        co_mere.setEnabled(active);
        co_pere.setEnabled(active);
    }
    
    private void modeSaisieEvenement(boolean active) {
        ed_lieu.setEditable(active);
        ed_date.setEditable(active);

        tb_individus.setEnabled(!active);
        tb_evenements.setEnabled(!active);

        pn_am_evenement.setVisible(active);
        pn_cms_evenement.setVisible(!active);
        
        bt_ajout.setEnabled(!active);
        bt_modifier.setEnabled(!active);
        bt_supprimer.setEnabled(!active);
        
        bt_ajout_evenement.setEnabled(!active);
        bt_modifier_evenement.setEnabled(!active);
        bt_supprimer_evenement.setEnabled(!active);
        
        co_types.setEnabled(active);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sc_evenements = new javax.swing.JScrollPane();
        tb_evenements = new javax.swing.JTable();
        lb_nom = new javax.swing.JLabel();
        lb_prenom = new javax.swing.JLabel();
        lb_genre = new javax.swing.JLabel();
        ed_nom = new javax.swing.JTextField();
        ed_prenom = new javax.swing.JTextField();
        co_genre = new javax.swing.JComboBox<>();
        pn_am = new javax.swing.JPanel();
        bt_annuler = new javax.swing.JButton();
        bt_enregistrer = new javax.swing.JButton();
        pn_cms = new javax.swing.JPanel();
        bt_ajout = new javax.swing.JButton();
        bt_supprimer = new javax.swing.JButton();
        bt_modifier = new javax.swing.JButton();
        sc_util = new javax.swing.JScrollPane();
        tb_individus = new javax.swing.JTable();
        co_types = new javax.swing.JComboBox<>();
        lb_date = new javax.swing.JLabel();
        ed_date = new javax.swing.JFormattedTextField();
        lb_lieu = new javax.swing.JLabel();
        ed_lieu = new javax.swing.JTextField();
        pn_cms_evenement = new javax.swing.JPanel();
        bt_ajout_evenement = new javax.swing.JButton();
        bt_modifier_evenement = new javax.swing.JButton();
        bt_supprimer_evenement = new javax.swing.JButton();
        pn_am_evenement = new javax.swing.JPanel();
        bt_enregistrer_evenement = new javax.swing.JButton();
        bt_annuler_evenement = new javax.swing.JButton();
        lb_evenement = new javax.swing.JLabel();
        co_pere = new javax.swing.JComboBox<>();
        co_mere = new javax.swing.JComboBox<>();
        lb_mere = new javax.swing.JLabel();
        lb_pere = new javax.swing.JLabel();
        bt_retour = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion des individus");
        setResizable(false);
        getContentPane().setLayout(null);

        tb_evenements.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Evenement", "Date", "Lieu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_evenements.getTableHeader().setReorderingAllowed(false);
        tb_evenements.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_evenementsMouseClicked(evt);
            }
        });
        sc_evenements.setViewportView(tb_evenements);
        if (tb_evenements.getColumnModel().getColumnCount() > 0) {
            tb_evenements.getColumnModel().getColumn(0).setResizable(false);
            tb_evenements.getColumnModel().getColumn(1).setResizable(false);
            tb_evenements.getColumnModel().getColumn(2).setResizable(false);
        }

        getContentPane().add(sc_evenements);
        sc_evenements.setBounds(520, 20, 380, 190);

        lb_nom.setText("Nom :");
        getContentPane().add(lb_nom);
        lb_nom.setBounds(30, 300, 60, 20);

        lb_prenom.setText("Prénom :");
        lb_prenom.setToolTipText("");
        getContentPane().add(lb_prenom);
        lb_prenom.setBounds(30, 350, 90, 27);

        lb_genre.setText("Genre :");
        getContentPane().add(lb_genre);
        lb_genre.setBounds(30, 400, 80, 27);
        getContentPane().add(ed_nom);
        ed_nom.setBounds(180, 290, 260, 35);
        getContentPane().add(ed_prenom);
        ed_prenom.setBounds(180, 340, 260, 35);

        co_genre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sélectionnez un genre" }));
        getContentPane().add(co_genre);
        co_genre.setBounds(180, 390, 260, 35);

        bt_annuler.setText("Annuler");
        bt_annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_annulerActionPerformed(evt);
            }
        });

        bt_enregistrer.setText("Enregistrer");
        bt_enregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_enregistrerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_amLayout = new javax.swing.GroupLayout(pn_am);
        pn_am.setLayout(pn_amLayout);
        pn_amLayout.setHorizontalGroup(
            pn_amLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_amLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_enregistrer, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_annuler, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn_amLayout.setVerticalGroup(
            pn_amLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_amLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_amLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_enregistrer)
                    .addComponent(bt_annuler))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pn_am);
        pn_am.setBounds(80, 540, 320, 50);

        bt_ajout.setText("Ajout");
        bt_ajout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ajoutActionPerformed(evt);
            }
        });

        bt_supprimer.setText("Supprimer");
        bt_supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_supprimerActionPerformed(evt);
            }
        });

        bt_modifier.setText("Modifier");
        bt_modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_modifierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_cmsLayout = new javax.swing.GroupLayout(pn_cms);
        pn_cms.setLayout(pn_cmsLayout);
        pn_cmsLayout.setHorizontalGroup(
            pn_cmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cmsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_ajout, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_modifier, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_supprimer, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        pn_cmsLayout.setVerticalGroup(
            pn_cmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_cmsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_ajout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_modifier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_supprimer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pn_cms);
        pn_cms.setBounds(340, 70, 170, 140);

        tb_individus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_individus.getTableHeader().setReorderingAllowed(false);
        tb_individus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_individusMouseClicked(evt);
            }
        });
        sc_util.setViewportView(tb_individus);
        if (tb_individus.getColumnModel().getColumnCount() > 0) {
            tb_individus.getColumnModel().getColumn(0).setResizable(false);
            tb_individus.getColumnModel().getColumn(1).setResizable(false);
        }

        getContentPane().add(sc_util);
        sc_util.setBounds(22, 14, 302, 256);

        co_types.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sélectionnez un évènement" }));
        co_types.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                co_typesActionPerformed(evt);
            }
        });
        getContentPane().add(co_types);
        co_types.setBounds(560, 310, 320, 35);

        lb_date.setText("Date (jj/mm/aaaa) :");
        getContentPane().add(lb_date);
        lb_date.setBounds(630, 350, 180, 27);

        ed_date.setToolTipText("jj/mm/aaaa");
        getContentPane().add(ed_date);
        ed_date.setBounds(560, 380, 320, 35);

        lb_lieu.setText("Lieu :");
        getContentPane().add(lb_lieu);
        lb_lieu.setBounds(690, 430, 48, 27);
        getContentPane().add(ed_lieu);
        ed_lieu.setBounds(560, 460, 320, 35);

        bt_ajout_evenement.setText("Ajouter");
        bt_ajout_evenement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ajout_evenementActionPerformed(evt);
            }
        });

        bt_modifier_evenement.setText("Modifier");
        bt_modifier_evenement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_modifier_evenementActionPerformed(evt);
            }
        });

        bt_supprimer_evenement.setText("Supprimer");
        bt_supprimer_evenement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_supprimer_evenementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_cms_evenementLayout = new javax.swing.GroupLayout(pn_cms_evenement);
        pn_cms_evenement.setLayout(pn_cms_evenementLayout);
        pn_cms_evenementLayout.setHorizontalGroup(
            pn_cms_evenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cms_evenementLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_ajout_evenement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_modifier_evenement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_supprimer_evenement)
                .addContainerGap(7, Short.MAX_VALUE))
        );
        pn_cms_evenementLayout.setVerticalGroup(
            pn_cms_evenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_cms_evenementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_cms_evenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_ajout_evenement)
                    .addComponent(bt_modifier_evenement)
                    .addComponent(bt_supprimer_evenement))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        getContentPane().add(pn_cms_evenement);
        pn_cms_evenement.setBounds(520, 220, 380, 50);

        bt_enregistrer_evenement.setText("Enregistrer évènement");
        bt_enregistrer_evenement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_enregistrer_evenementActionPerformed(evt);
            }
        });

        bt_annuler_evenement.setText("Annuler évènement");
        bt_annuler_evenement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_annuler_evenementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_am_evenementLayout = new javax.swing.GroupLayout(pn_am_evenement);
        pn_am_evenement.setLayout(pn_am_evenementLayout);
        pn_am_evenementLayout.setHorizontalGroup(
            pn_am_evenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_am_evenementLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(pn_am_evenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_annuler_evenement, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_enregistrer_evenement, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        pn_am_evenementLayout.setVerticalGroup(
            pn_am_evenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_am_evenementLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bt_enregistrer_evenement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_annuler_evenement)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pn_am_evenement);
        pn_am_evenement.setBounds(550, 510, 340, 90);

        lb_evenement.setText("Evenement :");
        getContentPane().add(lb_evenement);
        lb_evenement.setBounds(650, 280, 120, 27);

        co_pere.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aucun" }));
        getContentPane().add(co_pere);
        co_pere.setBounds(180, 490, 260, 35);
        co_pere.getAccessibleContext().setAccessibleName("");

        co_mere.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aucun" }));
        co_mere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                co_mereActionPerformed(evt);
            }
        });
        getContentPane().add(co_mere);
        co_mere.setBounds(180, 440, 260, 35);

        lb_mere.setText("Mère :");
        getContentPane().add(lb_mere);
        lb_mere.setBounds(30, 450, 65, 27);

        lb_pere.setText("Père :");
        getContentPane().add(lb_pere);
        lb_pere.setBounds(30, 500, 65, 27);

        bt_retour.setText("<< Retourner sur l'arbre");
        bt_retour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_retourActionPerformed(evt);
            }
        });
        getContentPane().add(bt_retour);
        bt_retour.setBounds(100, 590, 280, 35);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_modifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_modifierActionPerformed
        if (iIndividuSelection != null) {
            modeSaisie(true);
            bIsModification = true;
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un individu à modifier.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_modifierActionPerformed

    private void bt_supprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_supprimerActionPerformed
        if (iIndividuSelection != null) {
            M_Individu unIndividu = lhmLesIndividus.get(iIndividuSelection);

            int iConfirm = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer l’individu : " +
                unIndividu.getPrenom() + " " + unIndividu.getNom() + " ?",
                "Confirmation", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (iConfirm == JOptionPane.YES_OPTION) {
                try {
                    leControl.supprimerIndividu(iIndividuSelection);

                    lhmLesIndividus.remove(iIndividuSelection);

                    objTableauClesIndividus = lhmLesIndividus.keySet().toArray();

                    ed_nom.setText("");
                    ed_prenom.setText("");
                    co_genre.setSelectedIndex(0);

                    JOptionPane.showMessageDialog(this, "Individu supprimé avec succès.", "Mise à jour",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Erreur lors de la suppression : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un "
                    + "individu à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_supprimerActionPerformed

    private void bt_ajoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ajoutActionPerformed
        modeSaisie(true);
        ed_nom.setText("");
        ed_prenom.setText("");
        co_genre.setSelectedIndex(0);
    }//GEN-LAST:event_bt_ajoutActionPerformed

    private void bt_annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_annulerActionPerformed
        modeSaisie(false);
        bIsModification = false;
    }//GEN-LAST:event_bt_annulerActionPerformed

    private void bt_enregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_enregistrerActionPerformed
        String strNom = ed_nom.getText().trim();
        String strPrenom = ed_prenom.getText().trim();
        String strGenreSelectionne = (String) co_genre.getSelectedItem();

        if (strNom.isEmpty() || strPrenom.isEmpty() ||
                strGenreSelectionne.equals("Sélectionnez un genre")) {
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        M_Genre unGenreSelectionne = null;
        
        for (M_Genre genre : lhmLesGenres.values()) {
            if (genre.getLibelle().equals(strGenreSelectionne)) {
                unGenreSelectionne = genre;
                break;
            }
        }

        if (unGenreSelectionne == null) {
            JOptionPane.showMessageDialog(this, "Le genre sélectionné est invalide.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String strCodeGenre = unGenreSelectionne.getCodeGenre();
        
        // Récupérer mère
        
        String strSelectionMere = co_mere.getSelectedItem().toString();
        M_Individu uneMereSelectionne = null;
        
        for (M_Individu unIndividu : lhmLesIndividus.values()) {
            String strNomPrenom = unIndividu.getPrenom() + " " + unIndividu.getNom();
            
            if (strNomPrenom.equals(strSelectionMere)) {
                uneMereSelectionne = unIndividu;
                break;
            }
        }
       
        // Récupérer pere
        
        String strSelectionPere = co_pere.getSelectedItem().toString();
        M_Individu unPereSelectionne = null;
        
        for (M_Individu unIndividu : lhmLesIndividus.values()) {
            String strNomPrenom = unIndividu.getPrenom() + " " + unIndividu.getNom();
            
            if (strNomPrenom.equals(strSelectionPere)) {
                unPereSelectionne = unIndividu;
                break;
            }
        }

        try {
            if (bIsModification) {
                
                if (iIndividuSelection != -1) {
                    leControl.updateIndividu(iIndividuSelection, strNom,
                            strPrenom, strCodeGenre, unArbre);
                    
                    // Vérifie sélection mère
                    
                    if(uneMereSelectionne != null){
                        leControl.enregistrerMere(iIndividuSelection,
                                uneMereSelectionne.getIdIndividu());
                    }
                    else{
                        leControl.deleteRelationMere(iIndividuSelection);
                    }
                    
                    // Vérifie sélection père
                    
                    if(unPereSelectionne != null){
                        leControl.enregistrerPere(iIndividuSelection,
                                unPereSelectionne.getIdIndividu());
                    }
                    else{
                        leControl.deleteRelationPere(iIndividuSelection);
                    }
                    
                    JOptionPane.showMessageDialog(this,
                            "Individu modifié avec succès.");
                    leControl.aff_V_Individus(unArbre);
                }
            } else {
                // Ajout
                
                leControl.insertIndividu(strNom, strPrenom, strCodeGenre, unArbre);
                
                if(uneMereSelectionne != null){
                    leControl.enregistrerMere(iIndividuSelection,
                            uneMereSelectionne.getIdIndividu());
                }
                
                if(unPereSelectionne != null){
                    leControl.enregistrerPere(iIndividuSelection,
                            unPereSelectionne.getIdIndividu());
                }
                JOptionPane.showMessageDialog(this, "Individu ajouté avec succès.",
                        "Mise à jour", JOptionPane.INFORMATION_MESSAGE);
            }

            leControl.aff_V_Individus(unArbre);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(V_Individus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_enregistrerActionPerformed

    private void tb_evenementsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_evenementsMouseClicked
        this.iLigneEvenement = tb_evenements.getSelectedRow();
        
        if (iLigneEvenement >= 0 && objTableauClesEvenements != null && 
            iLigneEvenement < objTableauClesEvenements.length) {
            this.iEvenementSelection = (int) objTableauClesEvenements[iLigneEvenement];
            M_Evenement unEvenement = lhmLesEvenements.get(iEvenementSelection);
            
            if (unEvenement != null) {
                LocalDate dDate = unEvenement.getDate();
                
                if (dDate != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    ed_date.setText(dDate.format(formatter));
                } else {
                    ed_date.setText("Date inconnue");
                }
                
                ed_lieu.setText(unEvenement.getLieu());
                M_Type_Evenement unType = lhmLesTypesEvenement.get(unEvenement.getIdTypeEvenement());
                
                if (unType != null) {
                    co_types.setSelectedItem(unType.getLibelle());
                }
            } else {
                lb_date.setText("Événement introuvable");
            }
        }
    }//GEN-LAST:event_tb_evenementsMouseClicked

    private void tb_individusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_individusMouseClicked
        this.iLigneEvenement = tb_individus.getSelectedRow();
        
        if (iLigneEvenement != -1) {
            this.iIndividuSelection = (int) objTableauClesIndividus[iLigneEvenement];
            M_Individu unIndividu =  lhmLesIndividus.get(iIndividuSelection);

            ed_nom.setText(unIndividu.getNom());
            ed_prenom.setText(unIndividu.getPrenom());

            // Sélectionner le genre correspondant dans la ComboBox
            
            String strCodeGenre = unIndividu.getCodeGenre();
            M_Genre unGenre = lhmLesGenres.get(strCodeGenre);
            if (unGenre != null) {
                co_genre.setSelectedItem(unGenre.getLibelle());
            }
            
            // Sélectionner la mère correspondant dans la ComboBox
            
            if(lhmLesRelationsMere != null && lhmLesRelationsMere.get(iIndividuSelection) != null){
                M_Relation_Mere unRelationMere = lhmLesRelationsMere.get(iIndividuSelection);
                M_Individu uneMere = lhmLesIndividus.get(unRelationMere.getIdMere());
                
                if (uneMere != null) {
                    co_mere.setSelectedItem(uneMere.getPrenom() + " " + uneMere.getNom());
                }
            }
            else{
                co_mere.setSelectedItem("Aucun");
            }

            // Sélectionner le père correspondant dans la combo box
            
            if(lhmLesRelationsPere != null && lhmLesRelationsPere.get(iIndividuSelection) != null){
                M_Relation_Pere uneRelationPere = lhmLesRelationsPere.get(iIndividuSelection);
                M_Individu unPere = lhmLesIndividus.get(uneRelationPere.getIdPere());
                
                if (unPere != null) {
                    co_pere.setSelectedItem(unPere.getPrenom() + " " + unPere.getNom());
                }
            }
            else{
                co_pere.setSelectedItem("Aucun");
            }

            try {
                this.lhmLesEvenements = leControl.getEvenements(unIndividu);

                objTableauClesEvenements = lhmLesEvenements.keySet().toArray();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                
                dm_tb_evenements.setRowCount(0);
                
                for (Object objCle : objTableauClesEvenements) {
                    M_Evenement unEvenement = lhmLesEvenements.get(objCle);
                    int iIdTypeEvenement = unEvenement.getIdTypeEvenement();
                    M_Type_Evenement unTypeEvenement =  lhmLesTypesEvenement.get(iIdTypeEvenement);
                    String strLibelle = (unTypeEvenement != null) ? unTypeEvenement.getLibelle() : "Inconnu";
                    LocalDate dDate = unEvenement.getDate();
                    String strDate = (dDate != null) ? dDate.format(formatter) : "N/A";
                    String strLieu = (unEvenement.getLieu() != null) ? unEvenement.getLieu() : "Inconnu";
                    
                    dm_tb_evenements.addRow(new Object[]{strLibelle, strDate, strLieu});
                }
                
                iEvenementSelection = null;

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement des événements : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_tb_individusMouseClicked

    private void co_typesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_co_typesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_co_typesActionPerformed

    private void bt_enregistrer_evenementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_enregistrer_evenementActionPerformed
        try {
            String strTypeLibelle = (String) co_types.getSelectedItem();
            String strDateStr = ed_date.getText().trim();
            String strLieu = ed_lieu.getText().trim();

            if (strTypeLibelle == null || strTypeLibelle.equals("Sélectionnez un évènement") ||
                strDateStr.isEmpty() || strLieu.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous "
                        + "les champs d’événement.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dDate = null;
            
            try {
                dDate = LocalDate.parse(strDateStr, formatter);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Format de date invalide : "
                        + "respecter le format jj/mm/aaaa", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int iIdType = -1;
            for (Integer iCle : lhmLesTypesEvenement.keySet()) {
                if (lhmLesTypesEvenement.get(iCle).getLibelle().equals(strTypeLibelle)) {
                    iIdType = iCle;
                    break;
                }
            }

            if (iIdType == -1) {
                JOptionPane.showMessageDialog(this, "Type d’événement inconnu.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (iIndividuSelection == null) {
                JOptionPane.showMessageDialog(this, "Aucun individu sélectionné.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (iEvenementSelection == null && bIsModificationEvenement == true) {
                JOptionPane.showMessageDialog(this, "Aucun évènement sélectionné.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (bIsModificationEvenement) {
                leControl.update_evenement(iEvenementSelection, iIdType, strLieu, dDate);
                JOptionPane.showMessageDialog(this, "Événement modifié avec succès.",
                        "Mise à jour",JOptionPane.INFORMATION_MESSAGE);
            } else {
                leControl.insert_evenement(iIndividuSelection, iIdType, strLieu, dDate);
                JOptionPane.showMessageDialog(this, "Événement ajouté avec succès."
                ,"Mise à jour",JOptionPane.INFORMATION_MESSAGE);
            }

            bIsModificationEvenement = false;
            ed_date.setText("");
            ed_lieu.setText("");
            co_types.setSelectedIndex(0);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            Logger.getLogger(V_Individus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_enregistrer_evenementActionPerformed

    private void bt_ajout_evenementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ajout_evenementActionPerformed
        if(iIndividuSelection != null){
            bIsModificationEvenement = false;
            ed_date.setText("");
            ed_lieu.setText("");
            co_types.setSelectedIndex(0);
            modeSaisieEvenement(true);
        }
        else{
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un "
            + "individu.","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_ajout_evenementActionPerformed

    private void bt_modifier_evenementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_modifier_evenementActionPerformed
        if (iEvenementSelection != null) {
            bIsModificationEvenement = true;
            modeSaisieEvenement(true);
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un événement à modifier."
            ,"Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_modifier_evenementActionPerformed

    private void bt_annuler_evenementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_annuler_evenementActionPerformed
        modeSaisieEvenement(false);
        bIsModificationEvenement = false;
    }//GEN-LAST:event_bt_annuler_evenementActionPerformed

    private void bt_supprimer_evenementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_supprimer_evenementActionPerformed
        if (iEvenementSelection != null) {
            M_Evenement unEvenement = lhmLesEvenements.get(iEvenementSelection);

            int iConfirm = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer cet évènement ?",
                "Confirmation",
                JOptionPane.WARNING_MESSAGE
            );

            if (iConfirm == JOptionPane.YES_OPTION) {
                try {
                    leControl.delete_evenement(iEvenementSelection);

                    lhmLesEvenements.remove(iEvenementSelection);

                    objTableauClesEvenements = lhmLesEvenements.keySet().toArray();

                    ed_date.setText("");
                    ed_lieu.setText("");
                    co_types.setSelectedIndex(0);

                    JOptionPane.showMessageDialog(this, "Evènement supprimé avec succès.",
                            "Mise à jour", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Erreur lors de la suppression : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un "
                    + "évènement à supprimer.","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_supprimer_evenementActionPerformed

    private void co_mereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_co_mereActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_co_mereActionPerformed

    private void bt_retourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_retourActionPerformed
        try {
            this.dispose();
            leControl.aff_V_Arbre(unArbre, strCodeAcces);
        } catch (SQLException ex) {
            Logger.getLogger(V_Individus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_retourActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(V_Individus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(V_Individus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(V_Individus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(V_Individus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                V_Individus dialog = new V_Individus(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_ajout;
    private javax.swing.JButton bt_ajout_evenement;
    private javax.swing.JButton bt_annuler;
    private javax.swing.JButton bt_annuler_evenement;
    private javax.swing.JButton bt_enregistrer;
    private javax.swing.JButton bt_enregistrer_evenement;
    private javax.swing.JButton bt_modifier;
    private javax.swing.JButton bt_modifier_evenement;
    private javax.swing.JButton bt_retour;
    private javax.swing.JButton bt_supprimer;
    private javax.swing.JButton bt_supprimer_evenement;
    private javax.swing.JComboBox<String> co_genre;
    private javax.swing.JComboBox<String> co_mere;
    private javax.swing.JComboBox<String> co_pere;
    private javax.swing.JComboBox<String> co_types;
    private javax.swing.JFormattedTextField ed_date;
    private javax.swing.JTextField ed_lieu;
    private javax.swing.JTextField ed_nom;
    private javax.swing.JTextField ed_prenom;
    private javax.swing.JLabel lb_date;
    private javax.swing.JLabel lb_evenement;
    private javax.swing.JLabel lb_genre;
    private javax.swing.JLabel lb_lieu;
    private javax.swing.JLabel lb_mere;
    private javax.swing.JLabel lb_nom;
    private javax.swing.JLabel lb_pere;
    private javax.swing.JLabel lb_prenom;
    private javax.swing.JPanel pn_am;
    private javax.swing.JPanel pn_am_evenement;
    private javax.swing.JPanel pn_cms;
    private javax.swing.JPanel pn_cms_evenement;
    private javax.swing.JScrollPane sc_evenements;
    private javax.swing.JScrollPane sc_util;
    private javax.swing.JTable tb_evenements;
    private javax.swing.JTable tb_individus;
    // End of variables declaration//GEN-END:variables
}
