package org.example;

import java.util.Arrays;
import java.util.Date;

public class SortResult {
    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double getDataSizes() {
        return dataSizes;
    }

    public void setDataSizes(double dataSizes) {
        this.dataSizes = dataSizes;
    }

    private double mean, variance, dataSizes;
    private double elapsedTimeInNanoSeconds;
    private double theoreticalComplexity;
    private double actualComplexity;
    private String sortAlgorithm;
    private Double[] sortedData;
    private Date experimentDate;
    private int experimentNumber;
    private int jvmWarmUpRounds;

    public SortResult() {
    }

    public SortResult(double mean, double variance, double dataSizes, double elapsedTimeInNanoSeconds, double theoreticalComplexity, double actualComplexity, String sortAlgorithm, Double[] sortedData, Date experimentDate, int experimentNumber, int jvmWarmUpRounds) {
        this.mean = mean;
        this.variance = variance;
        this.dataSizes = dataSizes;
        this.elapsedTimeInNanoSeconds = elapsedTimeInNanoSeconds;
        this.theoreticalComplexity = theoreticalComplexity;
        this.actualComplexity = actualComplexity;
        this.sortAlgorithm = sortAlgorithm;
        this.sortedData = sortedData;
        this.experimentDate = experimentDate;
        this.experimentNumber = experimentNumber;
        this.jvmWarmUpRounds = jvmWarmUpRounds;
    }



    public double getElapsedTimeInNanoSeconds() {
        return elapsedTimeInNanoSeconds;
    }

    public void setElapsedTimeInNanoSeconds(double elapsedTimeInNanoSeconds) {
        this.elapsedTimeInNanoSeconds = elapsedTimeInNanoSeconds;
    }

    public double getTheoreticalComplexity() {
        return theoreticalComplexity;
    }

    public void setTheoreticalComplexity(double theoreticalComplexity) {
        this.theoreticalComplexity = theoreticalComplexity;
    }

    public double getActualComplexity() {
        return actualComplexity;
    }

    public void setActualComplexity(double actualComplexity) {
        this.actualComplexity = actualComplexity;
    }

    public String getSortAlgorithm() {
        return sortAlgorithm;
    }

    public void setSortAlgorithm(String sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }

    public Double[] getSortedData() {
        return sortedData;
    }

    public void setSortedData(Double[] sortedData) {
        this.sortedData = sortedData;
    }

    public Date getExperimentDate() {
        return experimentDate;
    }

    public void setExperimentDate(Date experimentDate) {
        this.experimentDate = experimentDate;
    }

    public int getExperimentNumber() {
        return experimentNumber;
    }

    public void setExperimentNumber(int experimentNumber) {
        this.experimentNumber = experimentNumber;
    }

    public int getJvmWarmUpRounds() {
        return jvmWarmUpRounds;
    }

    public void setJvmWarmUpRounds(int jvmWarmUpRounds) {
        this.jvmWarmUpRounds = jvmWarmUpRounds;
    }

    @Override
    public String toString() {
        return "SortResult{" +
                "elapsedTimeInNanoSeconds=" + elapsedTimeInNanoSeconds +
                ", theoreticalComplexity=" + theoreticalComplexity +
                ", actualComplexity=" + actualComplexity +
                ", sortAlgorithm='" + sortAlgorithm + '\'' +
                ", sortedData=" + Arrays.toString(sortedData) +
                ", experimentDate=" + experimentDate +
                ", experimentNumber=" + experimentNumber +
                ", jvmWarmUpRounds=" + jvmWarmUpRounds +
                '}';
    }
}
