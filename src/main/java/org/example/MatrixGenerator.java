package org.example;

import org.example.chart.SortResultSpiderChart;
import org.example.inout.StdIn;
import org.example.inout.StdOut;
import org.example.interfaces.DataGenerator;
import org.example.interfaces.Sorter;
import org.example.mergeSort.MergeSort;

import java.util.*;

public class MatrixGenerator {
    Map<String, SortResult> resultsMap;
    ResultSaver saver;

    // matrix Parameters
    List<Double> means = new ArrayList<>();
    List<Double> variances = new ArrayList<>();
    List<Integer> dataSizes = new ArrayList<>();

    //Initialization of mean array
    public void meanInstanciation(){
        StdOut.printf("+ add mean : ");
        while (!StdIn.isEmpty()){
            String text = StdIn.readString();
            if(text.equals("-")){
                break;
            }
            means.add(Double.parseDouble(text));
            System.out.println(means);
        }
    }

    //Initialization of mean array
    public void variancesInstanciation(){
        StdOut.print("+ add variance : ");
        while (!StdIn.isEmpty() ){
            String text = StdIn.readString();
            if(text.equals("-")){
                break;
            }
            variances.add(Double.parseDouble(text));
            System.out.println(variances);
        }
    }

    //Initialization of mean array
    public void dataSizesInstanciation(){
        StdOut.print("+ add dataSizes : ");
        while (!StdIn.isEmpty()){
            String text = StdIn.readString();
            if(text.equals("-")){
                break;
            }
            dataSizes.add(Integer.parseInt(text));
            System.out.println(dataSizes);
        }
    }

    // warmup and numberOfExperiments
    int warmUpRounds = 100;
    int numberOfExperiments = 10;

    // data generation and algorithm selected
    DataGenerator generator;
    Sorter sorter;

    public MatrixGenerator(){
        generator = new NormalDistributionGenerator();

        resultsMap = new HashMap<>();
        saver = new ResultSaver("ResultFile");
    }


    public void generateOrganizedRandomMatrices(){

        // Warm-up JVM
        Benchmark benchmark = new Benchmark(sorter);

        // JVM Warm-up
        benchmark.warmUpJVM(warmUpRounds, 1000);

        // Execute benchmarks
        for (int dataSize : dataSizes) {
            for (double mean : means) {
                for (double variance : variances) {
                    Double[] data = generator.generateData(dataSize, mean, variance);
                    String key = "Size:" + data.length + " Mean:" + mean + " Variance:" + variance;
                    resultsMap.put(key,benchmark.benchmark(data, numberOfExperiments,warmUpRounds,dataSize, mean, variance));

                    //System.out.println("Data Size: " + dataSize + ", Mean: " + mean + ", Variance: " + variance + ", Avg Time: " + averageTime + " ns");
                }
            }
        }
    }

    public void show(){

        for (String key : resultsMap.keySet()) {
            SortResult result = resultsMap.get(key);
            System.out.println("Key: " + key + ", Value: " + result);
        }
    }


    public void saveDataSet(){
        for (Map.Entry<String, SortResult> entry : resultsMap.entrySet()) {
            saver.saveResult(entry.getKey(), entry.getValue());
        }

    }

    public void visualizationAndAnalysis(){
        SortResultSpiderChart demo = new SortResultSpiderChart("Sort Algorithm Performance", resultsMap);
        demo.pack();
        demo.setVisible(true);
    }

    public void selectSortingAlgorithm() {
        StdOut.println("\n=== Choisissez l'algorithme de tri ===");
        StdOut.println("1. Merge Sort");
        StdOut.println("2. Quick Sort"); // Supposons que vous ayez une classe QuickSort
        // Ajoutez d'autres algorithmes de tri ici
        StdOut.print("Sélectionnez une option : ");

        int choice = StdIn.readInt();
        switch (choice) {
            case 1:
                sorter = new MergeSort();
                StdOut.println("Merge Sort sélectionné.");
                break;
            case 2:
                sorter = new MergeSort(); // Assurez-vous d'avoir une classe QuickSort implémentant Sorter
                StdOut.println("Quick Sort sélectionné.");
                break;
            // Ajoutez des cas supplémentaires pour d'autres algorithmes de tri
            default:
                StdOut.println("Option invalide, Merge Sort sélectionné par défaut.");
                sorter = new MergeSort();
        }
    }




    public static void main(String[] args) {
        MatrixGenerator matrixGenerator = new MatrixGenerator();

        boolean exit = false;
        while (!exit) {
            StdOut.println("\n=== Menu ===");
            StdOut.println("1. Choisir l'algorithme de tri");
            StdOut.println("2. Initialiser les moyennes");
            StdOut.println("3. Initialiser les variances");
            StdOut.println("4. Initialiser les tailles de données");
            StdOut.println("5. Générer des matrices aléatoires organisées");
            StdOut.println("6. Afficher les résultats");
            StdOut.println("7. Visualiser et analyser");
            StdOut.println("0. Quitter");
            StdOut.print("Sélectionnez une option : ");

            int choice = StdIn.readInt();
            switch (choice) {
                case 1:
                    matrixGenerator.selectSortingAlgorithm();
                    break;
                case 2:
                    matrixGenerator.meanInstanciation();
                    break;
                case 3:
                    matrixGenerator.variancesInstanciation();
                    break;
                case 4:
                    matrixGenerator.dataSizesInstanciation();
                    break;
                case 5:
                    matrixGenerator.generateOrganizedRandomMatrices();
                    break;
                case 6:
                    matrixGenerator.show();
                    break;
                case 7:
                    matrixGenerator.visualizationAndAnalysis();
                    break;
                case 0:
                    exit = true;
                    StdOut.println("Au revoir!");
                    break;
                default:
                    StdOut.println("Option invalide, veuillez réessayer.");
            }
        }
    }

}
