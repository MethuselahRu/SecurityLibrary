package ru.methuselah.securitylibrary;

import ru.methuselah.authlib.methods.ResponseException;
import ru.methuselah.authlib.methods.WebMethodCaller;
import ru.methuselah.securitylibrary.Data.Launcher.LauncherAnswerClients;
import ru.methuselah.securitylibrary.Data.Launcher.LauncherAnswerProjects;
import ru.methuselah.securitylibrary.Data.Launcher.LauncherAnswerServers;
import ru.methuselah.securitylibrary.Data.Launcher.LauncherMessageGetClients;
import ru.methuselah.securitylibrary.Data.Launcher.LauncherMessageGetServers;
import ru.methuselah.securitylibrary.Data.Methuselah.ProfileInfoPayload;
import ru.methuselah.securitylibrary.Data.Methuselah.ProfileInfoResponse;

public class MethuselahPrivate extends WebMethodCaller
{
	public static final String urlProfileInfo  = urlBase + "api/profile.php";
	public static final String urlListProjects = urlBase + "toolbox/listProjects.php";
	public static final String urlListClients  = urlBase + "toolbox/listProjectClients.php";
	public static final String urlListServers  = urlBase + "toolbox/listClientServers.php";
	public static ProfileInfoResponse profileInfo(ProfileInfoPayload payload) throws ResponseException
	{
		return action(urlProfileInfo, payload, ProfileInfoResponse.class);
	}
	public static LauncherAnswerProjects listProjects() throws ResponseException
	{
		return action(urlListProjects, null, LauncherAnswerProjects.class);
	}
	public static LauncherAnswerClients listProjectClients(LauncherMessageGetClients payload) throws ResponseException
	{
		return action(urlListClients, payload, LauncherAnswerClients.class);
	}
	public static LauncherAnswerServers listClientServers(LauncherMessageGetServers payload) throws ResponseException
	{
		return action(urlListServers, payload, LauncherAnswerServers.class);
	}
}
