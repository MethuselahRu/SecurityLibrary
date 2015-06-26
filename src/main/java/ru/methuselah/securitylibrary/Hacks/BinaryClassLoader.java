package ru.methuselah.securitylibrary.Hacks;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BinaryClassLoader extends URLClassLoader
{
	private final Map<String, byte[]> extraClassDefs = new HashMap<String, byte[]>();
	public BinaryClassLoader(URL[] urls, ClassLoader parent)
	{
		super(urls, parent);
	}
	public void addClass(String fqdn, byte[] definition)
	{
		this.extraClassDefs.put(fqdn, Arrays.copyOf(definition, definition.length));
	}
	public void addClasses(Map<String, byte[]> definitions)
	{
		this.extraClassDefs.putAll(definitions);
	}
	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException
	{
		final byte[] classBytes = this.extraClassDefs.get(name);
		return classBytes != null
			? defineClass(name, classBytes, 0, classBytes.length)
			: super.findClass(name);
	}
}
