package ru.methuselah.securitylibrary.Data.Launcher;

public class LauncherAnswerDesign
{
	// Описание визуальных настроек по группам
	public static class Background
	{
		public int    сolorNormal;
		public int    сolorHovered;
		public int    сolorPressed;
		public int    сolorDisabled;
		public String pngHashNormal;
		public String pngHashHovered;
		public String pngHashPressed;
		public String pngHashDisabled;
	}
	public static class Position
	{
		public int xPosition;
		public int yPosition;
		public int xSize;
		public int ySize;
	}
	public static class Font
	{
		public static enum AlignH { left, center, right }
		public static enum AlignV { top, center, bottom }
		public AlignH alignh = AlignH.center;
		public AlignV alignv = AlignV.center;
		public String face;
		public float  weight;
		public int    сolor;
	}
	// Описание основных элементов управления
	public static class Container
	{
		public Background background = new Background();
		public Position   position   = new Position();
	}
	public static class TextField extends Container
	{
		public Font       font       = new Font();
	}
	public static class Button extends Container
	{
		public Font       font       = new Font();
		public String     text;
	}
	public static class Link extends Button
	{
		public String     linkURL;
	}
	public static class CheckBox extends Container
	{
		public Font       font       = new Font();
		public String     text;
	}
	public static class ComboBox
	{
		public Background background = new Background();
		public Position   position   = new Position();
		public Font       font       = new Font();
	}
	public static class PanelLogin extends Container
	{
		public final TextField login         = new TextField();
		public final TextField password      = new TextField();
		public final CheckBox  savePassword  = new CheckBox();
		public final CheckBox  autoAuthorize = new CheckBox();
		public final Button    authorize     = new Button();
		public final Button    guest         = new Button();
		public final Button    offline       = new Button();
	}
	public static class PanelClients extends Container
	{
		public final ComboBox  clients     = new ComboBox();
		public final CheckBox  autoConnect = new CheckBox();
		public final Button    connect     = new Button();
	}
	public static class PanelSettings extends Container
	{
		public final TextField memoryLimit = new TextField();
		public final Button    saveBtn     = new Button();
		public final Button    cancelBtn   = new Button();
	}
	public static class PanelOffline extends Container
	{
		public final TextField login        = new TextField();
		public final TextField password     = new TextField();
		public final CheckBox  savePassword = new CheckBox();
	}
	// Описание свойств главного окна
	public final Container     frameLauncherMain = new Container();
	// Описание панелей
	public final PanelLogin    panelLogin    = new PanelLogin();
	public final PanelClients  panelClients  = new PanelClients();
	public final PanelSettings panelSettings = new PanelSettings();
	public final PanelOffline  panelOffline  = new PanelOffline();
	// Пользовательские элементы
	public final Link[]        customElements = new Link[0];
}
