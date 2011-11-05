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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The DomainRole is the base class for the domain hierarchy 
 * 
 * @author Byron Hawkins
 */
public class DomainRole extends CommunicationRole
{
	
	/**
	 * DOC comment task awaits.
	 * 
	 * @author Byron Hawkins
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Join
	{
		Class<? extends DomainRole>[] membership() default {};
	}

	/**
	 * DOC comment task awaits.
	 * 
	 * @author Byron Hawkins
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Instance
	{
	}

	private String name;

	public boolean includes(CommunicationRole other)
	{
		if (other == this)
		{
			return true;
		}

		for (CommunicationRole category : other.membership)
		{
			if (includes(category))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * DOC comment task awaits.
	 * 
	 * @author Byron Hawkins
	 */
	public static class OrthogonalSet
	{
		final List<DomainRole> domainsAssembly = new ArrayList<DomainRole>();
		final List<String> packagePatternsAssembly = new ArrayList<String>();

		public final List<DomainRole> domains = Collections.unmodifiableList(domainsAssembly);
		public final List<String> packagePatterns = Collections.unmodifiableList(packagePatternsAssembly);
	}

	/**
	 * DOC comment task awaits.
	 * 
	 * @author Byron Hawkins
	 */
	public static class Resolver
	{
		private static final Map<Class<? extends DomainRole>, DomainRole> INSTANCE_CACHE = new HashMap<Class<? extends DomainRole>, DomainRole>();

		public static DomainRole getInstance(Class<? extends DomainRole> type) throws IllegalArgumentException, IllegalAccessException
		{
			DomainRole instance = INSTANCE_CACHE.get(type);
			if (instance == null)
			{
				for (Field field : type.getFields())
				{
					if (Modifier.isStatic(field.getModifiers()) && (field.getAnnotation(DomainRole.Instance.class) != null))
					{
						field.setAccessible(true);
						instance = (DomainRole) field.get(null);
						INSTANCE_CACHE.put(type, instance);
					}
				}
				if (instance == null)
				{
					throw new IllegalStateException("Domain role " + type.getCanonicalName() + " has no instance!");
				}
			}
			return instance;
		}
	}
}