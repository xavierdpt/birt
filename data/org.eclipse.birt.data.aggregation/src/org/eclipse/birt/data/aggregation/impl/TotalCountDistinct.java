/*
 *************************************************************************
 * Copyright (c) 2004, 2008 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *  
 *************************************************************************
 */

package org.eclipse.birt.data.aggregation.impl;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.birt.core.data.DataType;
import org.eclipse.birt.data.aggregation.api.IBuildInAggregation;
import org.eclipse.birt.data.aggregation.i18n.Messages;
import org.eclipse.birt.data.aggregation.i18n.ResourceConstants;
import org.eclipse.birt.data.engine.api.aggregation.Accumulator;
import org.eclipse.birt.data.engine.api.aggregation.IParameterDefn;
import org.eclipse.birt.data.engine.core.DataException;

/**
 * Implements the built-in Total.countDistinct aggregation
 */
public class TotalCountDistinct extends AggrFunction {

	public String getName() {
		return IBuildInAggregation.TOTAL_COUNTDISTINCT_FUNC;
	}

	public int getType() {
		return SUMMARY_AGGR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.data.engine.api.aggregation.IAggregation#getDateType()
	 */
	public int getDataType() {
		return DataType.INTEGER_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.data.engine.api.aggregation.IAggrFunction#getParameterDefn()
	 */
	public IParameterDefn[] getParameterDefn() {
		// 1 argument
		return new IParameterDefn[] { new ParameterDefn(Constants.EXPRESSION_NAME, Constants.EXPRESSION_DISPLAY_NAME,
				false, true, SupportedDataTypes.ANY, "")//$NON-NLS-1$
		};
	}

	public Accumulator newAccumulator() {
		return new MyAccumulator();
	}

	private static class MyAccumulator extends SummaryAccumulator {
		private Set set;
		private boolean hasNullValue = false;

		public void start() {
			super.start();
			set = new HashSet();
			this.hasNullValue = false;
		}

		public void onRow(Object[] args) throws DataException {
			assert (args.length > 0);
			if (args[0] instanceof Comparable) {
				set.add(args[0]);
			} else {
				if (args[0] == null) {
					this.hasNullValue = true;
				} else {
					throw new DataException(ResourceConstants.UNSUPPORTED_DATA_TYPE,
							args[0] == null ? null : args[0].getClass().getName());
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.birt.data.engine.aggregation.SummaryAccumulator#getSummaryValue()
		 */
		public Object getSummaryValue() {
			return Integer.valueOf(set.size() + (this.hasNullValue ? 1 : 0));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.data.engine.api.aggregation.IAggrFunction#getDescription()
	 */
	public String getDescription() {
		return Messages.getString("TotalCountDistinct.description"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.data.engine.api.aggregation.IAggrFunction#getDisplayName()
	 */
	public String getDisplayName() {
		return Messages.getString("TotalCountDistinct.displayName"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.data.engine.api.aggregation.AggrFunction#getDefaultValue()
	 */
	public Object getDefaultValue() {
		return Integer.valueOf(0);
	}
}
