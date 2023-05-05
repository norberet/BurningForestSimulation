package pl.norbertsaja;

public class Main {
    public static void main(String[] args) {
        double[] forestation = new double[]{0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1};

        BurningForestSimulation[] symulacja = new BurningForestSimulation[forestation.length]; //tablica obiektow
        for (int i = 0; i < symulacja.length; i++) {
            symulacja[i] = new BurningForestSimulation(20, forestation[i]); //do konstruktora przekazujemy wartosci z tablicy oraz rozmiar 100
            symulacja[i].makeSimulation();
        }
    }
}