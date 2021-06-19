package ensi.model;

import java.util.ArrayList;
import java.util.Random;

public class CoordoneesPionts {

    public double decalageX;
    public double decalageY;

    public CoordoneesPionts(double x, double y){
        decalageX = x;
        decalageY = y;
    }

    /**
     * Return list of positions for seeds
     * @return list of positions
     */
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

    /**
     * Return random list of positions for seeds
     * @return random list of positions
     */
    public static ArrayList<CoordoneesPionts> randomCoordonnees(int joueur, int cell){
        long seed = (6L *joueur + cell);
        Random generator = new Random(seed);

        ArrayList<CoordoneesPionts> listeCoor = listeCoordonnees();
        ArrayList<CoordoneesPionts> randList = new ArrayList<>();

        while (listeCoor.size() > 0){

            int index = generator.nextInt(listeCoor.size());

            randList.add(listeCoor.get(index));
            listeCoor.remove(index);

        }

        return randList;
    }


}
