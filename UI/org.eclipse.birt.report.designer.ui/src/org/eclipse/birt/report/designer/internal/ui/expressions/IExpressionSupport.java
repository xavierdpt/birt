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

package org.eclipse.birt.report.designer.internal.ui.expressions;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * IExpressionSupport
 */
public interface IExpressionSupport {

	/**
	 * @return Return the unique name of this expression support.
	 */
	String getName();

	/**
	 * @return Return the name of this expression support for displaying.
	 */
	String getDisplayName();

	/**
	 * @return Returns the icon to reprensent this expression support.
	 */
	Image getImage();

	/**
	 * @return Returns the expression converter for this expressoin type if
	 *         applicable.
	 */
	IExpressionConverter getConverter();

	/**
	 * Creates the expression builder.
	 * 
	 * @param shl        The shell used to create the dialog.
	 * @param expression The inital expression object.
	 * @return The expression builder instance, or <code>null</code> if no builder
	 *         support is available.
	 */
	IExpressionBuilder createBuilder(Shell shl, Object expression);

}
