/*
 * Copyright (c) 2011 HawkinsSoftware
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Byron Hawkins of HawkinsSoftware
 */
package org.hawkinssoftware.rns.core.role;

import java.util.HashMap;

import org.hawkinssoftware.rns.core.util.RNSUtils;

/**
 * DOC comment task awaits.
 * 
 * @author Byron Hawkins
 */
public class TypeRole extends CommunicationRole
{
	
	/**
	 * DOC comment task awaits.
	 * 
	 * @author Byron Hawkins
	 */
	public static class Map extends HashMap<Class<?>, TypeRole>
	{
		public void add(TypeRole role)
		{
			put(role.type, role);
		}
	}

	public static final TypeRole NONE = new TypeRole(Object.class);

	private Class<?> type;

	public TypeRole(Class<?> type)
	{
		this.type = type;
	}

	public Class<?> getType()
	{
		return type;
	}

	public void setType(Class<?> type)
	{
		this.type = type;
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder(RNSUtils.getPlainName(type));
		java.util.Map<Class<?>, Object> properties = getProperties();
		if (!properties.isEmpty())
		{
			buffer.append(" {");
			for (java.util.Map.Entry<Class<?>, Object> property : properties.entrySet())
			{
				buffer.append(RNSUtils.getPlainName(property.getKey()));
				buffer.append(": ");
				buffer.append(property.getValue());
				buffer.append(", ");
			}
			buffer.setLength(buffer.length() - 2);
			buffer.append("}");
		}

		return buffer.toString();
	}
}
