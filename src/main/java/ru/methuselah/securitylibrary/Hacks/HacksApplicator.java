package ru.methuselah.securitylibrary.Hacks;

import com.mojang.authlib.properties.Property;
import ru.methuselah.securitylibrary.Data.MessagesWrapper.MessageWrappedGame;
import ru.methuselah.authlib.GlobalReplacementList;
import ru.methuselah.authlib.MethuselahMethods;

public final class HacksApplicator
{
	public static void process(GlobalReplacementList grl, ClassLoader classLoader)
	{
		if(grl == null)
			grl = new GlobalReplacementList();
		if(classLoader == null)
			classLoader = HacksApplicator.class.getClassLoader();
		// Замена класса Свойства на свой с вырезанной проверкой цифровой подписи
		Property.class.getCanonicalName();
		// Обманка для подключения по https к серверу с косяченным сертификатом
		MethuselahMethods.hackSSL();
		// Замена статических полей с URL скриптов
		HackPrivateStaticURLs.process(grl.replacementsSF, classLoader);
		// Внедрение подменщика URL адресов, для которых устанавливается соединение
		HackStreamHandler.process(grl.replacementsSH, classLoader);
	}
	public static void process(MessageWrappedGame message, ClassLoader classLoader)
	{
		// Основные внедрения
		process(message.replacements, classLoader);
		// Замена пути к natives
		HackClassPath.updateNativesPath(message.nativesDir);
		// Замена Class Path
		HackClassPath.setFakeClassPath(message.libraries);
	}
}
