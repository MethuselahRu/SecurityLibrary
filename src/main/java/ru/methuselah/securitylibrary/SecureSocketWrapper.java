package ru.methuselah.securitylibrary;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class SecureSocketWrapper implements Closeable
{
	private final Socket socket;
	private final BufferedReader br;
	private final BufferedWriter bw;
	private final JsonReader jr;
	private final JsonWriter jw;
	private final Gson gson = new Gson();
	public SecureSocketWrapper(Socket socket) throws UnsupportedEncodingException, IOException
	{
		this.socket = socket;
		final InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
		final OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
		this.br = new BufferedReader(isr);
		this.bw = new BufferedWriter(osw);
		this.jr = new JsonReader(br);
		this.jw = new JsonWriter(bw);
	}
	public synchronized Socket getSocket()
	{
		return this.socket;
	}
	@Override
	public synchronized void close()
	{
		try
		{
			if(jr != null)
				jr.close();
		} catch(IOException ex) {
		}
		try
		{
			if(jw != null)
				jw.close();
		} catch(IOException ex) {
		}
		try
		{
			if(br != null)
				br.close();
		} catch(IOException ex) {
		}
		try
		{
			if(bw != null)
				bw.close();
		} catch(IOException ex) {
		}
		try
		{
			if(socket != null)
			{
				socket.getInputStream().close();
				socket.getOutputStream().close();
				socket.close();
			}
		} catch(IOException ex) {
		}
	}
	public synchronized boolean writeLine(String line)
	{
		try
		{
			bw.write(line + '\n');
			bw.flush();
		} catch(IOException ex) {
			return false;
		}
		return true;
	}
	public synchronized String readLine()
	{
		try
		{
			return br.readLine();
		} catch(IOException ex) {
			return null;
		}
	}
	public synchronized boolean writeObject(Object object, Class cls)
	{
		try
		{
			gson.toJson(object, cls, jw);
			jw.flush();
			return true;
		} catch(IOException ex) {
		} catch(JsonIOException  ex) {
		}
		return false;
	}
	public synchronized <T> T readObject(Class cls)
	{
		try
		{
			return (T)gson.fromJson(jr, cls);
		} catch(JsonIOException ex) {
		} catch(JsonSyntaxException ex) {
		}
		return null;
	}
}
