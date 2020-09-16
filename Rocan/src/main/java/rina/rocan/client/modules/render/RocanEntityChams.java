package rina.rocan.client.modules.render;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.init.Items;

// OpenGL.
import org.lwjgl.opengl.GL11;

// Java.
import java.awt.*;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Turok.
import rina.turok.TurokRenderHelp;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Util.
import rina.rocan.util.RocanUtilRendererEntity2D3D;

// Event.
import rina.rocan.event.render.RocanEventRenderEntity;
import rina.rocan.event.render.RocanEventRender;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 07/09/2020.
 *
 **/
public class RocanEntityChams extends RocanModule {
	// Settings range, minimum, modes...
	RocanSetting range                = createSetting(new String[] {"Range", "EntityChamsRange", "Area range to able render."}, 200, 0, 200);
	RocanSetting range_to_stop_render = createSetting(new String[] {"Range To Stop Render", "EntityChamsRangeToStopRender", "Offset to stop render."}, 6, 0, 10);

	// Entities.
	RocanSetting render_entity_player      = createSetting(new String[] {"Player", "EntityChamsRenderEntityPlayer", "Enable entity to render."}, false);
	RocanSetting render_entity_enemy       = createSetting(new String[] {"Enemy", "EntityChamsRenderEntityEnemy", "Enable entity to render."}, false);
	RocanSetting render_entity_friend      = createSetting(new String[] {"Friend", "EntityChamsRenderEntityFriend", "Enable entity to render."}, true);
	RocanSetting render_entity_hostile     = createSetting(new String[] {"Hostile", "EntityChamsRenderEntityHostile", "Enable entity to render."}, false);
	RocanSetting render_entity_animals     = createSetting(new String[] {"Animals & Pigs", "EntityChamsRenderEntityAnimals", "Enable entity to render."}, false);
	RocanSetting render_entity_end_crystal = createSetting(new String[] {"End Crystal", "EntityChamsRenderEntityEndCrystal", "Enable entity to render."}, false);
	RocanSetting render_entity_drop_item   = createSetting(new String[] {"Drop Item", "EntityChamsRenderEntityDropItem", "Enable entity to render."}, false);

	public RocanEntityChams() {
		super(new String[] {"Entity Chams", "EntityChams", "Draw entities in walls."}, Category.ROCAN_RENDER);
	}

	@Listener
	public void onRenderEntity(RocanEventRenderEntity event) {
		if (mc.player == null) {
			return;
		}

		if (mc.player.getDistance(event.getEntity()) > range.getInteger() || mc.player.getDistance(event.getEntity()) < range_to_stop_render.getInteger()) {
			return;
		}

		if (event.getStage() == RocanEventRenderEntity.EventStage.PRE) {
			if (verifyRender(event.getEntity())) {
				GlStateManager.pushMatrix();

				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

				GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);

				GL11.glPolygonOffset(1.0f, -1100000.0f);

				GlStateManager.popMatrix();
			}
		} else if (event.getStage() == RocanEventRenderEntity.EventStage.POST) {
			if (verifyRender(event.getEntity())) {
				GlStateManager.pushMatrix();

				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

				GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);

				GL11.glPolygonOffset(1.0f, -1100000.0f);

				GlStateManager.popMatrix();
			}
		}
	}

	public boolean verifyRender(Entity entity) {
		if (entity instanceof EntityLivingBase && entity instanceof EntityPlayer && (render_entity_player.getBoolean() || (render_entity_enemy.getBoolean() && Rocan.getFriendManager().isEnemy(entity.getName())) || (render_entity_friend.getBoolean() && Rocan.getFriendManager().isFriend(entity.getName())))) {
			return true;
		}

		if (entity instanceof EntityLivingBase && entity instanceof IMob && render_entity_hostile.getBoolean()) {
			return true;
		}

		if (entity instanceof EntityLivingBase && entity instanceof IAnimals && render_entity_animals.getBoolean()) {
			return true;
		}

		if (entity instanceof EntityEnderCrystal && render_entity_end_crystal.getBoolean()) {
			return true;
		}

		if (entity instanceof EntityItem && render_entity_drop_item.getBoolean()) {
			return true;
		}

		return false;
	}
}