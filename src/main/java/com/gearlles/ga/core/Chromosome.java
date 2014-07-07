package com.gearlles.ga.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.crossover.CrossoverInterface;
import com.gearlles.ga.core.fitness.FitnessFunction;
import com.gearlles.ga.core.mutation.MutationInterface;

public class Chromosome implements Comparable<Chromosome> {
	private List<Route> gene;
	private double fitness;
	private int size;

	private static final Random rand = new Random();

	public static FitnessFunction fitnessFunction;
	public static CrossoverInterface crossover;
	public static MutationInterface mutation;

	public Chromosome(int chromosomeSize) {
		this.size = chromosomeSize;

		List<Route> arr = new ArrayList<Route>();
		for (int i = 0; i < arr.size(); i++) {
			arr.add(new Route()); // TODO dentro dos limitesdo mapa
		}

		this.gene = arr;
		this.fitness = fitnessFunction.evaluate(gene);

	}

	public Chromosome(List<Route> gene) {
		this.gene = gene;
		this.size = gene.size();
		this.fitness = fitnessFunction.evaluate(gene);
	}

	public List<Route> getGene() {
		return gene;
	}

	public double getFitness() {
		return fitness;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Chromosome)) {
			return false;
		}

		Chromosome c = (Chromosome) o;
		return (gene.equals(c.gene) && fitness == c.fitness);
	}

	public int compareTo(Chromosome o) {
		if (fitness < o.fitness) {
			return -1;
		} else if (fitness > o.fitness) {
			return 1;
		}

		return 0;
	}
}
