/*******************************************************************************
 * Copyright (c) 2012 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.designer.core.util.mediator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.birt.report.designer.core.mediator.IMediatorTarget;
import org.eclipse.birt.report.model.api.ModuleHandle;
import org.eclipse.birt.report.model.api.core.DisposeEvent;
import org.eclipse.birt.report.model.api.core.IDisposeListener;

/**
 * A mediator target wraping a ModuleHandle object.
 */
public class ModuleMediatorTarget implements IMediatorTarget {

	private ModuleHandle model;
	private Map<ITargetDisposeListener, IDisposeListener> listeners;

	public ModuleMediatorTarget(ModuleHandle model) {
		this.model = model;
		listeners = new HashMap<ITargetDisposeListener, IDisposeListener>();
	}

	@Override
	public int hashCode() {
		return model == null ? 0 : model.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof ModuleMediatorTarget)) {
			return false;
		}

		if (((ModuleMediatorTarget) obj).model == model) {
			return true;
		}

		if (model != null) {
			return model.equals(((ModuleMediatorTarget) obj).model);
		}

		return false;
	}

	public void addDisposeListener(final ITargetDisposeListener listener) {
		if (model != null && listener != null) {
			if (listeners.containsKey(listener)) {
				return;
			}

			IDisposeListener modelListener = new IDisposeListener() {

				public void moduleDisposed(ModuleHandle targetElement, DisposeEvent ev) {
					listener.dispose(new ModuleMediatorTarget(targetElement));
				}
			};

			listeners.put(listener, modelListener);
			model.addDisposeListener(modelListener);
		}
	}

	public void removeDisposeListener(ITargetDisposeListener listener) {
		if (model != null && listener != null) {
			IDisposeListener modelListener = listeners.remove(listener);

			if (modelListener != null) {
				model.removeDisposeListener(modelListener);
			}
		}
	}

}
