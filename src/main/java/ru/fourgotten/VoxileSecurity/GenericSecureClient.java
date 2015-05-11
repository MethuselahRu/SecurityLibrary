package ru.fourgotten.VoxileSecurity;
import java.io.Closeable;
import ru.fourgotten.VoxileSecurity.SecureConnection.ConnectionEstablishedNotification;

public abstract class GenericSecureClient implements Closeable, ConnectionEstablishedNotification
{
	private final ConnectionEstablishedNotification notification;
	private final SecureConnection[] connections =
	{
		new SecureConnection("methuselah.ru",  25500, this), // Мафусаил API
		new SecureConnection("37.59.50.158",   25500, this),
		new SecureConnection("s1.voxile.ru",   25500, this), // Офисный
		new SecureConnection("92.255.195.202", 25500, this),
		new SecureConnection("s0.voxile.ru",   25500, this), // 2×4.ru
		new SecureConnection("194.63.140.175", 25500, this),
	};
	private volatile boolean connected = false;
	private static final int waitForGranulation = 500;
	public GenericSecureClient(ConnectionEstablishedNotification notification, boolean autoStart)
	{
		this.notification = notification;
		if(autoStart)
			start();
	}
	public GenericSecureClient(ConnectionEstablishedNotification notification)
	{
		this(notification, true);
	}
	@Override
	public void connectionEstablished(SecureConnection connection)
	{
		if(notification == null)
			return;
		boolean oldStatus = connected;
		connected = true;
		if(!oldStatus)
			notification.connectionEstablished(connection);
	}
	@Override
	public void connectionBreaked()
	{
		if(notification == null)
			return;
		boolean oldStatus = connected;
		connected = false;
		if(connections != null)
			for(SecureConnection conn : connections)
				if(conn.isConnected())
					connected = true;
		if(oldStatus == true && connected == false)
			notification.connectionBreaked();
	}
	public synchronized boolean isConnected()
	{
		return connected;
	}
	public boolean waitForConnection(int millis)
	{
		try
		{
			for(int time = 0; time * waitForGranulation < millis; time += 1, Thread.sleep(millis))
				if(connected)
					return true;
		} catch(InterruptedException ex) {
		}
		return connected;
	}
	protected SecureConnection getWorkingConnection()
	{
		for(SecureConnection connection : connections)
			if(connection.isConnected())
				return connection;
		return null;
	}
	protected SecureSocketWrapper getWorkingWrapper()
	{
		final SecureConnection connection = getWorkingConnection();
		return connection != null && connection.isConnected()
			? connection.getWrapper()
			: null;
	}
	public final synchronized void start()
	{
		for(SecureConnection connection : connections)
			connection.start();
	}
	@Override
	public final synchronized void close()
	{
		for(SecureConnection connection : connections)
			if(connection.isConnected())
				connection.close();
	}
}
