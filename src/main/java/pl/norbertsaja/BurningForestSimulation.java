package pl.norbertsaja;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class BurningForestSimulation {
    private String[][] map;
    private final int size;
    private final double forestation;

    public BurningForestSimulation(int size, double forestation){
        this.size = size;
        this.forestation=forestation;
    }
    public void mapInitialization(){
        map = new String[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(Math.random() <= forestation){
                    map[i][j] = "T"; // Drzewo
                }
                else{
                    map[i][j] = "X"; // Brak drzewa
                }
            }
        }
        System.out.println("Utworzona siatka:");
        printMap();
    }
    public void fireInitialization(){
        for(int i = 0; i < size; i++){
            if(map[0][i].equals("T")){
                map[0][i] = "B"; //drzewo spalone
            }
        }
        boolean fireSpread = true;
        while (fireSpread){
            fireSpread = false;
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(map[i][j].equals("B")){
                        for (int k = Math.max(0, i - 1); k <= Math.min(i + 1, map.length - 1); k++){
                            for (int l = Math.max(0, j - 1); l <= Math.min(j + 1, map[i].length - 1); l++){
                                if (map[k][l].equals("T") && Math.sqrt((i - k) * (i - k) + (j - l) * (j - l)) < 2) {
                                    map[k][l] = "B";
                                    fireSpread = true;
                                }
                            }
                            //printMap();
                        }
                    }
                }
            }
        }
        System.out.println("Podpalona siatka:");
        printMap(); //jesli chcemy na biezaco podgladac symulacje wystarczy wywolac te metode wewnatrz petli
    }
    public void makeSimulation(){
        int t = 0, b = 0; //Drzewa zwykle i spalone
        double tb;
        double[] tab = new double[10]; //na sztywno 10 bo bedzie 10 rekordow w petli
        for(int l = 0; l < 10; l++){
            mapInitialization();
            fireInitialization();
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(map[i][j].equals("T")){
                        t++;
                    }else if(map[i][j].equals("B")){
                        b++;
                    }
                }
            }
            tb = t+b;
            tab[l] = (t/tb); //tablica zawiera stosunek drzew spalonych do wszystkich
            System.out.println("Stosunek liczby spalonych drzew do wszystkich drzew wynosi: " + tab[l] +" (" + t + "/" + tb + ")." );
            t=0;
            b=0;
        }
        //zapisujemy srednia wartosc
        double avg = Arrays.stream(tab).average().orElse(Double.NaN); //najpierw steamujemy wartosci potem wyliczamy srednia a orElse zwraca nam wartosc jesli tablica jest pusta
        //zapisujemy dane do pliku
        String nazwa = ("dane-" + forestation +".txt"); //np dane-0.5.txt
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(nazwa));
            for(double d: tab){
                writer.write(Double.toString(d));
                writer.newLine();
            }
            writer.write("Srednia wartosc: ");
            writer.write(Double.toString(avg));
            writer.newLine();

            writer.close();
            System.out.println("Dane zostaly zapisane do pliku " + nazwa);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void printMap(){ //wyswietla mape pozaru
        System.out.println();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}