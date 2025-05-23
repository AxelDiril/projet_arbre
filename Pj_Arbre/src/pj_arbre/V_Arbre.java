/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pj_arbre;

/**
 *
 * @author axel
 */
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel; 
import javax.swing.BorderFactory; 
import javax.swing.Box;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.time.format.DateTimeFormatter;


public class V_Arbre extends javax.swing.JFrame {

    /**
     * Creates new form V_Inscription
     */
    private C_Arbre leControl;
    private M_Arbre unArbre;
    private LinkedHashMap<Integer, M_Individu> lhmLesIndividus;
    private LinkedHashMap<Integer, M_Relation_Mere> lhmLesRelationsMere;
    private LinkedHashMap<Integer, M_Relation_Pere> lhmLesRelationsPere;
    private LinkedHashMap<Integer, M_Evenement> lhmLesEvenements;
    private LinkedHashMap<Integer, ArrayList<M_Avoir_Evenement>> lhmLesEvenementsArbre;
    
    public V_Arbre(java.awt.Frame parent, boolean modal, C_Arbre leControl) {
        this.leControl = leControl;
        initComponents();
    }
    
    public void afficher(M_Arbre unArbre, LinkedHashMap<Integer, M_Individu> 
            lhmLesIndividus, LinkedHashMap<Integer, M_Relation_Mere> 
            lhmLesRelationsMere, LinkedHashMap<Integer, M_Relation_Pere>
            lhmLesRelationsPere, LinkedHashMap<Integer, M_Evenement>
            lhmLesEvenements, LinkedHashMap<Integer,ArrayList<M_Avoir_Evenement>>
                    lhmLesEvenementsArbre){
        this.unArbre = unArbre;
        this.lhmLesIndividus = lhmLesIndividus;
        this.lhmLesRelationsMere = lhmLesRelationsMere;
        this.lhmLesRelationsPere = lhmLesRelationsPere;
        this.lhmLesEvenements = lhmLesEvenements;
        this.lhmLesEvenementsArbre = lhmLesEvenementsArbre;
        
        this.lb_arbre.setText(unArbre.getNom());
        this.setTitle("Arbre : " + unArbre.getNom());
        
        
        // Paramètrage layouts
        
        Dimension fixedSize = pn_arbre.getPreferredSize();
        pn_arbre.setMinimumSize(fixedSize);
        pn_arbre.setMaximumSize(fixedSize);
        pn_arbre.setPreferredSize(fixedSize);
        
        fixedSize = pn_parents.getPreferredSize();
        pn_parents.setMinimumSize(fixedSize);
        pn_parents.setMaximumSize(fixedSize);
        pn_parents.setPreferredSize(fixedSize);
        pn_parents.setLayout(new BoxLayout(pn_parents, BoxLayout.X_AXIS));
        
        
        fixedSize = pn_individu.getPreferredSize();
        pn_individu.setMinimumSize(fixedSize);
        pn_individu.setMaximumSize(fixedSize);
        pn_individu.setPreferredSize(fixedSize);
        pn_individu.setLayout(new BoxLayout(pn_individu, BoxLayout.X_AXIS));
        
        fixedSize = pn_enfants.getPreferredSize();
        pn_enfants.setMinimumSize(fixedSize);
        pn_enfants.setMaximumSize(fixedSize);
        pn_enfants.setPreferredSize(fixedSize);
        pn_enfants.setLayout(new BoxLayout(pn_enfants, BoxLayout.X_AXIS));
        
        co_individus.removeAllItems();
        for (M_Individu unIndividu : lhmLesIndividus.values()) {
            co_individus.addItem(unIndividu.getNom() + " " + unIndividu.getPrenom());
        }
        
        affichageArbre();
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void affichageArbre(){
        
        // Vider les panels
        
        pn_parents.removeAll();
        pn_individu.removeAll();
        pn_enfants.removeAll();
        
        // Récupérer l'id de l'individu
        
        if(co_individus.getItemCount() != 0){
            String strSelection = co_individus.getSelectedItem().toString();
            M_Individu unIndividuSelectionne = null;

            for (M_Individu unIndividu : lhmLesIndividus.values()) {
                String strNomPrenom = unIndividu.getNom() + " " + unIndividu.getPrenom();
                
                if (strNomPrenom.equals(strSelection)) {
                    unIndividuSelectionne = unIndividu;
                    break;
                }
            }

            if(unIndividuSelectionne != null){
                int iIdIndividu = unIndividuSelectionne.getIdIndividu();

                // Afficher les parents
                
                pn_parents.add(Box.createHorizontalGlue());

                // Mère

                M_Relation_Mere uneRelationMere = lhmLesRelationsMere.get(iIdIndividu);

                if (uneRelationMere != null) {
                    int iIdMere = uneRelationMere.getIdMere();
                    M_Individu unMere = lhmLesIndividus.get(iIdMere);
                    JPanel panelMere = createIndividuPanel(unMere);
                    panelMere.setAlignmentY(Component.CENTER_ALIGNMENT);
                    
                    pn_parents.add(panelMere);
                    pn_parents.add(Box.createRigidArea(new Dimension(20, 0)));
                } else {
                    System.out.println("Aucune relation mère trouvée pour cet individu.");
                }

                // Père

                M_Relation_Pere uneRelationPere = lhmLesRelationsPere.get(iIdIndividu);

                if (uneRelationPere != null) {
                    int iIdPere = uneRelationPere.getIdPere();
                    M_Individu unPere = lhmLesIndividus.get(iIdPere);
                    JPanel panelPere = createIndividuPanel(unPere);
                    panelPere.setAlignmentY(Component.CENTER_ALIGNMENT);
                    
                    pn_parents.add(panelPere);
                    pn_parents.add(Box.createRigidArea(new Dimension(20, 0)));
                } else {
                    System.out.println("Aucune relation père trouvée pour cet individu.");
                }

                // Afficher l'individu
                
                pn_individu.add(Box.createHorizontalGlue());

                JPanel panelIndividu = createIndividuPanel(unIndividuSelectionne);
                panelIndividu.setAlignmentY(Component.CENTER_ALIGNMENT);
                
                pn_individu.add(panelIndividu);
                pn_individu.add(Box.createRigidArea(new Dimension(20, 0)));

                // Afficher enfants
                
                pn_enfants.add(Box.createHorizontalGlue());

                // Faire une collection d'enfants
                
                ArrayList<M_Individu> arrLesEnfants = new ArrayList<>();

                // Relations mère
                
                for (M_Relation_Mere uneRelation : lhmLesRelationsMere.values()) {
                    
                    if (uneRelation.getIdMere() == iIdIndividu) {
                        M_Individu unEnfant = lhmLesIndividus.get(uneRelation.getIdIndividu());
                        
                        if (unEnfant != null) {
                            arrLesEnfants.add(unEnfant);
                        }
                    }
                }

                // Relations père
                
                for (M_Relation_Pere uneRelation : lhmLesRelationsPere.values()) {
                    
                    if (uneRelation.getIdPere() == iIdIndividu) {
                        M_Individu unEnfant = lhmLesIndividus.get(uneRelation.getIdIndividu());
                        
                        if (unEnfant != null) {
                            arrLesEnfants.add(unEnfant);
                        }
                    }
                }

                for (M_Individu unEnfant : arrLesEnfants) {
                    JPanel panelEnfant = createIndividuPanel(unEnfant);
                    panelEnfant.setAlignmentY(Component.CENTER_ALIGNMENT);
                    
                    pn_enfants.add(panelEnfant);
                    pn_enfants.add(Box.createRigidArea(new Dimension(20, 0)));
                }
            }
        }
        
        pn_parents.add(Box.createHorizontalGlue());
        pn_individu.add(Box.createHorizontalGlue());
        pn_enfants.add(Box.createHorizontalGlue());


        // Actualiser les panels
            
        pn_parents.revalidate();
        pn_individu.revalidate();
        pn_enfants.revalidate();
        
        pn_parents.repaint();
        pn_individu.repaint();
        pn_enfants.repaint();
    }
    
        public String getDateEvenement(int idIndividu, int idTypeEvenement) {
            String strDateEvenement =  "???";
            ArrayList<M_Avoir_Evenement> arrLesEvenements = lhmLesEvenementsArbre.get(idIndividu);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (arrLesEvenements != null){
                for (M_Avoir_Evenement unEvenementArbre : arrLesEvenements) {
                    M_Evenement unEvenement = lhmLesEvenements.get(unEvenementArbre.getIdEvenement());
                    if (unEvenement != null && unEvenement.getIdTypeEvenement() == idTypeEvenement) {
                        strDateEvenement  = unEvenement.getDate().format(formatter);
                    }
                }
            }

            return strDateEvenement ;
        }

    public JPanel createIndividuPanel(M_Individu unIndividu) {
        
        // Panel de l'individu
        
        JPanel panelIndividu = new JPanel();
        panelIndividu.setLayout(new BoxLayout(panelIndividu, BoxLayout.Y_AXIS));
        panelIndividu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelIndividu.setBackground(Color.YELLOW);
        panelIndividu.setPreferredSize(new Dimension(200, 60));

        // Label nom
        
        JLabel lbNom = new JLabel(unIndividu.getPrenom() + " " + unIndividu.getNom());
        lbNom.setFont(new Font("Arial", Font.BOLD, 18));
        lbNom.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Label dates
        
        JLabel lbDates = new JLabel(getDateEvenement(unIndividu.getIdIndividu(),1) +
                " - " + getDateEvenement(unIndividu.getIdIndividu(),2));
        lbDates.setFont(new Font("Arial", Font.PLAIN, 16));
        lbDates.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelIndividu.add(lbNom);
        panelIndividu.add(lbDates);

        return panelIndividu;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_arbre = new javax.swing.JLabel();
        pn_arbre = new javax.swing.JPanel();
        pn_parents = new javax.swing.JPanel();
        pn_individu = new javax.swing.JPanel();
        pn_enfants = new javax.swing.JPanel();
        lb_individu_selectionne = new javax.swing.JLabel();
        lb_parents = new javax.swing.JLabel();
        lb_enfants = new javax.swing.JLabel();
        lb_individu = new javax.swing.JLabel();
        co_individus = new javax.swing.JComboBox<>();
        bt_individus = new javax.swing.JButton();
        bt_collaborateurs = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nom arbre");
        setResizable(false);

        lb_arbre.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lb_arbre.setText("Nom Arbre");

        pn_arbre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(0, 0, 0), new java.awt.Color(102, 102, 102)));

        pn_parents.setBackground(new java.awt.Color(153, 255, 255));

        javax.swing.GroupLayout pn_parentsLayout = new javax.swing.GroupLayout(pn_parents);
        pn_parents.setLayout(pn_parentsLayout);
        pn_parentsLayout.setHorizontalGroup(
            pn_parentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pn_parentsLayout.setVerticalGroup(
            pn_parentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
        );

        pn_individu.setBackground(new java.awt.Color(255, 204, 255));
        pn_individu.setPreferredSize(new java.awt.Dimension(0, 123));

        javax.swing.GroupLayout pn_individuLayout = new javax.swing.GroupLayout(pn_individu);
        pn_individu.setLayout(pn_individuLayout);
        pn_individuLayout.setHorizontalGroup(
            pn_individuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );
        pn_individuLayout.setVerticalGroup(
            pn_individuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
        );

        pn_enfants.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout pn_enfantsLayout = new javax.swing.GroupLayout(pn_enfants);
        pn_enfants.setLayout(pn_enfantsLayout);
        pn_enfantsLayout.setHorizontalGroup(
            pn_enfantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pn_enfantsLayout.setVerticalGroup(
            pn_enfantsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
        );

        lb_individu_selectionne.setFont(new java.awt.Font("sansserif", 1, 19)); // NOI18N
        lb_individu_selectionne.setText("Individu");

        lb_parents.setFont(new java.awt.Font("sansserif", 1, 19)); // NOI18N
        lb_parents.setText("Parents");

        lb_enfants.setFont(new java.awt.Font("sansserif", 1, 19)); // NOI18N
        lb_enfants.setText("Enfants");

        javax.swing.GroupLayout pn_arbreLayout = new javax.swing.GroupLayout(pn_arbre);
        pn_arbre.setLayout(pn_arbreLayout);
        pn_arbreLayout.setHorizontalGroup(
            pn_arbreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_arbreLayout.createSequentialGroup()
                .addGroup(pn_arbreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pn_arbreLayout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(lb_individu_selectionne))
                    .addGroup(pn_arbreLayout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(lb_parents))
                    .addGroup(pn_arbreLayout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(lb_enfants))
                    .addGroup(pn_arbreLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pn_arbreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pn_individu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                            .addComponent(pn_parents, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pn_arbreLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pn_enfants, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_arbreLayout.setVerticalGroup(
            pn_arbreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_arbreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_parents)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_parents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_individu_selectionne)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_individu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lb_enfants)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_enfants, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lb_individu.setText("Voir individu :");

        co_individus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                co_individusActionPerformed(evt);
            }
        });

        bt_individus.setText("Gestion des individus");
        bt_individus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_individusActionPerformed(evt);
            }
        });

        bt_collaborateurs.setText("Collaborateurs");
        bt_collaborateurs.setActionCommand("");
        bt_collaborateurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_collaborateursActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_arbre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(co_individus, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_individus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_collaborateurs, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lb_individu)
                        .addGap(94, 94, 94))))
            .addGroup(layout.createSequentialGroup()
                .addGap(400, 400, 400)
                .addComponent(lb_arbre)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_arbre)
                .addGap(27, 27, 27)
                .addComponent(pn_arbre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_individu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(co_individus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(bt_individus)
                .addGap(26, 26, 26)
                .addComponent(bt_collaborateurs)
                .addGap(266, 266, 266))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void co_individusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_co_individusActionPerformed
        affichageArbre();
    }//GEN-LAST:event_co_individusActionPerformed

    private void bt_individusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_individusActionPerformed
        try {
            this.dispose();
            leControl.aff_V_Individus(unArbre);
        } catch (SQLException ex) {
            Logger.getLogger(V_Arbre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_individusActionPerformed

    private void bt_collaborateursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_collaborateursActionPerformed
        try {
            this.dispose();
            leControl.aff_V_Collaborateurs(unArbre);
        } catch (SQLException ex) {
            Logger.getLogger(V_Arbre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_collaborateursActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            V_Arbre dialog = new V_Arbre(new javax.swing.JFrame(), true, null);  // Même contrôleur null ici
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
    private javax.swing.JButton bt_collaborateurs;
    private javax.swing.JButton bt_individus;
    private javax.swing.JComboBox<String> co_individus;
    private javax.swing.JLabel lb_arbre;
    private javax.swing.JLabel lb_enfants;
    private javax.swing.JLabel lb_individu;
    private javax.swing.JLabel lb_individu_selectionne;
    private javax.swing.JLabel lb_parents;
    private javax.swing.JPanel pn_arbre;
    private javax.swing.JPanel pn_enfants;
    private javax.swing.JPanel pn_individu;
    private javax.swing.JPanel pn_parents;
    // End of variables declaration//GEN-END:variables
}
