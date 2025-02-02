/*
 *************************************************************************
 * Copyright (c) 2006 Actuate Corporation.
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
package org.eclipse.birt.report.data.adapter.internal.adapter;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.data.engine.api.querydefn.ScriptDataSetDesign;
import org.eclipse.birt.report.data.adapter.impl.ModelAdapter;
import org.eclipse.birt.report.model.api.ScriptDataSetHandle;

/**
 * Adapts a Model Script Data Set definition
 *
 */
public class ScriptDataSetAdapter extends ScriptDataSetDesign {
	public ScriptDataSetAdapter(ScriptDataSetHandle modelDataSet, ModelAdapter adapter) throws BirtException {
		super(modelDataSet.getQualifiedName());

		// TODO: event handler!!

		DataAdapterUtil.adaptBaseDataSet(modelDataSet, this, adapter);

		// Adapt script data set elements
		setOpenScript(modelDataSet.getOpen());
		setFetchScript(modelDataSet.getFetch());
		setCloseScript(modelDataSet.getClose());
		setDescribeScript(modelDataSet.getDescribe());

	}

}
