package ru.fourgotten.VoxileSecurity.Methuselah;

import ru.fourgotten.VoxileSecurity.Data.MessagesLauncher.AnswerLauncherClients;
import ru.fourgotten.VoxileSecurity.Data.MessagesLauncher.AnswerLauncherProjects;
import ru.fourgotten.VoxileSecurity.Data.MessagesLauncher.MessageLauncherGetClients;
import ru.fourgotten.VoxileSecurity.Data.Methuselah.ProfileInfoPayload;
import ru.fourgotten.VoxileSecurity.Data.Methuselah.ProfileInfoResponse;
import ru.methuselah.authlib.MethuselahMethods;
import ru.methuselah.authlib.exceptions.ResponseException;

public class MethuselahPrivate extends MethuselahMethods
{
	public static final String urlProfileInfo = urlBase + "api/profile.php";
	public static final String urlProjectList = urlBase + "toolbox/listProjects.php";
	public static final String urlClientList  = urlBase + "toolbox/listProjectClients.php";
	public static ProfileInfoResponse getProfileInfo(ProfileInfoPayload payload) throws ResponseException
	{
		return action(urlProfileInfo, payload, ProfileInfoResponse.class);
	}
	public static AnswerLauncherProjects getProjectList() throws ResponseException
	{
		return action(urlProjectList, null, AnswerLauncherProjects.class);
	}
	public static AnswerLauncherClients getProjectClients(MessageLauncherGetClients payload) throws ResponseException
	{
		return action(urlClientList, payload, AnswerLauncherClients.class);
	}
}
