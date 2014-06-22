/**
 * Copyright (C) 2006 - 2012
 *   Pawel Kedzior
 *   Tomasz Kmiecik
 *   Kamil Pietak
 *   Krzysztof Sikora
 *   Adam Wos
 *   Lukasz Faber
 *   Daniel Krzywicki
 *   and other students of AGH University of Science and Technology.
 *
 * This file is part of AgE.
 *
 * AgE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AgE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AgE.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created: 2012-03-16
 * $Id: DefaultSexualReproduction.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.emas.reproduction;

import java.util.Map.Entry;
import java.util.Random;

import javax.inject.Inject;

import org.jage.emas.agent.IndividualAgent;
import org.jage.evaluation.ISolutionEvaluator;
import org.jage.platform.component.provider.IComponentInstanceProvider;
import org.jage.platform.component.provider.IComponentInstanceProviderAware;
import org.jage.random.INormalizedDoubleRandomGenerator;
import org.jage.solution.ISolution;
import org.jage.solution.ISolutionFactory;
import org.jage.solution.IVectorSolution;
import org.jage.variation.mutation.IMutateSolution;
import org.jage.variation.recombination.IRecombine;

import comma.COMMAMutateSolution;
import comma.CommaMutation;
import comma.OperatorsScale;
import comma.Parameters;
import ranking.Ranking;

/**
 * Default implementation of {@link SexualReproduction}.
 * <p>
 * Each parent produces a gamete - a copy of its genotype. These are recombined and mutated, and one of them is randomly
 * choosen as the new agent's genotype.
 * <p>
 * Each of the parent also give 1/4 of its energy to the newborn child.
 *
 * @author AGH AgE Team
 */
public class DefaultSexualReproduction implements SexualReproduction<IndividualAgent>, IComponentInstanceProviderAware {

	private static final double ENERGY_FRACTION = 0.25;

	@Inject
	private ISolutionFactory<ISolution> solutionFactory;

	@Inject
	private IRecombine<ISolution> recombination;

	@Inject
	private IMutateSolution<ISolution> mutation;

	@Inject
	private INormalizedDoubleRandomGenerator rand;

	@Inject
	private ISolutionEvaluator<ISolution, Double> evaluator;

	private IComponentInstanceProvider provider;

	@Override
	public IndividualAgent reproduce(final IndividualAgent firstParent, final IndividualAgent secondParent) {
		final ISolution gamete = createGamete(firstParent, secondParent);
		final IndividualAgent child = createChild(gamete);
		transferEnergy(firstParent, secondParent, child);
		return child;
	}
	
	private ISolution createGamete(final IndividualAgent first, final IndividualAgent second) {
		final ISolution firstGamete = solutionFactory.copySolution(first.getSolution());
		final ISolution secondGamete = solutionFactory.copySolution(second.getSolution());
		recombination.recombine(firstGamete, secondGamete);

		// choose one at random
		final ISolution gamete = rand.nextDouble() <= 0.5 ? firstGamete : secondGamete;
		double fit = evaluator.evaluate(gamete);
		
		boolean fixedDistance = Parameters.getInstance().isFixedDistance();
		int currentGen = Parameters.getInstance().getCurrentGen();
		int maxGen = Parameters.getInstance().getMaxGen();
		int agents = Parameters.getInstance().getAgents();
		int r = Ranking.getInstance().getMiejsceWRankingu(fit);
		//System.out.println("Mutating " + "gen:" + currentGen + "/" + maxGen + " rank:" + r);
		mutateCOMMA(gamete, fixedDistance, currentGen, maxGen, agents, r);
		
		return gamete;
	}
	
	private COMMAMutateSolution commaMutateSolution = new COMMAMutateSolution();
	private OperatorsScale operatorsScale = new OperatorsScale();
	private Random random = new Random();
	
	private void mutateCOMMA(ISolution solution, boolean fixedDistance, int currentGen, int maxGen, int agents, int r) {
		int alpha = operatorsScale.size();
		double rate = 1 - (1.0*currentGen/(maxGen+1));
		
		int r_lower = (int) Math.max(0, (1.0*r*alpha/agents) - rate * alpha);
		int r_upper = (int) Math.min(alpha-1, (1.0 * r*alpha/agents) + rate * alpha);
		
		int r_op = r_lower == r_upper ? r_lower: random.nextInt(r_upper-r_lower)+r_lower;
		
		CommaMutation commaMutation = operatorsScale.getMutation(r_op);
		
		int distance;
		if(fixedDistance)
			distance = commaMutation.distance;
		else
			distance = random.nextInt(commaMutation.distance)+1;	
		
		
		commaMutateSolution.prepare(commaMutation, distance);
		//TODO: check if casting works
		commaMutateSolution.mutateSolution((IVectorSolution<Double>) solution); 
		r--;
	}

	private IndividualAgent createChild(final ISolution gamete) {
		final IndividualAgent child = provider.getInstance(IndividualAgent.class);
		child.setSolution(gamete);
		child.setOriginalFitness(evaluator.evaluate(gamete));
		return child;
	}

	private void transferEnergy(final IndividualAgent firstParent, final IndividualAgent secondParent,
	        final IndividualAgent child) {
		final double firstParentGift = firstParent.getEnergy() * ENERGY_FRACTION;
		final double secondParentGift = secondParent.getEnergy() * ENERGY_FRACTION;

		child.changeEnergyBy(firstParentGift + secondParentGift);
		firstParent.changeEnergyBy(-firstParentGift);
		secondParent.changeEnergyBy(-secondParentGift);
	}

	@Override
	public void setInstanceProvider(final IComponentInstanceProvider provider) {
		this.provider = provider;
	}
}
