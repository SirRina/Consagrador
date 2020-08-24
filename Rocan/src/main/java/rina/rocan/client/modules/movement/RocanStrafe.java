package rina.rocan.client.modules.movement;

// Minecraft.
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.init.MobEffects;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Event.
import rina.rocan.event.player.RocanEventPlayerMove;

// Module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Turok.
import rina.turok.TurokString;

// Utils.
import rina.rocan.util.RocanUtilMath;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 16/08/2020. // 00:05 am
 *
 **/
@Define(name = "Strafe", tag = "Strafe", description = "Make you fast and stable.", category = Category.ROCAN_MOVEMENT)
public class RocanStrafe extends RocanModule {
	RocanSetting onground_move = createSetting(new String[] {"OnGround", "OnGround", "Make movement in ground"}, true);
	RocanSetting auto_jump     = createSetting(new String[] {"Auto Jump", "AutoJump", "Auto jump to strafe"}, false);
	RocanSetting effective     = createSetting(new String[] {"Effective", "Effective", "Effective"}, false);

	private double speed;

	@Override
	public void onRender() {
		double delta_x = mc.player.posX - mc.player.prevPosX;
		double detal_z = mc.player.posZ - mc.player.prevPosZ;

		float tick_rate = (mc.timer.tickLength / 1000.0f);

		TurokString.renderStringHUD("Strafe on - " + String.format("%.1f", (double) (MathHelper.sqrt(delta_x * delta_x + detal_z * detal_z) / tick_rate)) + "bp/s", 10, 10, 255, 255, 255, true, true);
	}

	@Listener
	public void playerMove(RocanEventPlayerMove event) {
		if (mc.player.isSneaking() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInLava() || mc.player.isInWater() || mc.player.capabilities.isFlying) {
			return;
		}

		if (!onground_move.getBoolean() && !auto_jump.getBoolean()) {
			if (mc.player.onGround) {
				return;
			}
		}

		if (effective.getBoolean()) {
			speed = 0.2873d;
		} else {
			speed = 0.2875d;
		}

		double[] player_movement = RocanUtilMath.transformStrafeMovement(mc.player);

		// player_movement[0] = Yaw; player_movement[1] = Pitch; player_movement[2] = Forward; player_movement[3] = Strafe;

		if (player_movement[2] == 0.0d && player_movement[3] == 0.0d) {
			event.setX(0.0d);
			event.setZ(0.0d);
		} else {
			if (auto_jump.getBoolean()) {
				multiplicJumpSpeed(event);
			} else {
				if (mc.gameSettings.keyBindJump.isKeyDown()) {
					multiplicJumpSpeed(event);
				}
			}

			event.setX((player_movement[2] * speed) * Math.cos(Math.toRadians((player_movement[0] + 90.0f))) + (player_movement[3] * speed) * Math.sin(Math.toRadians((player_movement[0] + 90.0f))));
			event.setZ((player_movement[2] * speed) * Math.sin(Math.toRadians((player_movement[0] + 90.0f))) - (player_movement[3] * speed) * Math.cos(Math.toRadians((player_movement[0] + 90.0f))));
		}
	}

	public void multiplicJumpSpeed(RocanEventPlayerMove event) {
		double new_motion_y = 0.40223128;

		if (mc.player.onGround) {
			event.setY(mc.player.motionY = new_motion_y);

			if (effective.getBoolean()) {
				speed *= 2.149;
			} else {
				speed *= 2.149;
			}
		}
	}
}