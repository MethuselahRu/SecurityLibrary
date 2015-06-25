package ru.methuselah.securitylibrary.Defaults;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import ru.methuselah.securitylibrary.Data.MessagesLauncher.MessageLauncherRun;

public class DefaultLauncherRun
{
	public static MessageLauncherRun getFilledMessageLauncherRun()
	{
		final MessageLauncherRun result = new MessageLauncherRun();
		try
		{
			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			while(networks.hasMoreElements())
			{
				NetworkInterface network = networks.nextElement();
				byte[] mac = network.getHardwareAddress();
				if(mac != null && mac.length == 6)
				{
					StringBuilder sb = new StringBuilder();
					for(byte b : mac)
						sb.append(String.format("%02X", b));
					if(!result.mac_address.equalsIgnoreCase(sb.toString()))
						result.mac_address = sb.toString();
				}
			}
		} catch(SocketException ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		try
		{
			result.ip_address_local = InetAddress.getLocalHost().getHostAddress();
		} catch(UnknownHostException ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		try
		{
			result.path_run = DefaultLauncherRun.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch(URISyntaxException ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		return result;
	}
}
