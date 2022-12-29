// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.gui;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.model.IBakedModel;
import com.dimchig.bedwarsbro.hints.LobbyFly;
import com.dimchig.bedwarsbro.ColorCodesManager;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import com.dimchig.bedwarsbro.hints.LightningLocator;
import java.util.Date;
import java.awt.Color;
import com.dimchig.bedwarsbro.hints.BWItemsHandler;
import net.minecraft.potion.PotionEffect;
import java.util.ArrayList;
import com.dimchig.bedwarsbro.OnMyTickEvent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.GlStateManager;
import com.dimchig.bedwarsbro.CustomScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import com.dimchig.bedwarsbro.MyChatListener;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.client.gui.ScaledResolution;
import com.dimchig.bedwarsbro.hints.HintsValidator;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.client.settings.KeyBinding;
import com.dimchig.bedwarsbro.hints.GeneratorTimers;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import java.text.DecimalFormat;
import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.gui.Gui;

public class GuiOnScreen extends Gui
{
    private Main asInstance;
    private Minecraft mc;
    private Field renderEventTypeField;
    private Field configChangedEventModIDField;
    private final DecimalFormat timeFormatter;
    private MyItem item_diamond;
    private MyItem item_emerald;
    private MyItem item_wool;
    private boolean isPotionEffectTrackerActive;
    private boolean isPotionEffectTrackerSoundsActive;
    private boolean isBlocksCounterActive;
    public int COUNT_EMERALDS;
    public int COUNT_DIAMONDS;
    public int COUNT_BLOCKS;
    private ResourceLocation resourceLoc_potions;
    private ResourceLocation resourceLoc_other;
    private TextureManager textureManager;
    private GuiMinimap minimap;
    private GeneratorTimers generatorTimers;
    private KeyBinding keyTab;
    long lastPlaySoundTime;
    
    public void setDiamonds(final int x) {
        this.COUNT_DIAMONDS = x;
    }
    
    public void setEmeralds(final int x) {
        this.COUNT_EMERALDS = x;
    }
    
    public void setBlocks(final int x) {
        this.COUNT_BLOCKS = x;
    }
    
    public GuiOnScreen(final Main asInstance) {
        this.timeFormatter = new DecimalFormat("0.0");
        this.isPotionEffectTrackerActive = false;
        this.isPotionEffectTrackerSoundsActive = false;
        this.isBlocksCounterActive = false;
        this.COUNT_EMERALDS = 0;
        this.COUNT_DIAMONDS = 0;
        this.COUNT_BLOCKS = 0;
        this.resourceLoc_potions = new ResourceLocation("bedwarsbro:textures/gui/potions.png");
        this.resourceLoc_other = new ResourceLocation("bedwarsbro:textures/gui/other.png");
        this.lastPlaySoundTime = 0L;
        this.asInstance = asInstance;
        this.mc = Minecraft.func_71410_x();
        this.minimap = Main.minimap;
        this.generatorTimers = Main.generatorTimers;
        this.item_diamond = new MyItem(-2, -2, 12, 13, Items.field_151045_i);
        this.item_emerald = new MyItem(-3, -1, 11, 14, Items.field_151166_bC);
        this.item_wool = new MyItem(-1, 0, 14, 16, Item.func_150898_a(Blocks.field_150325_L));
        this.textureManager = this.mc.func_110434_K();
        this.updateBooleans();
        this.keyTab = this.mc.field_71474_y.field_74321_H;
        try {
            (this.renderEventTypeField = RenderGameOverlayEvent.class.getDeclaredField("type")).setAccessible(true);
            (this.configChangedEventModIDField = ConfigChangedEvent.class.getDeclaredField("modID")).setAccessible(true);
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException("Cannot find field", e);
        }
    }
    
    public void updateBooleans() {
        this.isPotionEffectTrackerActive = HintsValidator.isPotionEffectsTrackerActive();
        this.isPotionEffectTrackerSoundsActive = HintsValidator.isPotionEffectsTrackerSoundsActive();
        this.isBlocksCounterActive = Main.getConfigBool(Main.CONFIG_MSG.ITEM_COUNTER_BLOCKS);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent event) throws IllegalAccessException {
        final boolean isF3 = this.mc.field_71474_y.field_74330_P;
        if (this.renderEventTypeField.get(event) == RenderGameOverlayEvent.ElementType.TEXT) {
            final ScaledResolution sr = new ScaledResolution(this.mc);
            final int screen_width = sr.func_78326_a();
            final int screen_height = sr.func_78328_b();
            if (!isF3 && this.minimap != null) {
                this.minimap.draw(this.mc);
            }
            if (!isF3 && this.generatorTimers != null) {
                this.generatorTimers.draw(screen_width, screen_height);
            }
            int block_counter_size_x = 0;
            if (this.isBlocksCounterActive && this.item_wool != null && !isF3) {
                String text_blocks = "" + this.COUNT_BLOCKS;
                if (this.COUNT_BLOCKS > 64) {
                    text_blocks = "" + this.timeFormatter.format(this.COUNT_BLOCKS / 64.0f);
                }
                if (this.COUNT_BLOCKS > 320) {
                    text_blocks = "" + this.COUNT_BLOCKS / 64;
                }
                final int color_blocks = this.getColor("ffffffff");
                if (this.COUNT_BLOCKS > 0) {
                    block_counter_size_x += this.mc.field_71466_p.func_78256_a(text_blocks);
                    final int offsetY = 5;
                    final int topX = screen_width - block_counter_size_x - this.item_wool.width;
                    final int topY = screen_height - offsetY - this.item_wool.height;
                    final int bottomX = screen_width - block_counter_size_x;
                    final int bottomY = screen_height - offsetY;
                    final EnumDyeColor block_color = EnumDyeColor.WHITE;
                    final MyChatListener chatListener = Main.chatListener;
                    final CustomScoreboard.TEAM_COLOR team_color = MyChatListener.getEntityTeamColor((EntityPlayer)this.mc.field_71439_g);
                    int meta = 0;
                    if (team_color == CustomScoreboard.TEAM_COLOR.RED) {
                        meta = 14;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.YELLOW) {
                        meta = 4;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.GREEN) {
                        meta = 5;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.AQUA) {
                        meta = 3;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.BLUE) {
                        meta = 11;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.PINK) {
                        meta = 6;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.WHITE) {
                        meta = 0;
                    }
                    else if (team_color == CustomScoreboard.TEAM_COLOR.GRAY) {
                        meta = 8;
                    }
                    this.item_wool.stack.func_77964_b(meta);
                    this.mc.field_71466_p.func_175065_a(text_blocks, (float)(bottomX - 1), (float)(bottomY - 4), color_blocks, true);
                    IBakedModel ibakedmodel = this.mc.func_175599_af().func_175037_a().func_178089_a(this.item_wool.stack);
                    GlStateManager.func_179094_E();
                    this.textureManager.func_110577_a(TextureMap.field_110575_b);
                    this.textureManager.func_110581_b(TextureMap.field_110575_b).func_174936_b(false, false);
                    GlStateManager.func_179091_B();
                    GlStateManager.func_179141_d();
                    GlStateManager.func_179092_a(516, 0.1f);
                    GlStateManager.func_179147_l();
                    GlStateManager.func_179112_b(770, 771);
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.func_179109_b(topX + (float)this.item_wool.offsetX, (float)topY, -10.0f);
                    GlStateManager.func_179109_b(8.0f, 8.0f, 0.0f);
                    GlStateManager.func_179152_a(1.0f, 1.0f, -1.0f);
                    GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
                    GlStateManager.func_179152_a(40.0f, 40.0f, 40.0f);
                    GlStateManager.func_179114_b(210.0f, 1.0f, 0.0f, 0.0f);
                    GlStateManager.func_179114_b(-135.0f, 0.0f, 1.0f, 0.0f);
                    ibakedmodel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GUI);
                    this.mc.func_175599_af().func_180454_a(this.item_wool.stack, ibakedmodel);
                    GlStateManager.func_179118_c();
                    GlStateManager.func_179101_C();
                    GlStateManager.func_179140_f();
                    GlStateManager.func_179121_F();
                    this.textureManager.func_110577_a(TextureMap.field_110575_b);
                    this.textureManager.func_110581_b(TextureMap.field_110575_b).func_174935_a();
                    block_counter_size_x += this.item_wool.width + 2;
                }
            }
            final OnMyTickEvent myTickEvent = Main.myTickEvent;
            if (OnMyTickEvent.isHintsItemCounterActive && this.item_emerald != null && this.item_diamond != null && !isF3) {
                final String text_emeralds = "" + this.COUNT_EMERALDS;
                final int color_emerals = this.getColor("ffffffff");
                final String text_diamonds = "" + this.COUNT_DIAMONDS;
                final int color_diamonds = this.getColor("ffffffff");
                final int font_emeralds_width = this.mc.field_71466_p.func_78256_a(text_emeralds);
                final int font_emeralds_height = this.mc.field_71466_p.field_78288_b;
                final int font_diamonds_width = this.mc.field_71466_p.func_78256_a(text_diamonds);
                final int font_diamonds_height = this.mc.field_71466_p.field_78288_b;
                int offsetX = block_counter_size_x;
                if (this.COUNT_EMERALDS > 0) {
                    if (text_emeralds.length() == 1) {
                        offsetX += 6;
                    }
                    if (text_emeralds.length() == 2) {
                        offsetX += 12;
                    }
                    if (text_emeralds.length() == 3) {
                        offsetX += 18;
                    }
                    final int offsetY2 = 5;
                    final int topX2 = screen_width - offsetX - this.item_emerald.width;
                    final int topY2 = screen_height - offsetY2 - this.item_emerald.height;
                    final int bottomX2 = screen_width - offsetX;
                    final int bottomY2 = screen_height - offsetY2;
                    this.item_emerald.drawOnGui(topX2, topY2);
                    this.mc.field_71466_p.func_175065_a(text_emeralds, (float)(bottomX2 - 1), (float)(bottomY2 - 4), color_emerals, true);
                }
                if (this.COUNT_DIAMONDS > 0) {
                    if (text_diamonds.length() == 1) {
                        offsetX += 6;
                    }
                    if (text_diamonds.length() == 2) {
                        offsetX += 12;
                    }
                    if (text_diamonds.length() == 3) {
                        offsetX += 18;
                    }
                    if (this.COUNT_EMERALDS > 0) {
                        offsetX += 12;
                    }
                    final int offsetY2 = 5;
                    final int topX2 = screen_width - offsetX - this.item_diamond.width;
                    final int topY2 = screen_height - offsetY2 - this.item_diamond.height;
                    final int bottomX2 = screen_width - offsetX;
                    final int bottomY2 = screen_height - offsetY2;
                    this.item_diamond.drawOnGui(topX2, topY2 - 1);
                    this.mc.field_71466_p.func_175065_a(text_diamonds, (float)(bottomX2 - 1), (float)(bottomY2 - 4), color_diamonds, true);
                }
            }
            if (this.isPotionEffectTrackerActive && !isF3) {
                final EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
                final Collection<PotionEffect> pe = (Collection<PotionEffect>)player.func_70651_bq();
                if (pe.size() > 0) {
                    if (this.textureManager == null) {
                        this.textureManager = this.mc.func_110434_K();
                    }
                    final ArrayList<PotionEffect> potion_effects = new ArrayList<PotionEffect>();
                    final BWItemsHandler.BWItemPotionsID[] potions_id_to_display = { BWItemsHandler.BWItemPotionsID.STRENGTH, BWItemsHandler.BWItemPotionsID.JUMP, BWItemsHandler.BWItemPotionsID.SPEED, BWItemsHandler.BWItemPotionsID.REGEN };
                    for (int i = 0; i < potions_id_to_display.length; ++i) {
                        for (final PotionEffect effect : pe) {
                            if (effect.func_76456_a() == potions_id_to_display[i].id) {
                                potion_effects.add(effect);
                                break;
                            }
                        }
                    }
                    final float scale = 3.0f;
                    final int cx = 5;
                    final int cy = screen_height / 2;
                    final int space_y = 30;
                    int start_y = cy - space_y * (potion_effects.size() / 2);
                    if (potion_effects.size() % 2 == 0) {
                        start_y += space_y / 2;
                    }
                    int current_y = start_y;
                    for (final PotionEffect p : potion_effects) {
                        BWItemsHandler.BWItemPotionsID potion = null;
                        int texX = 0;
                        int texY = 0;
                        Color color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
                        String text = "-";
                        this.mc.field_71446_o.func_110577_a(this.resourceLoc_potions);
                        if (BWItemsHandler.BWItemPotionsID.STRENGTH.id == p.func_76456_a()) {
                            texX = 0;
                            texY = 0;
                            color = new Color(1.0f, 0.0f, 0.0f, 1.0f);
                            text = "\u0421\u0438\u043b\u043a\u0430";
                            potion = BWItemsHandler.BWItemPotionsID.STRENGTH;
                        }
                        if (BWItemsHandler.BWItemPotionsID.JUMP.id == p.func_76456_a()) {
                            texX = 1;
                            texY = 0;
                            potion = BWItemsHandler.BWItemPotionsID.JUMP;
                            text = "\u041f\u0440\u044b\u0436\u043e\u043a";
                            color = new Color(0.0f, 1.0f, 0.0f, 1.0f);
                        }
                        if (BWItemsHandler.BWItemPotionsID.SPEED.id == p.func_76456_a()) {
                            texX = 2;
                            texY = 0;
                            color = new Color(0.0f, 1.0f, 1.0f, 1.0f);
                            text = "\u0421\u043a\u043e\u0440\u043a\u0430";
                            potion = BWItemsHandler.BWItemPotionsID.SPEED;
                        }
                        if (BWItemsHandler.BWItemPotionsID.REGEN.id == p.func_76456_a()) {
                            texX = 0;
                            texY = 1;
                            color = new Color(1.0f, 0.0f, 1.0f, 1.0f);
                            text = "\u0420\u0435\u0433\u0435\u043d";
                            potion = BWItemsHandler.BWItemPotionsID.REGEN;
                        }
                        if (potion == null) {
                            continue;
                        }
                        GlStateManager.func_179094_E();
                        GlStateManager.func_179109_b((float)cx, (float)current_y, 0.0f);
                        GlStateManager.func_179152_a(1.0f / scale, 1.0f / scale, 1.0f / scale);
                        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                        this.func_73729_b(0, -40, texX * 80, texY * 80, 80, 80);
                        GlStateManager.func_179121_F();
                        this.mc.field_71466_p.func_175065_a(text, cx + 80.0f / scale + 5.0f, current_y - 30.0f / scale, color.getRGB(), false);
                        final int time = p.func_76459_b() / 20;
                        String s_time = "" + time + "s";
                        if (time > 60) {
                            final int mins = time / 60;
                            final int secs = time % 60;
                            s_time = mins + ":" + ((secs < 10) ? "0" : "") + secs;
                        }
                        Color text_color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
                        boolean isShadow = false;
                        if (p.func_76459_b() <= 120) {
                            text_color = new Color(1.0f, 0.0f, 0.0f, 1.0f);
                            isShadow = true;
                        }
                        if (this.isPotionEffectTrackerSoundsActive && p.func_76459_b() == 120 && potion.id != BWItemsHandler.BWItemPotionsID.REGEN.id) {
                            final long t = new Date().getTime();
                            if (t - this.lastPlaySoundTime > 100L) {
                                MyChatListener.playSound("note.hat", 1.0f);
                                this.lastPlaySoundTime = t;
                            }
                        }
                        if (this.isPotionEffectTrackerSoundsActive && p.func_76459_b() == 3 && potion.id != BWItemsHandler.BWItemPotionsID.REGEN.id) {
                            final long t = new Date().getTime();
                            if (t - this.lastPlaySoundTime > 100L) {
                                MyChatListener.playSound("random.fizz", 1.0f);
                                this.lastPlaySoundTime = t;
                            }
                        }
                        this.mc.field_71466_p.func_175065_a(s_time, cx + 80.0f / scale + 5.0f, (float)current_y, text_color.getRGB(), isShadow);
                        current_y += space_y;
                    }
                }
            }
            if (!isF3) {
                Main.guiRadarIcon.draw();
            }
            Main.guiCrosshairBlocks.draw();
            final LightningLocator lightningLocator = Main.lightningLocator;
            if (LightningLocator.isActive) {
                final LightningLocator lightningLocator2 = Main.lightningLocator;
                if (LightningLocator.last_lightning != null) {
                    final LightningLocator lightningLocator3 = Main.lightningLocator;
                    final LightningLocator.MyLightning lightning = LightningLocator.last_lightning;
                    final int dist = (int)Math.sqrt(Math.pow(lightning.lightning.field_70165_t - this.mc.field_71439_g.field_70165_t, 2.0) + Math.pow(lightning.lightning.field_70161_v - this.mc.field_71439_g.field_70161_v, 2.0));
                    final int game_time = Math.max((int)((new Date().getTime() - lightning.time_created) / 1000.0f) - 1, 0);
                    final int seconds = game_time % 60;
                    String time2 = game_time / 60 + ":" + ((seconds >= 10) ? "" : "0") + seconds;
                    if (game_time < 60) {
                        time2 = game_time + " \u0441";
                    }
                    final String s = ColorCodesManager.replaceColorCodesInString("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f: &a" + dist + " &l" + HintsFinder.getArrowDirection(lightning.lightning.field_70165_t, lightning.lightning.field_70161_v) + "&f, \u0432\u0440\u0435\u043c\u044f: &e" + time2 + "");
                    this.mc.field_71466_p.func_175065_a(s, (float)(screen_width / 2 - this.mc.field_71466_p.func_78256_a(s) / 2), (float)(screen_height / 3 * 2), new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB(), true);
                }
            }
            final LobbyFly lobbyFly = Main.lobbyFly;
            if (LobbyFly.isActive) {
                GlStateManager.func_179094_E();
                GlStateManager.func_179109_b((float)(screen_width - 32), (float)(screen_height - 20), 0.0f);
                final float scale2 = 0.5f;
                GlStateManager.func_179152_a(scale2, scale2, scale2);
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                this.mc.field_71446_o.func_110577_a(this.resourceLoc_other);
                this.func_175174_a(-75.0f, -34.5f, 126, 187, 130, 69);
                GlStateManager.func_179121_F();
                final StringBuilder append = new StringBuilder().append("");
                final LobbyFly lobbyFly2 = Main.lobbyFly;
                String s2 = append.append(LobbyFly.speed).toString();
                final LobbyFly lobbyFly3 = Main.lobbyFly;
                if (LobbyFly.speed >= 1.0f) {
                    final StringBuilder append2 = new StringBuilder().append("");
                    final LobbyFly lobbyFly4 = Main.lobbyFly;
                    s2 = append2.append((int)LobbyFly.speed).toString();
                }
                this.mc.field_71466_p.func_175065_a(s2, (float)(screen_width - this.mc.field_71466_p.func_78256_a(s2) / 2 - 36), (float)(screen_height - 30), new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB(), true);
            }
        }
    }
    
    private int getColor(final String hexColor) {
        final Color colorNoAlpha = Color.decode("0x" + hexColor.substring(0, 6));
        final int alpha = Integer.parseInt(hexColor.substring(6, 8), 16);
        return new Color(colorNoAlpha.getRed(), colorNoAlpha.getGreen(), colorNoAlpha.getBlue(), alpha).getRGB();
    }
    
    public class MyItem
    {
        public int offsetX;
        public int offsetY;
        public int width;
        public int height;
        public ItemStack stack;
        
        public MyItem(final int offsetX, final int offsetY, final int width, final int height, final Item item) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.width = width;
            this.height = height;
            this.stack = new ItemStack(item);
        }
        
        public void drawOnGui(final int posX, final int posY) {
            GuiOnScreen.this.mc.func_175599_af().func_175042_a(this.stack, posX + this.offsetX, posY + this.offsetY);
        }
    }
}
