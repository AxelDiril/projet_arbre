package pj_arbre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class M_Avoir_Evenement {
    private Db_mariadb db;
    private int iIdIndividu;
    private int iIdEvenement;
    
    public M_Avoir_Evenement(int iIdIndividu, int iIdEvenement){
        this.iIdIndividu = iIdIndividu;
        this.iIdEvenement = iIdEvenement;
    }

    public M_Avoir_Evenement(Db_mariadb db, int iIdIndividu, int iIdEvenement) 
            throws SQLException {
            String strSql = "INSERT INTO AVOIR_EVENEMENT (id_individu, id_evenement) VALUES (" 
                    + iIdIndividu + ", " + iIdEvenement + ")";
            
            db.sqlExec(strSql);
    }

    public int getIdIndividu() {
        return iIdIndividu;
    }

    public int getIdEvenement() {
        return iIdEvenement;
    }

    public void delete() throws SQLException {
        String strSql = "DELETE FROM AVOIR_EVENEMENT WHERE id_individu = " + 
                iIdIndividu + " AND id_evenement = " + iIdEvenement;
        
        db.sqlExec(strSql);
    }
    
    public static LinkedHashMap<Integer, ArrayList<M_Avoir_Evenement>> 
        getEvenementsPourArbre(Db_mariadb db, int iIdArbre) throws SQLException {
        LinkedHashMap<Integer, ArrayList<M_Avoir_Evenement>> lhmLesEvenementsArbre 
                = new LinkedHashMap<>();

        String strSql = "SELECT ae.id_individu, ae.id_evenement " +
                     "FROM AVOIR_EVENEMENT ae " +
                     "JOIN INDIVIDUS i ON ae.id_individu = i.id_individu " +
                     "WHERE i.id_arbre = " + iIdArbre;

        ResultSet res = db.sqlSelect(strSql);

        while (res.next()) {
            int iIdIndividu = res.getInt("id_individu");
            int iIdEvenement = res.getInt("id_evenement");
            M_Avoir_Evenement unEvenementArbre = new M_Avoir_Evenement(iIdIndividu, iIdEvenement);

            ArrayList<M_Avoir_Evenement> arrLesEvenementsArbre = lhmLesEvenementsArbre.get(iIdIndividu);
            
            if (arrLesEvenementsArbre == null) {
                arrLesEvenementsArbre = new ArrayList<>();
                lhmLesEvenementsArbre.put(iIdIndividu, arrLesEvenementsArbre);
            }
            
            arrLesEvenementsArbre.add(unEvenementArbre);
        }
        return lhmLesEvenementsArbre;
    }

    @Override
    public String toString() {
        return "M_Avoir_Evenement{" +
                "idIndividu=" + iIdIndividu +
                ", idEvenement=" + iIdEvenement +
                '}';
    }
}
