package rina.rocan;

// Minecraft.
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

// Mojang.
import com.mojang.realmsclient.gui.ChatFormatting;

// Event Manager.
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;
import team.stiff.pomelo.EventManager;

// Rocan Managers.
import rina.rocan.manager.RocanCommandManager;
import rina.rocan.manager.RocanSettingManager;
import rina.rocan.manager.RocanModuleManager;
import rina.rocan.manager.RocanEventManager;

@Mod(modid = "rocan", name = Rocan.ROCAN_NAME, version = Rocan.ROCAN_VERSION)
public class Rocan {
	// Details of the client.
	public final static String ROCAN_VERSION = "0.1";
	public final static String ROCAN_NAME    = "Rocan";

	@Mod.Instance
	private static Rocan INSTANCE;

	// Managers.
	private RocanModuleManager module_manager;
	private RocanSettingManager setting_manager;
	private RocanCommandManager command_manager;
	private RocanEventManager event_manager;

	// EventBus.
	private EventManager pomelo_event_manager;

	@Mod.EventHandler
	public void clientInitialize(FMLInitializationEvent event) {
		setting_manager      = new RocanSettingManager();
		module_manager       = new RocanModuleManager();
		command_manager      = new RocanCommandManager(".");
		event_manager        = new RocanEventManager();
		pomelo_event_manager = new AnnotatedEventManager();

		MinecraftForge.EVENT_BUS.register(command_manager);
		MinecraftForge.EVENT_BUS.register(event_manager);
	}

	public static Rocan getInstance() {
		return INSTANCE;
	}

	public static String getClientName() {
		return getInstance().ROCAN_NAME;
	}

	public static String getClientVersion() {
		return getInstance().ROCAN_VERSION;
	}

	public static RocanSettingManager getSettingManager() {
		return getInstance().setting_manager;
	}

	public static RocanCommandManager getCommandManager() {
		return getInstance().command_manager;
	}

	public static RocanModuleManager getModuleManager() {
		return getInstance().module_manager;
	}

	public static RocanEventManager getEventManager() {
		return getInstance().event_manager;
	}

	public static EventManager getPomeloEventManager() {
		return getInstance().pomelo_event_manager;
	}

	// Colors.
	public static ChatFormatting setDefaultColor() {
		return ChatFormatting.RESET;
	}

	public static ChatFormatting getBlackColor() {
		return ChatFormatting.BLACK;
	}

	public static ChatFormatting getRedColor() {
		return ChatFormatting.RED;
	}

	public static ChatFormatting getAquaColor() {
		return ChatFormatting.AQUA;
	}

	public static ChatFormatting getBlueColor() {
		return ChatFormatting.BLUE;
	}

	public static ChatFormatting getGoldColor() {
		return ChatFormatting.GOLD;
	}

	public static ChatFormatting getGrayColor() {
		return ChatFormatting.GRAY;
	}

	public static ChatFormatting getWhiteColor() {
		return ChatFormatting.WHITE;
	}

	public static ChatFormatting getGreenColor() {
		return ChatFormatting.GREEN;
	}

	public static ChatFormatting getYellowColor() {
		return ChatFormatting.YELLOW;
	}

	public static ChatFormatting getDarkRedColor() {
		return ChatFormatting.DARK_RED;
	}

	public static ChatFormatting getDarkAquaColor() {
		return ChatFormatting.DARK_AQUA;
	}

	public static ChatFormatting getDarkBlueColor() {
		return ChatFormatting.DARK_BLUE;
	}

	public static ChatFormatting getDarkGrayColor() {
		return ChatFormatting.DARK_GRAY;
	}

	public static ChatFormatting getDarkGreenColor() {
		return ChatFormatting.DARK_GREEN;
	}

	public static ChatFormatting getDarkPurpleColor() {
		return ChatFormatting.DARK_PURPLE;
	}

	public static ChatFormatting getLightPurpleColor() {
		return ChatFormatting.LIGHT_PURPLE;
	}
}