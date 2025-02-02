/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - modification of Batik's ListValue.java to support BIRT's CSS rules
 *******************************************************************************/

package org.eclipse.birt.report.engine.css.engine.value;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

/**
 * This class represents a list of values.
 * 
 */
public class ListValue extends Value implements CSSValueList {

	/**
	 * The length of the list.
	 */
	protected int length;

	/**
	 * The items.
	 */
	protected CSSValue[] items = new CSSValue[5];

	/**
	 * The list separator.
	 */
	protected char separator = ',';

	/**
	 * Creates a ListValue.
	 */
	public ListValue() {
	}

	/**
	 * Creates a ListValue with the given separator.
	 */
	public ListValue(char s) {
		separator = s;
	}

	/**
	 * Returns the separator used for this list.
	 */
	public char getSeparatorChar() {
		return separator;
	}

	/**
	 * Implements {@link Value#getCssValueType()}.
	 */
	public short getCssValueType() {
		return CSSValue.CSS_VALUE_LIST;
	}

	/**
	 * A string representation of the current value.
	 */
	public String getCssText() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			Value value = (Value) items[i];
			if (value == null) {
				continue;
			}
			short valueType = value.getCssValueType();
			if (valueType == CSSValue.CSS_PRIMITIVE_VALUE) {
				switch (value.getPrimitiveType()) {
				case CSSPrimitiveValue.CSS_STRING:
					sb.append(encodeString(value.getStringValue()));
					break;
				case CSSPrimitiveValue.CSS_URI:
					sb.append("url('");
					sb.append(value.getStringValue());
					sb.append("')");
					break;
				default:
					sb.append(value.getCssText());
				}
			} else {
				sb.append(value.getCssText());
			}
			sb.append(separator);
		}
		if (sb.length() != 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	protected String encodeString(String value) {
		char[] chars = value.toCharArray();

		boolean needQuote = false;
		for (int i = 0; i < chars.length; i++) {
			switch (chars[i]) {
			case '"':
				return '\'' + value + '\'';
			case '\'':
				return '"' + value + '"';
			case ' ':
			case '{':
			case '}':
			case '[':
			case ']':
			case '(':
			case ')':
			case ';':
			case '!':
			case ',':
			case '.':
			case '\\':
				needQuote = true;
				break;
			}
		}
		if (needQuote)
			return '\"' + value + '\"';
		else
			return value;
	}

	/**
	 * Implements {@link Value#getLength()}.
	 */
	public int getLength() throws DOMException {
		return length;
	}

	/**
	 * Implements {@link Value#item(int)}.
	 */
	public CSSValue item(int index) throws DOMException {
		return items[index];
	}

	/**
	 * Returns a printable representation of this value.
	 */
	public String toString() {
		return getCssText();
	}

	/**
	 * Appends an item to the list.
	 */
	public void append(CSSValue v) {
		if (length == items.length) {
			CSSValue[] t = new CSSValue[length * 2];
			for (int i = 0; i < length; i++) {
				t[i] = items[i];
			}
			items = t;
		}
		items[length++] = v;
	}

	public boolean equals(Object value) {
		if (value instanceof ListValue) {
			ListValue l = (ListValue) value;
			if (l.length == length) {
				for (int i = 0; i < length; i++) {
					CSSValue i1 = items[i];
					CSSValue i2 = l.items[i];
					if (i1 != i2) {
						if (i1 == null || !i1.equals(i2)) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}
}
