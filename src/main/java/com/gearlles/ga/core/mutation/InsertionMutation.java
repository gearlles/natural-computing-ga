package com.gearlles.ga.core.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.poli.ecomp.nestor.cn.graph.NodeVrp;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Population;
import com.gearlles.ga.core.Route;

public class InsertionMutation implements MutationInterface {
	private static Random rand = new Random();

	public Chromosome mutate(Chromosome chromosome) {
		List<Route> gene = chromosome.getGene();
		int genesSize = gene.size(); 
		for (int i = 0; i < genesSize; i++) {
			Route r = gene.get(i);
			for(NodeVrp node : r.getNodes()) {
				if (rand.nextDouble() > Population.mutationRatio) {
					continue;
				}
				
				r.getNodes().remove(node);
				
				int newRouteIndex = rand.nextInt(genesSize);
				
				int V = chromosome.getMap().getFleetSize();
				// Se a prob de criar uma nova rota NAO for sorteada ou a quantidade de rotas for a capacidade maxima 
				if(rand.nextDouble() > 1.0 / (V * 2) || genesSize >= chromosome.getMap().getFleetSize()) {
					gene.get(newRouteIndex == i ? newRouteIndex + 1 : newRouteIndex).addNode(node);
				} else {
					Route newRoute = new Route(new ArrayList<NodeVrp>(), chromosome.getMap().getVehicleCapacity(), chromosome.getMap().getFunction());
					newRoute.addNode(node);
					gene.add(newRoute);
				}
			}
		}

		return new Chromosome(gene);
	}

}
