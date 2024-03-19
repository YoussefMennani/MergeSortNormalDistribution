package org.example.chart;

import org.example.SortResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SortResultSpiderChart extends ApplicationFrame {



    private Map<String, Color> seriesColors; // Track colors for each series
    private Map<String, Boolean> seriesVisibility; // Track visibility for each series
    private SpiderWebPlot plot;

    private JPanel detailsPanel; // Panel to display details


    int s = 1000;
    Map<String, SortResult> data;
    public SortResultSpiderChart(String title, Map<String, SortResult> resultsMap) {
        super(title);
        seriesColors = new HashMap<>();
        seriesVisibility = new HashMap<>();
        detailsPanel = new JPanel(); // Initialize the details panel

        data = resultsMap;
        JFreeChart chart = createChart(resultsMap);
        plot = (SpiderWebPlot) chart.getPlot();

        // Initialize visibility and colors
        int seriesIndex = 0;
        for (String algorithmName : resultsMap.keySet()) {
            Color color = getColorForSeries(seriesIndex);
            seriesColors.put(algorithmName, color);
            seriesVisibility.put(algorithmName, true); // Initially, all series are visible
            plot.setSeriesPaint(seriesIndex, color);
            seriesIndex++;
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(chartPanel, BorderLayout.CENTER);
        contentPane.add(createLegendPanel(resultsMap), BorderLayout.EAST);

        setContentPane(contentPane);
    }


    private Color getColorForSeries(int seriesIndex) {
        // This method should return a distinct color for each series based on the index
        // Here you can define your own color scheme
        Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.GRAY};
        return colors[seriesIndex % colors.length];
    }

    private JPanel createLegendPanel(Map<String, SortResult> resultsMap) {
        JPanel legendPanel = new JPanel();
        legendPanel.setPreferredSize(new Dimension(400, 300)); // Example: 400px wide, 300px tall

        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        for (Map.Entry<String, SortResult> entry : resultsMap.entrySet()) {
            String algorithmName = entry.getKey();
            Color color = seriesColors.get(algorithmName);

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel label = new JLabel(algorithmName);
            label.setForeground(color);

            JButton toggleButton = new JButton("Hide");
            toggleButton.setActionCommand(algorithmName);
            toggleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Call your method to toggle visibility here
                    toggleSeriesVisibility(e.getActionCommand());
                    // Toggle button text between "Hide" and "Show"
                    JButton btn = (JButton)e.getSource();
                    if ("Hide".equals(btn.getText())) {
                        btn.setText("Show");
                    } else {
                        btn.setText("Hide");
                    }
                }
            });

            itemPanel.add(toggleButton);
            itemPanel.add(label);
            legendPanel.add(itemPanel);
        }
        return legendPanel;
    }

    // Example stub of the toggleSeriesVisibility method
    private void toggleSeriesVisibility(String algorithmName) {

        System.out.println(" ----------------- DATA : --------------- ");
        System.out.println(data.get(algorithmName));
        boolean isVisible = seriesVisibility.getOrDefault(algorithmName, true);

        System.out.println(" Mean : "+data.get(algorithmName).getMean());
        System.out.println(" Variance : "+data.get(algorithmName).getVariance());
        System.out.println(" Data Size : "+data.get(algorithmName).getDataSizes());
        System.out.println(" Time Taken : "+data.get(algorithmName).getElapsedTimeInNanoSeconds()/s+" / "+s);

        seriesVisibility.put(algorithmName, !isVisible); // Toggle the visibility

        int seriesIndex = new ArrayList<>(seriesVisibility.keySet()).indexOf(algorithmName);
        if (seriesIndex != -1) {
            if (isVisible) {
                plot.setSeriesPaint(seriesIndex, new Color(0, 0, 0, 0)); // Make transparent to "hide"
            } else {
                plot.setSeriesPaint(seriesIndex, seriesColors.get(algorithmName)); // Restore original color to "show"
            }
        }
        System.out.println("Toggling visibility for: " + algorithmName + ". Now visible: " + !isVisible);
    }
    private JFreeChart createChart(Map<String, SortResult> resultsMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, SortResult> entry : resultsMap.entrySet()) {
            String algorithmName = entry.getKey();
            SortResult result = entry.getValue();

            // Add data to the dataset for the four specified metrics
            dataset.addValue(result.getMean(), algorithmName, "Mean");
            dataset.addValue(result.getVariance(), algorithmName, "Variance");
            dataset.addValue(result.getDataSizes()/10, algorithmName, "Data Size");
            dataset.addValue(result.getElapsedTimeInNanoSeconds() / s, algorithmName, "Time Taken (ms)"); // Convert nanoseconds to milliseconds
            //dataset.addValue(result.getElapsedTimeInNanoSeconds() / 1e9, algorithmName, "Time Taken (s)");

        }

        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        JFreeChart chart = new JFreeChart("Sort Algorithm Performance Comparison", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        return chart;
    }


}
