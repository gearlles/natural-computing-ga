package com.gearlles.ga;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Population;
import com.gearlles.ga.core.crossover.TwoPoint;
import com.gearlles.ga.core.fitness.Rastrigin;
import com.gearlles.ga.core.mutation.UniformMutation;
import com.gearlles.ga.core.selection.Tournament;

public class App {
	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		final int maxGenerations = 10000;
		final int dimension = 30;
		final int populationSize = 30;

		Population.crossoverRatio = 0.8f;
		Population.elitismRatio = 0f;
		Population.mutationRatio = 0.05f;

		Chromosome.crossover = new TwoPoint();
		Chromosome.fitnessFunction = new Rastrigin(dimension);
		Chromosome.mutation = new UniformMutation();

		Population.selection = new Tournament(5);

		double[] generationResults = new double[maxGenerations];
		double bestFitness = Double.MAX_VALUE;

		for (int k = 0; k < 30; k++) {
			// Create the first population and initializate it
			Population pop = new Population(dimension, populationSize);

			Chromosome best = pop.getPopulation()[0];
			bestFitness = best.getFitness();

			for (int i = 0; i < maxGenerations; i++) {
				pop.evolve();
				best = pop.getPopulation()[0];

				if (bestFitness > best.getFitness()) {
					bestFitness = best.getFitness();
				}

				generationResults[i] += bestFitness;
			}
		}

		for (int i = 0; i < generationResults.length; i++) {
			log.debug(String.format("%f", generationResults[i] / 30));
		}

		// plot(generationResults);

	}

	public static void plot(double[] generationResults) {
		XYSeries series = new XYSeries("Fitness");
		for (int i = 0; i < generationResults.length; i++) {
			series.add(i, generationResults[i]);
		}

		// Add the series to your data set
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		// Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart("Algoritmo Genético", // Title
				"Geração", // x-axis Label
				"Melhor Fitness", // y-axis Label
				dataset, // Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		try {
			ChartUtilities.saveChartAsJPEG(new File("./chart.jpg"), chart, 500, 300);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
