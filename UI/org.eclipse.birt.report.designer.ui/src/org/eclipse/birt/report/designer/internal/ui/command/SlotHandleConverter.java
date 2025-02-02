/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.designer.internal.ui.command;

import org.eclipse.birt.report.designer.core.model.SessionHandleAdapter;
import org.eclipse.birt.report.model.api.SlotHandle;
import org.eclipse.core.commands.AbstractParameterValueConverter;
import org.eclipse.core.commands.ParameterValueConversionException;

/**
 * 
 */

public class SlotHandleConverter extends AbstractParameterValueConverter {

	public SlotHandleConverter() {
		// TODO Auto-generated constructor stub
	}

	public Object convertToObject(String parameterValue) throws ParameterValueConversionException {
		String elementId = parameterValue.substring(0, parameterValue.indexOf("#")); //$NON-NLS-1$
		String slotId = parameterValue.substring(parameterValue.indexOf("#") + 1, //$NON-NLS-1$
				parameterValue.length());
		return SessionHandleAdapter.getInstance().getReportDesignHandle().getElementByID(Long.parseLong(elementId))
				.getSlot(Integer.parseInt(slotId));
	}

	public String convertToString(Object parameterValue) throws ParameterValueConversionException {

		return ((SlotHandle) parameterValue).getElement().getID() + "#" //$NON-NLS-1$
				+ ((SlotHandle) parameterValue).getSlotID();
	}

}
