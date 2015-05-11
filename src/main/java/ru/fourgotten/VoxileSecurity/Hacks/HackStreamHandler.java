package ru.fourgotten.VoxileSecurity.Hacks;
import ru.fourgotten.VoxileSecurity.ProGuardKeep;
import ru.methuselah.authlib.ReplacementListEntrySH;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import ru.methuselah.authlib.GlobalReplacementList;

@ProGuardKeep
public class HackStreamHandler extends URLStreamHandler
{
	private final URLStreamHandler defaultHandler;
	private final Method openConnNoProxy;
	private final Method openConnProxy;
	private final ReplacementListEntrySH[] replacements;
	private static class FakeTrustManager implements X509TrustManager
	{
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
		{
		}
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
		{
		}
		@Override
		public X509Certificate[] getAcceptedIssuers()
		{
			return new X509Certificate[0];
		}
	}
	private static final FakeTrustManager[] trustManagers = { new FakeTrustManager(), };
	public HackStreamHandler(String protocol, ReplacementListEntrySH[] replacements)
	{
		defaultHandler = ("https".equals(protocol)
			? new sun.net.www.protocol.https.Handler()
			: new sun.net.www.protocol.http.Handler());
		try
		{
			if("https".equals(protocol))
			{
				final SSLContext sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, trustManagers, null);
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			}
		} catch(NoSuchAlgorithmException ex) {
		} catch(KeyManagementException ex) {
		} catch(RuntimeException ex) {
		}
		this.replacements = replacements != null
			? replacements
			: GlobalReplacementList.defaultReplacementsSH;
		try
		{
			openConnNoProxy = defaultHandler.getClass().getDeclaredMethod("openConnection", URL.class);
			openConnNoProxy.setAccessible(true);
			openConnProxy = defaultHandler.getClass().getDeclaredMethod("openConnection", URL.class, Proxy.class);
			openConnProxy.setAccessible(true);
		} catch(NoSuchMethodException ex) {
			throw new RuntimeException(ex);
		}
	}
	@Override
	protected synchronized URLConnection openConnection(URL url) throws IOException
	{
		try
		{
			return (URLConnection)openConnNoProxy.invoke(defaultHandler, replaceURL(url));
		} catch(IllegalAccessException ex) {
		} catch(InvocationTargetException ex) {
		}
		return null;
	}
	@Override
	protected synchronized URLConnection openConnection(URL url, Proxy proxy) throws IOException
	{
		try
		{
			final URLConnection connection = (URLConnection)openConnProxy.invoke(defaultHandler, replaceURL(url), proxy);
			return connection;
		} catch(IllegalAccessException ex) {
		} catch(InvocationTargetException ex) {
		}
		return null;
	}
	private URL replaceURL(URL originalUrl)
	{
		try
		{
			String replacedUrl = originalUrl.toString();
			for(ReplacementListEntrySH entry : replacements)
				if(entry.find != null && !"".equals(entry.find) && entry.replace != null)
					replacedUrl = replacedUrl.replace(entry.find, entry.replace);
			final boolean hasChanged = !replacedUrl.equals(originalUrl.toString());
			if(hasChanged)
				System.out.println("Methuselah has changed url from " + originalUrl.toString() + " to " + replacedUrl);
			return hasChanged ? new URL(replacedUrl) : originalUrl;
		} catch(MalformedURLException ex) {
			return originalUrl;
		}
	}
	protected static void process(final ReplacementListEntrySH[] replacements, ClassLoader classLoader)
	{
		try
		{
			final URLStreamHandlerFactory factory = new URLStreamHandlerFactory()
			{
				@Override
				public URLStreamHandler createURLStreamHandler(String protocol)
				{
					return ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol))
						? new HackStreamHandler(protocol, replacements)
						: null;
				}
			};
			final Class classUrl = Class.forName("java.net.URL", true, classLoader);
			final Field lockField = classUrl.getDeclaredField("streamHandlerLock");
			boolean isLockFieldAccessible = lockField.isAccessible();
			lockField.setAccessible(true);
			synchronized(lockField.get(null))
			{
				final Field factoryField = classUrl.getDeclaredField("factory");
				final boolean isAccessible = factoryField.isAccessible();
				factoryField.setAccessible(true);
				factoryField.set(null, null);
				final Method setHandlerMethod = classUrl.getDeclaredMethod("setURLStreamHandlerFactory", new Class[] { URLStreamHandlerFactory.class });
				setHandlerMethod.invoke(null, factory);
				factoryField.setAccessible(isAccessible);
			}
			lockField.setAccessible(isLockFieldAccessible);
		} catch(ClassNotFoundException ex) {
		} catch(NoSuchFieldException ex) {
		} catch(SecurityException ex) {
		} catch(IllegalArgumentException ex) {
		} catch(IllegalAccessException ex) {
		} catch(NoSuchMethodException ex) {
		} catch(InvocationTargetException ex) {
		}
	}
}
