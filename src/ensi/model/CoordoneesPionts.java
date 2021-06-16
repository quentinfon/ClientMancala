package ensi.model;

import java.util.ArrayList;

public class CoordoneesPionts {

    public double decalageX;
    public double decalageY;

    public CoordoneesPionts(double x, double y){
        decalageX = x;
        decalageY = y;
    }

    public static ArrayList<CoordoneesPionts> listeCoordonnees(){

        ArrayList<CoordoneesPionts> liste = new ArrayList<>();

        liste.add(new CoordoneesPionts(-20, 0));
        liste.add(new CoordoneesPionts(0, 0));
        liste.add(new CoordoneesPionts(20, 0));
        liste.add(new CoordoneesPionts(-40, 20));
        liste.add(new CoordoneesPionts(-20, 20));
        liste.add(new CoordoneesPionts(0, 20));
        liste.add(new CoordoneesPionts(20, 20));
        liste.add(new CoordoneesPionts(40, 20));
        liste.add(new CoordoneesPionts(-40, 40));
        liste.add(new CoordoneesPionts(-20, 40));
        liste.add(new CoordoneesPionts(0, 40));
        liste.add(new CoordoneesPionts(20, 40));
        liste.add(new CoordoneesPionts(40, 40));
        liste.add(new CoordoneesPionts(-40, 60));
        liste.add(new CoordoneesPionts(-20, 60));
        liste.add(new CoordoneesPionts(-0, 60));
        liste.add(new CoordoneesPionts(20, 60));
        liste.add(new CoordoneesPionts(40, 60));
        liste.add(new CoordoneesPionts(-20, 80));
        liste.add(new CoordoneesPionts(0, 80));
        liste.add(new CoordoneesPionts(20, 80));


        return liste;
    }


}
