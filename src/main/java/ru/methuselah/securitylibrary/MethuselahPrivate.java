package ru.methuselah.securitylibrary;

import ru.methuselah.authlib.methods.ResponseException;
import ru.methuselah.authlib.methods.WebMethodCaller;
import ru.methuselah.securitylibrary.Data.MessagesLauncher.AnswerLauncherClients;
import ru.methuselah.securitylibrary.Data.MessagesLauncher.AnswerLauncherProjects;
import ru.methuselah.securitylibrary.Data.MessagesLauncher.MessageLauncherGetClients;
import ru.methuselah.securitylibrary.Data.Methuselah.ProfileInfoPayload;
import ru.methuselah.securitylibrary.Data.Methuselah.ProfileInfoResponse;

public class MethuselahPrivate extends WebMethodCaller
{
	public static final String urlProfileInfo  = urlBase + "api/profile.php";
	public static final String urlListProjects = urlBase + "toolbox/listProjects.php";
	public static final String urlListClients  = urlBase + "toolbox/listProjectClients.php";
	public static ProfileInfoResponse profileInfo(ProfileInfoPayload payload) throws ResponseException
	{
		return action(urlProfileInfo, payload, ProfileInfoResponse.class);
	}
	public static AnswerLauncherProjects listProjects() throws ResponseException
	{
		return action(urlListProjects, null, AnswerLauncherProjects.class);
	}
	public static AnswerLauncherClients listProjectClients(MessageLauncherGetClients payload) throws ResponseException
	{
		return action(urlListClients, payload, AnswerLauncherClients.class);
	}
}
