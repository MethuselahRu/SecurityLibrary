package ru.methuselah.securitylibrary.Data.MessagesLauncher;

public class AnswerLauncherProjects
{
	public static final transient float maximumAvailableRating = 10.0f;
	public static class ProjectInfo
	{
		public String code                 = "MGMCV";
		public String caption              = "voxile.ru";
		public int    currentOnlinePlayers = 0;
		public int    currentMaxPlayers    = 1000;
		public float  averageOnlineTime    = 100.0f;
		public float  rating               = 10.0f;
	}
	public ProjectInfo[] projects;
	public final ProjectInfo findByCode(String projectCode)
	{
		if(projects == null || projectCode == null)
			return null;
		for(ProjectInfo project : projects)
			if(projectCode.equalsIgnoreCase(project.code))
				return project;
		return null;
	}
}
