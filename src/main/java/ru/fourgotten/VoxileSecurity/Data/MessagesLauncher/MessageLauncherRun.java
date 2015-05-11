package ru.fourgotten.VoxileSecurity.Data.MessagesLauncher;

public class MessageLauncherRun
{
	public String version;
	public String os_name          = System.getProperty("os.name");
	public String os_version       = System.getProperty("os.version");
	public String os_architecture  = System.getProperty("os.arch");
	public String java_vendor      = System.getProperty("java.vendor");
	public String java_version     = System.getProperty("java.version") + " (x" + System.getProperty("sun.arch.data.model") + ")";
	public String mac_address      = "000000000000";
	public String ip_address_local = "127.0.0.1";
	public String path_run         = "";
}
