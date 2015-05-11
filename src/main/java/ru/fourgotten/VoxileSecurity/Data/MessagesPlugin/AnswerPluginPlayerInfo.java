package ru.fourgotten.VoxileSecurity.Data.MessagesPlugin;
import ru.methuselah.authlib.UserProvider;
import ru.methuselah.authlib.UserRole;

public class AnswerPluginPlayerInfo
{
	public UserProvider authProvider;
	public boolean guestFlag;
	public boolean secureLauncher;
	public UserRole projectRole;
	public UserRole serverRole;
}
