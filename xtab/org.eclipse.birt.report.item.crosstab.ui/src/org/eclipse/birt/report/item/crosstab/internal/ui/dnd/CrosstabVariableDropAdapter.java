/*******************************************************************************
 * Copyright (c) 2008 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.item.crosstab.internal.ui.dnd;

import org.eclipse.birt.report.designer.internal.ui.VariableDropAdapter;
import org.eclipse.birt.report.designer.internal.ui.dnd.DNDLocation;
import org.eclipse.birt.report.designer.internal.ui.dnd.DNDService;
import org.eclipse.birt.report.designer.util.IVirtualValidator;
import org.eclipse.birt.report.model.api.VariableElementHandle;
import org.eclipse.gef.EditPart;

/**
 * 
 */

public class CrosstabVariableDropAdapter extends VariableDropAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.birt.report.designer.internal.ui.dnd.IDropAdapter#canDrop(java.
	 * lang.Object, java.lang.Object, int,
	 * org.eclipse.birt.report.designer.internal.ui.dnd.DNDLocation)
	 */
	public int canDrop(Object transfer, Object target, int operation, DNDLocation location) {
		if (transfer instanceof VariableElementHandle && target instanceof EditPart) {
			EditPart editPart = (EditPart) target;
			if (editPart.getModel() instanceof IVirtualValidator) {
				if (((IVirtualValidator) editPart.getModel()).handleValidate(transfer))
					return DNDService.LOGIC_TRUE;
				else
					return DNDService.LOGIC_FALSE;
			}
		}
		return DNDService.LOGIC_UNKNOW;
	}

}
