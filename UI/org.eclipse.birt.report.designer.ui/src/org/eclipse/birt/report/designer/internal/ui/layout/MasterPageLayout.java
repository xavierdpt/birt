/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation. All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Actuate Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.birt.report.designer.internal.ui.layout;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * This layout manager lays out the components inside the master page area.
 * 
 *  
 */
public class MasterPageLayout extends XYLayout
{

	/**
	 * Minimum height for header/footer. TODO:50 is the default value, migrate
	 * it to preference later.
	 */
	public final static int MINIMUM_HEIGHT = 50;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.LayoutManager#layout(org.eclipse.draw2d.IFigure)
	 */
	public void layout( IFigure parent )
	{
		List children = parent.getChildren( );
		Rectangle clientArea = parent.getClientArea( );
		int y = clientArea.y;
		int height = clientArea.height;
		int width = clientArea.width;

		IFigure figure;
		for ( int i = 0; i < children.size( ); i++ )
		{
			figure = (IFigure) children.get( i );

			Rectangle bounds = (Rectangle) getConstraint( figure );
			if ( bounds == null )
			{
				continue;
			}

			// this is to ensue the child layout can get the correct parent
			// client area width.
			// TODO: change/use the client layoutManager's
			// calculatePrefersize(widthHint, heightHint).
			figure.getBounds( ).width = width;

			Dimension preferredSize = figure.getPreferredSize( );
			bounds = bounds.getCopy( );

			if ( bounds.height <= 0 )
			{
				bounds.height = Math.max( preferredSize.height, MINIMUM_HEIGHT );
			}
			else if ( bounds.height < MINIMUM_HEIGHT )
			{
				bounds.height = MINIMUM_HEIGHT;
			}

			// adapt the figure's location to make sure it's inside the client
			// area.
			if (bounds.height > height)
			{
				bounds.height = height;
			}
			
			if ( bounds.y + bounds.height > height + y || bounds.y < y)
			{
				bounds.y = height + y - bounds.height;
			}

			figure.setBounds( bounds );

		}
	}
}