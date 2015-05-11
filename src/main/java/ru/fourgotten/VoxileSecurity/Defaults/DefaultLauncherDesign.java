package ru.fourgotten.VoxileSecurity.Defaults;
import java.awt.Color;
import ru.fourgotten.VoxileSecurity.Data.MessagesLauncher.AnswerLauncherDesign;

public class DefaultLauncherDesign
{
	public static AnswerLauncherDesign getDefaultVoxileDesign()
	{
		final AnswerLauncherDesign result = new AnswerLauncherDesign();
		// Main frame
		result.frameLauncherMain.background.pngHashNormal = "hash hash, hash hash";
		result.frameLauncherMain.background.сolorNormal = new Color(0xFF, 0xFF, 0xFF, 0x80).getRGB();
		return result;
	}
}