package ru.methuselah.securitylibrary.Hacks;

import com.mojang.authlib.properties.Property;
import ru.methuselah.authlib.links.GlobalReplacementList;
import ru.methuselah.authlib.links.LinksMethuselah;
import ru.methuselah.authlib.methods.WebMethodCaller;
import ru.methuselah.securitylibrary.Data.MessagesWrapper.MessageWrappedGame;

public final class HacksApplicator
{
	public static void process(GlobalReplacementList grl, ClassLoader classLoader)
	{
		if(grl == null)
		{
			System.out.println("[Methuselah] Links provider not set, using Methuselah's.");
			grl = new LinksMethuselah().buildReplacements();
		}
		if(classLoader == null)
			classLoader = HacksApplicator.class.getClassLoader();
		// Замена класса Свойства на свой с вырезанной проверкой цифровой подписи
		Property.class.getCanonicalName();
		// Обманка для подключения по https к серверу с косяченным сертификатом
		WebMethodCaller.hackSSL();
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
