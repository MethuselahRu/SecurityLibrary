package ru.methuselah.securitylibrary;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public final class SecureConnection implements Closeable, Runnable
{
	public static final String[] enabledCipherSuites = 
	{
		// "TLS_ECDH_anon_WITH_AES_256_CBC_SHA",
		// "TLS_ECDH_anon_WITH_AES_128_CBC_SHA",
		// "TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA",
		// "TLS_ECDH_anon_WITH_NULL_SHA",
		// Voxile Security Server
		"TLS_ECDH_anon_WITH_RC4_128_SHA",
	};
	public static interface ConnectionEstablishedNotification
	{
		void connectionEstablished(SecureConnection connection);
		void connectionBreaked();
	}
	private static final int sleepInterval = 1000;
	private final String host;
	private final int port;
	private final boolean plain;
	private final ConnectionEstablishedNotification vssl;
	private volatile boolean connected = false;
	private volatile Thread keepConnection;
	private volatile SecureSocketWrapper wrapper;
	public SecureConnection(int port)
	{
		this(null, port, null, false);
	}
	public SecureConnection(String host, int port)
	{
		this(host, port, null, false);
	}
	public SecureConnection(String host, int port, ConnectionEstablishedNotification owner)
	{
		this(host, port, owner, false);
	}
	public SecureConnection(String host, int port, ConnectionEstablishedNotification owner, boolean plain)
	{
		try
		{
			if(host == null || "".equals(host))
				host = InetAddress.getLocalHost().getHostAddress();
		} catch(UnknownHostException ex) {
			host = "127.0.0.1";
		}
		this.host = host;
		this.port = port;
		this.vssl = owner;
		this.plain = plain;
	}
	public SecureSocketWrapper getWrapper()
	{
		return wrapper;
	}
	public boolean isConnected()
	{
		return connected;
	}
	public void start()
	{
		close();
		keepConnection = new Thread(this);
		keepConnection.start();
	}
	@Override
	public void run()
	{
		Thread.currentThread().setName("Network connection (" + host + ":" + port + ")");
		System.out.println("Initiation of the network channel with: "
			+ host + ":" + port + (plain ? " (plain)" : " (ssl)"));
		try
		{
			for(; !Thread.interrupted(); Thread.sleep(sleepInterval))
				if(connected == false)
				{
					threadClose();
					try
					{
						wrapper = new SecureSocketWrapper(threadSocket());
						// Handshake
						if(wrapper.writeLine("hello!") && "greetings!".equals(wrapper.readLine()))
						{
							// Connected
							connected = true;
							if(vssl != null)
								vssl.connectionEstablished(SecureConnection.this);
						}
					} catch(IOException ex) {
					} catch(NullPointerException ex) {
						// System.out.println(ex.getLocalizedMessage());
					}
				// System.out.println(ex.getLocalizedMessage());
				
				}
		} catch(IllegalArgumentException ex) {
			System.err.println("Illegal port number, cancelling.");
		} catch(InterruptedException ex) {
		}
		threadClose();
	}
	private Socket threadSocket() throws IOException
	{
		// Insecure
		if(plain)
			return new Socket(host, port);
		// Secure
		SSLSocket socket = (SSLSocket)SSLSocketFactory.getDefault().createSocket(host, port);
		tryEnableCipherSuites(socket);
		return socket;
	}
	public static void tryEnableCipherSuites(SSLSocket socket)
	{
		// Enable all possible cipher suites
		for(String cipherSuite : enabledCipherSuites)
		{
			final ArrayList<String> enabled = new ArrayList<String>();
			enabled.addAll(Arrays.asList(socket.getEnabledCipherSuites()));
			enabled.add(cipherSuite);
			try
			{
				socket.setEnabledCipherSuites(enabled.toArray(new String[enabled.size()]));
			} catch(IllegalArgumentException ex) {
			}
		}
	}
	private void threadClose()
	{
		if(vssl != null)
			vssl.connectionBreaked();
		if(wrapper != null)
			wrapper.close();
		wrapper = null;
	}
	@Override
	public synchronized void close()
	{
		if(keepConnection != null)
		{
			try
			{
				keepConnection.interrupt();
				keepConnection.join();
			} catch(InterruptedException ex) {
			}
			keepConnection = null;
		}
	}
}
