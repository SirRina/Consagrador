package rina.turok;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;

// OpenGL.
import org.lwjgl.opengl.GL11;

// Java.
import java.util.*;

/**
 * @author Rina!
 *
 * Created by Rina in 27/07/2020.
 *
 **/
public class TurokRenderGL {
	public static void color(int r, int g, int b, int a) {
		GL11.glColor4f((float) r / 255, (float) g / 255, (float) b / 255, (float) a / 255);
	}

	public static void color(int r, int g, int b) {
		GL11.glColor3f((float) r / 255, (float) g / 255, (float) b / 255);
	}

	public static void drawArc(float cx, float cy, float r, float start_angle, float end_angle, float num_segments) {
		GL11.glBegin(GL11.GL_TRIANGLES);

		for (int i = (int) (num_segments / (360 / start_angle)) + 1; i <= num_segments / (360 / end_angle); i++) {
			double previousangle = 2 * Math.PI * (i - 1) / num_segments;
			double angle         = 2 * Math.PI * i / num_segments;

			GL11.glVertex2d(cx, cy);
			GL11.glVertex2d(cx + Math.cos(angle) * r, cy + Math.sin(angle) * r);
			GL11.glVertex2d(cx + Math.cos(previousangle) * r, cy + Math.sin(previousangle) * r);
		}

		GL11.glEnd();
	}

	public static void drawArc(float x, float y, float radius) {
		drawArc(x, y, radius, 0, 360, 40);
	}

	public static void drawArcOutline(float cx, float cy, float r, float start_angle, float end_angle, float num_segments) {
		GL11.glBegin(GL11.GL_LINE_LOOP);

		for (int i = (int) (num_segments / (360 / start_angle)) + 1; i <= num_segments / (360 / end_angle); i++) {
			double angle = 2 * Math.PI * i / num_segments;

			GL11.glVertex2d(cx + Math.cos(angle) * r, cy + Math.sin(angle) * r);
		}

		GL11.glEnd();
	}

	public static void drawArcOutline(float x, float y, float radius) {
		drawArcOutline(x, y, radius, 0, 360, 40);
	}

	public static void drawOutlineRect(float x, float y, float width, float height) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glLineWidth(2f);

		GL11.glBegin(GL11.GL_LINE_LOOP);
		{
			GL11.glVertex2d(width, y);
			GL11.glVertex2d(x, y);
			GL11.glVertex2d(x, height);
			GL11.glVertex2d(width, height);
		}

		GL11.glEnd();
	}

	public static void drawOutlineRoundedRect(float x, float y, float width, float height, float radius, float dR, float dG, float dB, float dA, float line_width) {
		drawRoundedRect(x, y, width, height, radius);

		GL11.glColor4f(dR, dG, dB, dA);

		drawRoundedRect(x + line_width, y + line_width, width - line_width * 2, height - line_width * 2, radius);
	}

	public static void drawRoundedRect(float x, float y, float width, float height, float radius) {
		GL11.glEnable(GL11.GL_BLEND);

		drawArc((x + width - radius), (y + height - radius), radius, 0, 90, 16);
		drawArc((x + radius), (y + height - radius), radius, 90, 180, 16);
		drawArc(x + radius, y + radius, radius, 180, 270, 16);
		drawArc((x + width - radius), (y + radius), radius, 270, 360, 16);

		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			GL11.glVertex2d(x + width - radius, y);
			GL11.glVertex2d(x + radius, y);

			GL11.glVertex2d(x + width - radius, y+radius);
			GL11.glVertex2d(x + width - radius, y+radius);

			GL11.glVertex2d(x + radius, y);
			GL11.glVertex2d(x + radius, y+radius);

			GL11.glVertex2d(x + width, y + radius);
			GL11.glVertex2d(x, y + radius);

			GL11.glVertex2d(x, y + height - radius);
			GL11.glVertex2d(x + width, y + radius);

			GL11.glVertex2d(x, y + height-radius);
			GL11.glVertex2d(x + width, y + height - radius);

			GL11.glVertex2d(x + width - radius, y + height - radius);
			GL11.glVertex2d(x + radius, y + height - radius);

			GL11.glVertex2d(x + width - radius, y + height);
			GL11.glVertex2d(x + width - radius, y + height);

			GL11.glVertex2d(x + radius, y + height - radius);
			GL11.glVertex2d(x + radius, y + height);
		}

		GL11.glEnd();
	}

	public static void drawRoundedRect(TurokRect rect, float size) {
		drawRoundedRect((float) rect.getX(), (float) rect.getY(), (float) rect.getWidth(), (float) rect.getHeight(), size);	
	}

	public static void drawOutlineRect(int x, int y, int width, int height) {
		drawOutlineRect((float) x, (float) y, (float) width, (float) height);
	}

	public static void drawOutlineRect(TurokRect rect) {
		drawOutlineRect((float) rect.getX(), (float) rect.getY(), (float) (rect.getX() + rect.getWidth()), (float) (rect.getY() + rect.getHeight()));
	}

	public static void drawSolidRect(float x, float y, float width, float height) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2d(width, y);
			GL11.glVertex2d(x, y);
			GL11.glVertex2d(x, height);
			GL11.glVertex2d(width, height);
		}

		GL11.glEnd();
	}

	public static void drawSolidRect(int x, int y, int width, int height) {
		drawSolidRect((float) x, (float) y, (float) width, (float) height);
	}

	public static void drawSolidRect(TurokRect rect) {
		drawSolidRect((float) rect.getX(), (float) rect.getY(), (float) (rect.getX() + rect.getWidth()), (float) (rect.getY() + rect.getHeight()));
	}

	public static void prepareToRenderString() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
	}

	public static void releaseRenderString() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public static void fixScreen(float scaled_width, float scaled_height) {
		GL11.glPushMatrix();
		GL11.glTranslated(scaled_width, scaled_height, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glPopMatrix();
	}

	public static void init2D() {
		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GlStateManager.enableBlend();

		GL11.glPopMatrix();
	}

	public static void release2D() {
		GlStateManager.enableCull();
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableDepth();
	}
}