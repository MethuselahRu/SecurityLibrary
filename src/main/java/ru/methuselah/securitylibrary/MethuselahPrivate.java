package ru.methuselah.securitylibrary;

import ru.methuselah.authlib.links.Links;
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
	private final String urlProfileInfo;
	private final String urlListProjects;
	private final String urlListClients;
	private final String urlListServers;
	public MethuselahPrivate(Links links) throws IllegalArgumentException
	{
		super(links);
		final String urlBase = links.getBaseURL();
		urlProfileInfo  = urlBase + "/api/profile.php";
		urlListProjects = urlBase + "/toolbox/listProjects.php";
		urlListClients  = urlBase + "/toolbox/listProjectClients.php";
		urlListServers  = urlBase + "/toolbox/listClientServers.php";
	}
	public ProfileInfoResponse profileInfo(ProfileInfoPayload payload) throws ResponseException
	{
		return webExecute(urlProfileInfo, payload, ProfileInfoResponse.class);
	}
	public LauncherAnswerProjects listProjects() throws ResponseException
	{
		return webExecute(urlListProjects, null, LauncherAnswerProjects.class);
	}
	public LauncherAnswerClients listProjectClients(LauncherMessageGetClients payload) throws ResponseException
	{
		return webExecute(urlListClients, payload, LauncherAnswerClients.class);
	}
	public LauncherAnswerServers listClientServers(LauncherMessageGetServers payload) throws ResponseException
	{
		return webExecute(urlListServers, payload, LauncherAnswerServers.class);
	}
}
