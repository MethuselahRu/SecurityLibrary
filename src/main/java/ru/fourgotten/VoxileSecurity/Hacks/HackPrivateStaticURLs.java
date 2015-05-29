package ru.fourgotten.VoxileSecurity.Hacks;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import ru.methuselah.authlib.ReplacementListEntrySF;

public class HackPrivateStaticURLs
{
	public static int process(ReplacementListEntrySF[] tasks, ClassLoader loader)
	{
		int result = 0;
		for(ReplacementListEntrySF replaceTask : tasks)
			for(String prefix : ReplacementListEntrySF.possibleClassPrefixes)
				try
				{
					final Class targetClass = Class.forName(prefix + replaceTask.targetClass, true, loader);
					final Field targetField = targetClass.getDeclaredField(replaceTask.targetField);
					final Class fieldClass = targetField.getType();
					final boolean originalAccessible = targetField.isAccessible();
					targetField.setAccessible(true);
					final boolean originalFinal = (targetField.getModifiers() & Modifier.FINAL) != 0;
					final Field modifiersField = Field.class.getDeclaredField("modifiers");
					if(originalFinal)
					{
						modifiersField.setAccessible(true);
						modifiersField.setInt(targetField, targetField.getModifiers() & ~ Modifier.FINAL);
					}
					if(fieldClass.equals(URL.class))
						targetField.set(null, new URL(replaceTask.newValue));
					if(fieldClass.equals(String.class))
						targetField.set(null, replaceTask.newValue);
					if(originalFinal)
					{
						modifiersField.setInt(targetField, targetField.getModifiers() | Modifier.FINAL);
						modifiersField.setAccessible(false);
					}
					targetField.setAccessible(originalAccessible);
					result += 1;
				} catch(ClassNotFoundException ex) {
				} catch(NoSuchFieldException ex) {
				} catch(SecurityException ex) {
				} catch(MalformedURLException ex) {
				} catch(IllegalArgumentException ex) {
				} catch(IllegalAccessException ex) {
				}
		return result;
	}
}
