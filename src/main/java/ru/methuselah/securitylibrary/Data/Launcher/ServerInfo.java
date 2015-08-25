package ru.methuselah.securitylibrary.Data.Launcher;

public class ServerInfo
{
	public String  caption = "Minecraft server";
	public String  address = "localhost";
	public boolean hideAddress = true;
	public ServerInfo(String name, String ip)
	{
		this(name, ip, false);
	}
	public ServerInfo(String name, String ip, boolean hideAddress)
	{
		this.caption = name;
		this.address = ip;
		this.hideAddress = hideAddress;
	}
}
