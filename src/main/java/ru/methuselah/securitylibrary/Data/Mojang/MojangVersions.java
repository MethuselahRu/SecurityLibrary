package ru.methuselah.securitylibrary.Data.Mojang;

public class MojangVersions
{
	public static class Latest
	{
		public String snapshot;
		public String release;
	}
	public static enum VersionType
	{
		release, snapshot, old_beta, old_alpha,
	}
	public static class Version
	{
		public String id;
		public String time;
		public String releaseTime;
		public VersionType type;
	}
	public Latest latest;
	public Version[] versions;
}
