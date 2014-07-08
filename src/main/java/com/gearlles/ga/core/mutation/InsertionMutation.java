package com.gearlles.ga.core.mutation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.util.logging.resources.logging;
import br.poli.ecomp.nestor.cn.graph.NodeVrp;

import com.gearlles.ga.App;
import com.gearlles.ga.core.Chromosome;
import com.gearlles.ga.core.Population;
import com.gearlles.ga.core.Route;
import com.rits.cloning.Cloner;

public class InsertionMutation implements MutationInterface
{
	private static final Logger log = LoggerFactory.getLogger(InsertionMutation.class);

	private static Random rand = new Random();

	public Chromosome mutate(Chromosome chromosome)
	{
//		System.out.println(String.format("B input=%d", chromosome.getNodeCount()));
		List<Route> gene = new ArrayList<Route>();
		for(Route r : chromosome.getGene())
		{
			Route route = new Route(new ArrayList<NodeVrp>(), r.getMaxCapacity(), r.getDistanceFunction());
			route.getNodes().addAll(r.getNodes());
			gene.add(route);
		}
		
		int genesSize = gene.size();
		ArrayList<Route> routesToRemove = new ArrayList<Route>();
		for (int i = 0; i < genesSize; i++)
		{
			Route r = gene.get(i);
			Iterator<NodeVrp> iter = r.getNodes().iterator();
			while (iter.hasNext())
			{
				NodeVrp node = iter.next();
				
				if (rand.nextDouble() > Population.mutationRatio)
				{
					continue;
				}

				iter.remove();

				int V = chromosome.getMap().getFleetSize();

				// Se ainda puder criar uma nova rota e a prob for escolhida,
				// cria
				if (rand.nextDouble() < 1.0 / (V * 2) && genesSize < chromosome.getMap().getFleetSize())
				{
					addNodeAsNewRoute(chromosome, gene, node);
				}
				else
				{
					// Se nao, tenta escolher uma aleatoriamente
					int newRouteIndex = rand.nextInt(genesSize - 1);
					Route selectedRoute = gene.get(newRouteIndex == i ? newRouteIndex + 1 : newRouteIndex);

					if (selectedRoute.getLoad() + node.getDemand() <= selectedRoute.getMaxCapacity())
					{
						selectedRoute.addNode(node);
					}
					else
					{
						// Caso nao caiba nela, tenta primeiro criar uma nova
						// rota
						if (genesSize < chromosome.getMap().getFleetSize())
						{
							addNodeAsNewRoute(chromosome, gene, node);
						}
						else
						{
							// Se nao puder criar uma nova, fica tentando achar
							// uma existente que caiba
							do
							{
								newRouteIndex = rand.nextInt(genesSize - 1);
								selectedRoute = gene.get(newRouteIndex == i ? newRouteIndex + 1 : newRouteIndex);
								if (selectedRoute.getLoad() + node.getDemand() <= selectedRoute.getMaxCapacity())
								{
									selectedRoute.addNode(node);
									break;
								}
								
								log.debug("Tentando novamente (se essa mensagem se repetir infinita, problema!)");
							} while (true);
						}
					}
				}
			}
		}
		
		for(Route r : gene)
		{
			if(r.getNodes().isEmpty())
			{
				routesToRemove.add(r);
			}
		}
		
		for(Route r : routesToRemove)
		{
			gene.remove(r);
		}
		Chromosome output = new Chromosome(gene, chromosome.getMap());
//		System.out.println("GENE-S: " + gene.toString());
//		System.out.println(String.format("A input=%d, output=%d", chromosome.getNodeCount(), output.getNodeCount()));

		return output;
	}

	private void addNodeAsNewRoute(Chromosome chromosome, List<Route> gene, NodeVrp node)
	{
		Route newRoute = new Route(new ArrayList<NodeVrp>(), chromosome.getMap().getVehicleCapacity(), chromosome
				.getMap().getFunction());
		newRoute.addNode(node);
		gene.add(newRoute);
	}

}
