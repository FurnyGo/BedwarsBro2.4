// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.dimchig.bedwarsbro.CustomScoreboard;
import java.util.Iterator;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityPlayerSP;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import com.dimchig.bedwarsbro.hints.InvulnerableTime;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class GuiPlayerFocus
{
    static Minecraft mc;
    public static boolean STATE;
    public static boolean isAutoWaterDropActive;
    public static boolean isInvulnerableTimerActive;
    public static boolean isInvulnerableTimerSoundsActive;
    public static boolean isNamePlateActive;
    public static boolean isNamePlateRainbowActive;
    public static boolean isNamePlateTeamColorActive;
    public static boolean isResourcesHologramActive;
    public static int rainbowSpeed;
    public static String namePlateCustomColor;
    public static boolean isT_Active;
    public static boolean isLookAtBaseActive;
    private ArrayList<MyLine> lines;
    private static boolean cflag;
    private static ArrayList<Long> cps;
    
    public GuiPlayerFocus() {
        GuiPlayerFocus.mc = Minecraft.func_71410_x();
        this.lines = new ArrayList<MyLine>();
        this.updateBooleans();
    }
    
    public void updateBooleans() {
        GuiPlayerFocus.isAutoWaterDropActive = HintsValidator.isAutoWaterDropActive();
        GuiPlayerFocus.isInvulnerableTimerActive = HintsValidator.isInvulnerableTimerActive();
        GuiPlayerFocus.isInvulnerableTimerSoundsActive = HintsValidator.isInvulnerableTimerSoundsActive();
        GuiPlayerFocus.isNamePlateActive = HintsValidator.isNamePlateActive();
        GuiPlayerFocus.isNamePlateRainbowActive = HintsValidator.isNamePlateRainbowActive();
        GuiPlayerFocus.rainbowSpeed = HintsValidator.getRainbowSpeed();
        GuiPlayerFocus.namePlateCustomColor = HintsValidator.getNamePlateCustomColor();
        GuiPlayerFocus.isNamePlateTeamColorActive = HintsValidator.getNamePlateTeamColor();
        GuiPlayerFocus.isResourcesHologramActive = HintsValidator.isResourcesHologramActive();
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event) {
        final float partialTicks = event.partialTicks;
        final EntityPlayerSP player = GuiPlayerFocus.mc.field_71439_g;
        final World world = (World)GuiPlayerFocus.mc.field_71441_e;
        if (player == null || world == null) {
            return;
        }
        final double d0 = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * partialTicks;
        final double d2 = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * partialTicks;
        final double d3 = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * partialTicks;
        final Vec3 pos = new Vec3(d0, d2, d3);
        if (GuiPlayerFocus.isAutoWaterDropActive) {
            Main.autoWaterDrop.check(player, pos);
        }
        if (GuiPlayerFocus.isInvulnerableTimerActive && MyChatListener.IS_IN_GAME) {
            final InvulnerableTime invulnerableTime = Main.invulnerableTime;
            InvulnerableTime.scan(world.field_73010_i, pos, partialTicks, GuiPlayerFocus.isInvulnerableTimerSoundsActive);
        }
        if (GuiPlayerFocus.isInvulnerableTimerActive && MyChatListener.IS_IN_GAME) {
            final InvulnerableTime invulnerableTime2 = Main.invulnerableTime;
            InvulnerableTime.scan(world.field_73010_i, pos, partialTicks, GuiPlayerFocus.isInvulnerableTimerSoundsActive);
        }
        if (GuiPlayerFocus.isNamePlateActive) {
            Main.namePlate.draw(pos, GuiPlayerFocus.isNamePlateRainbowActive, GuiPlayerFocus.isNamePlateTeamColorActive, GuiPlayerFocus.rainbowSpeed, GuiPlayerFocus.namePlateCustomColor);
        }
        if (GuiPlayerFocus.isLookAtBaseActive) {
            final MyChatListener chatListener = Main.chatListener;
            if (MyChatListener.GAME_BED == null) {
                GuiPlayerFocus.mc.field_71439_g.func_70082_c(GuiPlayerFocus.mc.field_71439_g.field_71109_bG - 360.0f + GuiPlayerFocus.mc.field_71441_e.field_73012_v.nextInt(720), GuiPlayerFocus.mc.field_71439_g.field_70726_aT);
            }
            else {
                final double x1 = d0;
                final double y1 = d2 + player.func_70047_e();
                final double z1 = d3;
                final MyChatListener chatListener2 = Main.chatListener;
                final double x2 = MyChatListener.GAME_BED.part1_posX;
                final MyChatListener chatListener3 = Main.chatListener;
                final double y2 = MyChatListener.GAME_BED.part1_posY;
                final MyChatListener chatListener4 = Main.chatListener;
                HintsFinder.lookAtPlayer(x1, y1, z1, x2, y2, MyChatListener.GAME_BED.part1_posZ);
            }
        }
        Main.rotateBind.rotate();
        if (GuiPlayerFocus.isResourcesHologramActive) {
            Main.guiResourceHologram.draw(pos);
        }
        Main.tntjump.draw(pos, partialTicks);
        if (!GuiPlayerFocus.STATE) {
            return;
        }
        if (Minecraft.func_71410_x().field_71474_y.field_152399_aq.func_151468_f()) {
            GuiPlayerFocus.isT_Active = !GuiPlayerFocus.isT_Active;
            final MyChatListener chatListener5 = Main.chatListener;
            String name;
            if (GuiPlayerFocus.isT_Active) {
                final MyChatListener chatListener6 = Main.chatListener;
                name = MyChatListener.SOUND_PARTY_CHAT;
            }
            else {
                final MyChatListener chatListener7 = Main.chatListener;
                name = MyChatListener.SOUND_REJECT;
            }
            MyChatListener.playSound(name);
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        GL11.glTranslated(-pos.field_72450_a, -pos.field_72448_b, -pos.field_72449_c);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GlStateManager.func_179084_k();
        GL11.glLineWidth(1.0f);
        final List<EntityPlayer> entities = (List<EntityPlayer>)Minecraft.func_71410_x().field_71441_e.field_73010_i;
        final double playerSpeedY = GuiPlayerFocus.mc.field_71439_g.field_70163_u - GuiPlayerFocus.mc.field_71439_g.field_70167_r;
        for (final EntityPlayer en : entities) {
            if (en == null) {
                continue;
            }
            if (en == player) {
                continue;
            }
            if (en.func_96124_cp() == player.func_96124_cp()) {
                continue;
            }
            final double posX = en.field_70169_q + (en.field_70165_t - en.field_70169_q) * partialTicks;
            final double posY = en.field_70167_r + (en.field_70163_u - en.field_70167_r) * partialTicks;
            final double posZ = en.field_70166_s + (en.field_70161_v - en.field_70166_s) * partialTicks;
            final double posX2 = posX - en.field_70130_N / 2.0f;
            final double posZ2 = posZ - en.field_70130_N / 2.0f;
            final double posX3 = posX + en.field_70130_N / 2.0f;
            final double posZ3 = posZ + en.field_70130_N / 2.0f;
            final CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor(en);
            this.setLineColor(this.getColorByTeam(team_color));
            this.drawBox(posX2, posY, posZ2, posX3, posY + en.field_70131_O, posZ3);
            final double enSpeedY = en.field_70163_u - en.field_70167_r;
            if (!GuiPlayerFocus.isT_Active || enSpeedY <= -1.0 || playerSpeedY <= -1.0) {
                continue;
            }
            final Vec3 head_pos = new Vec3(d0, d2 + player.func_70047_e(), d3);
            drawLineWithGL(head_pos, new Vec3(posX, posY + en.eyeHeight, posZ));
        }
        if (this.lines.size() > 0) {
            for (final MyLine l : this.lines) {
                Vec3 p1 = l.pos1;
                if (p1 == null) {
                    p1 = new Vec3(d0, d2 + player.func_70047_e(), d3);
                }
                this.setLineColor(l.color);
                drawLineWithGL(p1, l.pos2);
            }
        }
        this.setLineColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public Color getColorByTeam(final CustomScoreboard.TEAM_COLOR team_color) {
        Color color = new Color(0.0f, 0.0f, 0.0f, 1.0f);
        if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
            color = new Color(1.0f, 0.0f, 0.0f, 1.0f);
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
            color = new Color(1.0f, 1.0f, 0.0f, 1.0f);
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
            color = new Color(0.0f, 1.0f, 0.0f, 1.0f);
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
            color = new Color(0.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
            color = new Color(0.0f, 0.0f, 1.0f, 1.0f);
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
            color = new Color(1.0f, 0.0f, 1.0f, 1.0f);
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
            color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
            color = new Color(0.6f, 0.6f, 0.6f, 1.0f);
        }
        return color;
    }
    
    private void setLineColor(final Color color) {
        GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 1.0f);
    }
    
    public void addLine(final Vec3 pos1, final Vec3 pos2, final Color color) {
        this.lines.add(new MyLine(pos1, pos2, color));
    }
    
    public void clearLines() {
        this.lines.clear();
    }
    
    public void drawFilledSquare(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glEnd();
    }
    
    public void drawFilledBox(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x1, y2, z1);
        GL11.glEnd();
    }
    
    public void drawBox(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        drawLineWithGL(new Vec3(x1, y1, z1), new Vec3(x2, y1, z1));
        drawLineWithGL(new Vec3(x2, y1, z1), new Vec3(x2, y1, z2));
        drawLineWithGL(new Vec3(x2, y1, z2), new Vec3(x1, y1, z2));
        drawLineWithGL(new Vec3(x1, y1, z2), new Vec3(x1, y1, z1));
        drawLineWithGL(new Vec3(x1, y2, z1), new Vec3(x2, y2, z1));
        drawLineWithGL(new Vec3(x2, y2, z1), new Vec3(x2, y2, z2));
        drawLineWithGL(new Vec3(x2, y2, z2), new Vec3(x1, y2, z2));
        drawLineWithGL(new Vec3(x1, y2, z2), new Vec3(x1, y2, z1));
        drawLineWithGL(new Vec3(x1, y1, z1), new Vec3(x1, y2, z1));
        drawLineWithGL(new Vec3(x1, y1, z2), new Vec3(x1, y2, z2));
        drawLineWithGL(new Vec3(x2, y1, z1), new Vec3(x2, y2, z1));
        drawLineWithGL(new Vec3(x2, y1, z2), new Vec3(x2, y2, z2));
    }
    
    public void drawBoxAroundBlock(final double x, final double y, final double z, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        this.drawFilledBox(x + x1, y + y1, z + z1, x + x2 - 1.0, y + y2 - 1.0, z + z2 - 1.0);
    }
    
    public static void drawLineWithGL(final Vec3 blockA, final Vec3 blockB) {
        GL11.glBegin(3);
        GL11.glVertex3d(blockA.field_72450_a, blockA.field_72448_b, blockA.field_72449_c);
        GL11.glVertex3d(blockB.field_72450_a, blockB.field_72448_b, blockB.field_72449_c);
        GL11.glEnd();
    }
    
    static {
        GuiPlayerFocus.STATE = false;
        GuiPlayerFocus.isAutoWaterDropActive = false;
        GuiPlayerFocus.isInvulnerableTimerActive = false;
        GuiPlayerFocus.isInvulnerableTimerSoundsActive = false;
        GuiPlayerFocus.isNamePlateActive = false;
        GuiPlayerFocus.isNamePlateRainbowActive = false;
        GuiPlayerFocus.isNamePlateTeamColorActive = false;
        GuiPlayerFocus.isResourcesHologramActive = false;
        GuiPlayerFocus.rainbowSpeed = 1;
        GuiPlayerFocus.namePlateCustomColor = "";
        GuiPlayerFocus.isT_Active = false;
        GuiPlayerFocus.isLookAtBaseActive = false;
        GuiPlayerFocus.cflag = false;
        GuiPlayerFocus.cps = new ArrayList<Long>();
    }
    
    private class MyLine
    {
        public Vec3 pos1;
        public Vec3 pos2;
        public Color color;
        
        public MyLine(final Vec3 pos1, final Vec3 pos2, final Color color) {
            this.pos1 = pos1;
            this.pos2 = pos2;
            this.color = color;
        }
    }
}
