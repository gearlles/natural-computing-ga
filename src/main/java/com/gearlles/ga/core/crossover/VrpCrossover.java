package com.gearlles.ga.core.crossover;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import br.poli.ecomp.nestor.cn.graph.NodeVrp;
import br.poli.ecomp.nestor.cn.graph.distance.DistanceFunction;

import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Route;

public class VrpCrossover implements CrossoverInterface {
	
	private static Random rand = new Random();

	public Chromosome[] mate(Chromosome dad, Chromosome mom) {
		List<Route> dadGene = dad.getGene();
		List<Route> momGene = mom.getGene();
		List<Route> momGeneCopy = new ArrayList<Route>(momGene);
		
		DistanceFunction distanceFunction = dad.getMap().getFunction();		
		int randomRoute = rand.nextInt(dadGene.size());
		
		// 0, 1, 2, 3, 4, 5   size = 6
		int randomSubRoutStart = rand.nextInt(dadGene.size() - 1); // 0 até 4
		int randomSubRoutEnd = randInt(randomSubRoutStart + 1, dadGene.size() - 1); // do número escolhido acima, até o fim (inicio, fim[
		
		List<NodeVrp> subRoute = dadGene.get(randomRoute).getNodes().subList(randomSubRoutStart, randomSubRoutEnd + 1);
		NodeVrp a1 = subRoute.get(0);
		
		// achar o nó mais próximo de a1
		int momCloserRouteIndex = 0;
		int momCloserRouteNodeIndex = 0;
		double closerDistance = Double.MAX_VALUE;
		for (int i = 0; i < momGeneCopy.size(); i++) {
			Route momRoute = momGeneCopy.get(i);
			List<NodeVrp> nodes = momRoute.getNodes();
			for (int j = 0; j < nodes.size(); j++) {
				NodeVrp node = nodes.get(j);
				if (!(node.getX() == a1.getX() && node.getY() == a1.getY() ))  { // verificando se estão na mesma posição
					double dist = distanceFunction.calculate(node.getX(), node.getY(), a1.getX(), a1.getY());
					if (dist < closerDistance) {
						closerDistance = dist;
						momCloserRouteIndex = i;
						momCloserRouteNodeIndex = j;
					}
				}
			}
		}
		
		// achamos a posição do nó mais próximo, vamos concatenar
		Route momSelectedRoute = momGeneCopy.get(momCloserRouteIndex);
		momSelectedRoute.getNodes().addAll(momCloserRouteNodeIndex + 1, subRoute);
		
		// remover nós duplicados
		Iterator<Route> routeIterator = momGeneCopy.iterator();
		while (routeIterator.hasNext()) {
			Route route = routeIterator.next();
			List<NodeVrp> nodes = route.getNodes();
			Iterator<NodeVrp> nodesIterator = nodes.iterator();
			while (nodesIterator.hasNext()) {
				NodeVrp nodeVrp = nodesIterator.next();
				// nessa verificação não haverá o problema de remover o que acabamos de colocar
				// porque são objetos diferentes, então contains funcionará
				if (subRoute.contains(nodeVrp)) {
					nodesIterator.remove();
				}
			}

			// se não sobrou nós da rota, remove a rota
			if (route.getNodes().isEmpty()) {
				routeIterator.remove();
			}
		}
		List<Route> processedRoute = process(momGeneCopy);
		return new Chromosome[]{new Chromosome(processedRoute)};
	}
	
	private List<Route> process(List<Route> momGeneCopy) {
		Iterator<Route> routeIterator = momGeneCopy.iterator();
		while (routeIterator.hasNext()) {
			Route route = (Route) routeIterator.next();
			
			if (getLoad(route.getNodes()) <= route.getMaxCapacity()) continue;
			
			momGeneCopy.remove(route);
			
			List<Route> res = splitRoute(route);
			for (Route rout : res) {
				momGeneCopy.add(rout);
			}
		}
		return momGeneCopy;
	}

	private List<Route> splitRoute(Route route) {
		double capacity = route.getMaxCapacity();
		
		List<Route> res = new ArrayList<Route>();
		List<NodeVrp> toSplit = route.getNodes();
		
		while(true) {
			Route r = new Route(null, capacity, route.getDistanceFunction());
			List<NodeVrp> nodesForRoute = new ArrayList<NodeVrp>();
			double tempCap = 0;
			for (int i = 0; i < toSplit.size(); i++) {
				NodeVrp n = toSplit.get(i);
				if (!(tempCap + n.getDemand() <= capacity)) {
					
				}
			}
			res.add(route);
		}
		
		return res;
	}

	private double getLoad(List<NodeVrp> nodes) {
		double load = 0;
		for (NodeVrp nodeVrp : nodes) {
			load += nodeVrp.getDemand();
		}
		return load;
	}

	public static int randInt(int min, int max) {
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
