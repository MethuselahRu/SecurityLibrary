package ru.methuselah.securitylibrary.Data.Methuselah;
import java.util.UUID;
import ru.methuselah.authlib.UserProvider;
import ru.methuselah.authlib.UserRole;
import ru.methuselah.serversideplugin.API.PlayerInformation;

public class ProfileInfoResponse implements PlayerInformation
{
	protected String       uuid;
	protected UserRole     role;
	protected UserProvider provider;
	protected boolean      launcher;
	@Override
	public UUID getUUID()
	{
		return UUID.fromString(uuid);
	}
	@Override
	public UserRole getUserRole()
	{
		return role;
	}
	@Override
	public UserProvider getUserProvider()
	{
		return provider;
	}
	@Override
	public boolean isUsingCustomLauncher()
	{
		return launcher;
	}
	@Override
	public UUID[] getBoundUUIDs()
	{
		return new UUID[] { getUUID() };
	}
}
