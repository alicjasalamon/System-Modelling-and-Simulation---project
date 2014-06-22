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

package org.jage.emas.action.island;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jage.agent.AgentException;
import org.jage.agent.IAgent;
import org.jage.emas.agent.DefaultIslandAgent;
import org.jage.emas.util.ChainingAction;

import comma.Parameters;

import ranking.Ranking;

/**
 * This action handler performs statistics update actions on island agents. It triggers an island to recompute its
 * statistics.
 *
 * @author AGH AgE Team
 */
public final class StatisticsUpdateAction extends ChainingAction<DefaultIslandAgent> {

	private static final Logger LOG = LoggerFactory.getLogger(StatisticsUpdateAction.class);

	private static final int DEFAULT_RESOLUTION = 20;

	private int resolution = DEFAULT_RESOLUTION;

	public void setResolution(final int resolution) {
		this.resolution = resolution;
	}

	@Override
	public void doPerform(final DefaultIslandAgent agent) throws AgentException {
		log.debug("Performing statistics update action on {}", agent);
		//agent.getIndividualAgents().get(0).getEffectiveFitness();
		if (LOG.isDebugEnabled()) {
			LOG.debug(getChildrenLog(agent));
		}

		int step = (int) agent.getStep();
		if(step > 0) {
			agent.updateStatistics();
			Ranking.getInstance().setRanking(agent.getIndividualAgents());		
			Parameters.getInstance().setCurrentGen(step);
			Parameters.getInstance().setAgents(agent.getIndividualAgents().size());
		} else if (step == Parameters.getInstance().getMaxGen() - 1) {
			Ranking.getInstance().printRanking();
		}
		
		if ((agent.getStep() - 1) % resolution == 0) {
			//Ranking.getInstance().printRanking();
			//	System.out.println(agent.getStep() + " " + agent.getAvgFitness() + " " + agent.getBestFitnessEver());
		}
		
	}

	private String getChildrenLog(final DefaultIslandAgent agent) {
		final StringBuilder builder = new StringBuilder(agent + " children at step " + (agent.getStep() - 1) + "\n");
		for (final IAgent child : agent.getAgents()) {
			builder.append("\t" + child + "\n");
		}
		return builder.toString();
	}
}
