/*******************************************************************************
 * Copyright (c) 2011 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.data.engine.executor.transform.group;

import org.eclipse.birt.data.engine.core.DataException;
import org.eclipse.birt.data.engine.executor.transform.ResultSetPopulator;

public class IncrementalUpdateRowFilter extends IncrementalUpdateCaculator {

	public IncrementalUpdateRowFilter(ResultSetPopulator populator) throws DataException {
		super(populator);
	}

	public void onGroup(int index) throws DataException {
		for (int level = groupUpdators.length - 1; level >= 0; level--) {
			groupUpdators[level].onGroup(getCurrentGroupIndex(level + 1));
		}
		acceptAggrValues(index);
	}

	public void notOnGroup(int index) throws DataException {
		int rIdx = index;
		for (int level = groupUpdators.length - 1; level >= 0; level--) {
			rIdx = groupUpdators[level].notOnGroup(getCurrentGroupIndex(level + 1));
			if (rIdx < 0)
				break;

			if (level < groupUpdators.length - 1)
				groupUpdators[level + 1].increaseParentIndex();
		}

	}

	public void close() throws DataException {
		this.groupInfoUtil.setGroups(getGroups());

		populator.getResultIterator().clearAggrValueHolder();
		for (int i = 0; i < aggrValuesUpdators.length; i++) {
			populator.getResultIterator().addAggrValueHolder(aggrValuesUpdators[i]);
		}
	}

}
