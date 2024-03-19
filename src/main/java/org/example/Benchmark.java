package org.example;

import org.example.interfaces.Sorter;
import org.example.mergeSort.MergeSort;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Benchmark {
    private final Sorter sorter;

    public Benchmark(Sorter sorter) {
        this.sorter = sorter;
    }

    public void warmUpJVM(int rounds, int dataSize) {

        Double[] warmUpData = new Double[dataSize];
        Random random = new Random();

        for (int i = 0; i < dataSize; i++) {
            warmUpData[i] = random.nextDouble() * dataSize;
        }

        for (int i = 0; i < rounds; i++) {
            Double[] copy = Arrays.copyOf(warmUpData, warmUpData.length);
            sorter.sort(copy);
        }

        System.out.println("JVM Warm-up completed.");
    }


    //double mean, double variance,
    public SortResult benchmark(Double[] data, int numberOfExperiments,int warmUpRounds,double dataSize,double mean,double variance) {
        long totalTime = 0;
        long complixity = 0;
        int experiment;

        double theoreticalComplexity =(data.length*(Math.log(data.length)/Math.log(2)));

        System.out.println(" _____________________________ Start ________________________________ ");
        System.out.println(" Data Size() : "+data.length);
        System.out.println(" Data  : "+ Arrays.toString(data));

        for (experiment = 0; experiment < numberOfExperiments; experiment++) {
            Double[] dataCopy = Arrays.copyOf(data, data.length);

            long startTime = System.nanoTime();
            complixity = sorter.sort(dataCopy);
            long endTime = System.nanoTime();

            totalTime += (endTime - startTime);

        }




        double averageTimeInNanoSeconds = (double) totalTime / numberOfExperiments;
        System.out.println(" Time taken to sort: " +   averageTimeInNanoSeconds );

        System.out.println(" complixity : ");

        //System.out.println(" theoretical Time  Complexity : "+theoreticalComplexity);

       //System.out.println(" Réel Time  Complexity : "+ MergeSort.complexity);

        SortResult sortResult = new SortResult(
                 mean, variance,dataSize,
                averageTimeInNanoSeconds,
                theoreticalComplexity,
                complixity,
                "Merge Sort",
                data,
                new Date(),
                experiment,
                warmUpRounds
        );

        // After sorting, store the result in the map
        //String key = "Size:" + data.length + " Mean:" + mean + " Variance:" + variance;
        //resultsMap.put(key, sortResult);

        //System.out.println(" _____________________________ end ________________________________ ");

        // Save the result as before...
        // Omitted for brevity

        return sortResult;
    }

}