package com.gearlles.ga.core;

import java.util.List;
import java.util.Random;

import com.gearlles.ga.core.crossover.CrossoverInterface;
import com.gearlles.ga.core.fitness.FitnessFunction;
import com.gearlles.ga.core.mutation.MutationInterface;

public class Chromosome<T extends Comparable<T>> implements Comparable<Chromosome<T>> {
	private List<T> gene;
	private double fitness;
	private int size;

	private static final Random rand = new Random();

	public FitnessFunction<T> fitnessFunction;
	public CrossoverInterface<T> crossover;
	public MutationInterface<T> mutation;

	public Chromosome(int chromosomeSize, FitnessFunction<T> fitnessFunction) {
		this.size = chromosomeSize;

		this.gene = fitnessFunction.getRandom();
		this.fitness = fitnessFunction.evaluate(gene);

	}

	public Chromosome(List<T> gene, FitnessFunction<T> fitnessFunction) {
		this.gene = gene;
		this.size = gene.size();
		this.fitness = fitnessFunction.evaluate(gene);
	}

	public List<T> getGene() {
		return gene;
	}

	public double getFitness() {
		return fitness;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Chromosome<?>)) {
			return false;
		}

		Chromosome<T> c = (Chromosome<T>) o;
		return (gene.equals(c.gene) && fitness == c.fitness);
	}
	
	public int compareTo(Chromosome<T> o) {
		if (fitness < o.fitness) {
			return -1;
		} else if (fitness > o.fitness) {
			return 1;
		}

		return 0;
	}

	public FitnessFunction<T> getFitnessFunction() {
		return fitnessFunction;
	}

	public CrossoverInterface<T> getCrossover() {
		return crossover;
	}

	public MutationInterface<T> getMutation() {
		return mutation;
	}

}
