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
 * Created: 2011-11-03
 * $Id: BinaryMutate.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.variation.mutation.binary;

import it.unimi.dsi.fastutil.booleans.BooleanList;

import java.util.Random;

import org.jage.solution.IVectorSolution;
import org.jage.strategy.AbstractStrategy;
import org.jage.variation.mutation.IMutateSolution;
import org.jage.variation.mutation.impls.FlipCommaMutation;
import org.jage.variation.mutation.impls.InvertCommaMutation;
import org.jage.variation.mutation.impls.SwapCommaMutation;

/**
 * Flips the given binary feature.
 * 
 * @author AGH AgE Team
 */
public final class LABSMutate extends AbstractStrategy implements
		IMutateSolution<IVectorSolution<Boolean>> {
	
	CommaMutation mutations[];
	int distance = 1;
	Random rand = new Random();
	
	public LABSMutate() {
		super();
		mutations = new CommaMutation[4];
		mutations[0] = new SwapCommaMutation(1);
		mutations[1] = new FlipCommaMutation(1);
		mutations[2] = new InvertCommaMutation(1);
		mutations[3] = new SwapCommaMutation(1);
	}
	
	@Override
	public void mutateSolution(IVectorSolution<Boolean> solution) {

		BooleanList representation = (BooleanList) solution.getRepresentation();
		determineMutation().doMutate(solution, 2);
	}

	private CommaMutation determineMutation() {

		int x = rand.nextInt(4);
		distance = rand.nextInt(5);
		return mutations[x];
		
	}
	
	
}
