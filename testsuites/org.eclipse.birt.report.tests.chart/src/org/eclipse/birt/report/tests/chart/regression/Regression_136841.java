/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation. All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Actuate Corporation -
 * initial API and implementation
 ******************************************************************************/

package org.eclipse.birt.report.tests.chart.regression;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Angle3D;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.Orientation;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.impl.Angle3DImpl;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
import org.eclipse.birt.chart.model.attribute.impl.Rotation3DImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.AxisImpl;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.type.AreaSeries;
import org.eclipse.birt.chart.model.type.impl.AreaSeriesImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.birt.report.tests.chart.ChartTestCase;

/**
 * Regression description:
 * </p>
 * 3D chart, set stagger labels for Y axis, labels displayed incorrectly.
 * </p>
 * Test description:
 * <p>
 * 3D chart, set stagger labels for Y axis, view the chart
 * </p>
 */

public class Regression_136841 extends ChartTestCase {

	private static String GOLDEN = "Regression_136841.jpg"; //$NON-NLS-1$
	private static String OUTPUT = "Regression_136841.jpg"; //$NON-NLS-1$

	/**
	 * A chart model instance
	 */
	private Chart cm = null;

	/**
	 * The jpg rendering device
	 */
	private IDeviceRenderer dRenderer = null;

	private GeneratedChartState gcs = null;

	/**
	 * execute application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Regression_136841();
	}

	/**
	 * Constructor
	 */
	public Regression_136841() {
		final PluginSettings ps = PluginSettings.instance();
		try {
			dRenderer = ps.getDevice("dv.JPG");//$NON-NLS-1$

		} catch (ChartException ex) {
			ex.printStackTrace();
		}
		cm = createAreaChart();
		BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();

		Graphics2D g2d = (Graphics2D) g;
		dRenderer.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, g2d);
		dRenderer.setProperty(IDeviceRenderer.FILE_IDENTIFIER, this.genOutputFile(OUTPUT)); // $NON-NLS-1$
		Bounds bo = BoundsImpl.create(0, 0, 500, 500);
		bo.scale(72d / dRenderer.getDisplayServer().getDpiResolution());

		Generator gr = Generator.instance();

		try {
			gcs = gr.build(dRenderer.getDisplayServer(), cm, bo, null, null, null);
			gr.render(dRenderer, gcs);
		} catch (ChartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test_regression_136841() throws Exception {
		Regression_136841 st = new Regression_136841();
		assertTrue(st.compareImages(GOLDEN, OUTPUT));
	}

	/**
	 * Creates a area chart model as a reference implementation
	 * 
	 * @return An instance of the simulated runtime chart model (containing filled
	 *         datasets)
	 */
	public static final Chart createAreaChart() {
		ChartWithAxes cwaArea = ChartWithAxesImpl.create();

		// Chart Type
		cwaArea.setType("Area Chart");
		cwaArea.setDimension(ChartDimension.THREE_DIMENSIONAL_LITERAL);
		cwaArea.setRotation(
				Rotation3DImpl.create(new Angle3D[] { Angle3DImpl.createY(45), Angle3DImpl.createX(-20), }));

		// Title
		cwaArea.getTitle().getLabel().getCaption().setValue("Computer Hardware Sales"); //$NON-NLS-1$
		cwaArea.getBlock().setBackground(ColorDefinitionImpl.WHITE());

		// X-Axis
		Axis xAxisPrimary = ((ChartWithAxesImpl) cwaArea).getPrimaryBaseAxes()[0];
		xAxisPrimary.getTitle().setVisible(false);

		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
		xAxisPrimary.getLabel().getCaption().setColor(ColorDefinitionImpl.GREEN().darker());

		// Y-Axis
		Axis yAxisPrimary = ((ChartWithAxesImpl) cwaArea).getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getLabel().getCaption().setValue("Sales Growth"); //$NON-NLS-1$
		yAxisPrimary.getLabel().getCaption().setColor(ColorDefinitionImpl.BLUE());
		yAxisPrimary.setStaggered(true);
		yAxisPrimary.getTitle().setVisible(false);
		yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
		yAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);

		// Z-Axis
		Axis zAxisPrimary = AxisImpl.create(Axis.ANCILLARY_BASE);
		zAxisPrimary.setTitlePosition(Position.ABOVE_LITERAL);
		zAxisPrimary.getTitle().getCaption().setValue("Z Axis Title"); //$NON-NLS-1$
		zAxisPrimary.getTitle().setVisible(false);
		zAxisPrimary.setPrimaryAxis(true);
		zAxisPrimary.setLabelPosition(Position.ABOVE_LITERAL);
		zAxisPrimary.setOrientation(Orientation.HORIZONTAL_LITERAL);
		zAxisPrimary.getOrigin().setType(IntersectionType.MIN_LITERAL);
		zAxisPrimary.getOrigin().setValue(NumberDataElementImpl.create(0));
		zAxisPrimary.getTitle().setVisible(true);
		zAxisPrimary.setType(AxisType.TEXT_LITERAL);
		cwaArea.getPrimaryBaseAxes()[0].getAncillaryAxes().add(zAxisPrimary);

		cwaArea.getPrimaryOrthogonalAxis(cwaArea.getPrimaryBaseAxes()[0]).getTitle().getCaption().getFont()
				.setRotation(0);

		// Data Set
		TextDataSet dsStringValue = TextDataSetImpl
				.create(new String[] { "Keyboards", "Moritors", "Printers", "Mortherboards" });
		NumberDataSet dsNumericValues1 = NumberDataSetImpl.create(new double[] { 143.26, 156.55, 95.25, 47.56 });

		// X-Series
		Series seBase = SeriesImpl.create();
		seBase.setDataSet(dsStringValue);

		SeriesDefinition sdX = SeriesDefinitionImpl.create();
		xAxisPrimary.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seBase);

		// Y-Series
		AreaSeries as = (AreaSeries) AreaSeriesImpl.create();
		as.setSeriesIdentifier("Actuate"); //$NON-NLS-1$
		as.getLabel().getCaption().setColor(ColorDefinitionImpl.RED());
		as.getLabel().setBackground(ColorDefinitionImpl.CYAN());
		as.getLabel().setVisible(true);
		as.setLineAttributes(
				LineAttributesImpl.create(ColorDefinitionImpl.create(207, 41, 207), LineStyle.SOLID_LITERAL, 1));
		as.setDataSet(dsNumericValues1);

		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		yAxisPrimary.getSeriesDefinitions().add(sdY);
		sdY.getSeriesPalette().update(ColorDefinitionImpl.GREEN());
		sdY.getSeries().add(as);

		return cwaArea;
	}
}
