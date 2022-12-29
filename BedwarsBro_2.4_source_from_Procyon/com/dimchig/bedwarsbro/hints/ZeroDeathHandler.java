// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.hints;

import java.text.DecimalFormat;
import com.dimchig.bedwarsbro.ChatSender;
import java.util.Date;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentHelper;
import java.util.Iterator;
import java.util.Collection;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import com.dimchig.bedwarsbro.MyChatListener;
import com.dimchig.bedwarsbro.Main;
import net.minecraft.client.Minecraft;

public class ZeroDeathHandler
{
    static Minecraft mc;
    public static boolean isActive;
    public static int height_treshold;
    public static boolean fall_check;
    public static double health_treshold;
    public static boolean health_checker;
    public static boolean isWriteInChat;
    public static int MIN_TIME_INTERVAL;
    long last_time_rejoin;
    double min_fall_speed;
    double temp_val_1;
    double temp_val_2;
    double temp_val_3;
    
    public ZeroDeathHandler() {
        this.last_time_rejoin = 0L;
        this.min_fall_speed = 0.7;
        this.temp_val_1 = 0.0;
        this.temp_val_2 = 0.0;
        this.temp_val_3 = 0.0;
        ZeroDeathHandler.mc = Minecraft.func_71410_x();
        ZeroDeathHandler.isActive = false;
    }
    
    public void updateBooleans() {
        ZeroDeathHandler.isActive = Main.getConfigBool(Main.CONFIG_MSG.ZERO_DEATH);
        ZeroDeathHandler.height_treshold = Main.getConfigInt(Main.CONFIG_MSG.ZERO_DEATH_HEIGHT_TRESHOLD);
        ZeroDeathHandler.fall_check = Main.getConfigBool(Main.CONFIG_MSG.ZERO_DEATH_FALL_CHECK);
        ZeroDeathHandler.health_treshold = Main.getConfigDouble(Main.CONFIG_MSG.ZERO_DEATH_HEALTH_TRESHOLD);
        ZeroDeathHandler.health_checker = Main.getConfigBool(Main.CONFIG_MSG.ZERO_DEATH_HEALTH_CHECK_NEARBY);
        ZeroDeathHandler.isWriteInChat = Main.getConfigBool(Main.CONFIG_MSG.ZERO_DEATH_WRITE_IN_CHAT);
    }
    
    public void scan() {
        if (ZeroDeathHandler.mc == null || ZeroDeathHandler.mc.field_71439_g == null) {
            return;
        }
        int bed_pos_y = 0;
        Label_0070: {
            if (!ZeroDeathHandler.mc.func_71356_B()) {
                if (ZeroDeathHandler.isActive) {
                    final MyChatListener chatListener = Main.chatListener;
                    if (MyChatListener.IS_IN_GAME) {
                        final MyChatListener chatListener2 = Main.chatListener;
                        final BWBed bed = MyChatListener.GAME_BED;
                        if (bed == null) {
                            return;
                        }
                        bed_pos_y = bed.part1_posY;
                        if (!this.isPlayerHasArmour()) {
                            return;
                        }
                        break Label_0070;
                    }
                }
                return;
            }
        }
        if (ZeroDeathHandler.height_treshold != 999 && bed_pos_y - ZeroDeathHandler.mc.field_71439_g.field_70163_u >= ZeroDeathHandler.height_treshold && ZeroDeathHandler.mc.field_71439_g.field_70181_x < -this.min_fall_speed && !this.isWaterUnderMe()) {
            this.rejoin(0, bed_pos_y - ZeroDeathHandler.mc.field_71439_g.field_70163_u);
            return;
        }
        if (ZeroDeathHandler.fall_check && !Main.autoWaterDrop.isWaterDropStarted && this.isDeadFromFall() && ZeroDeathHandler.mc.field_71439_g.field_70181_x < -this.min_fall_speed && !this.isWaterUnderMe() && !this.isHoldingWaterBucket()) {
            this.rejoin(1, ZeroDeathHandler.mc.field_71439_g.field_70143_R);
            return;
        }
        if (ZeroDeathHandler.health_treshold == 999.0 || ZeroDeathHandler.mc.field_71439_g.func_110143_aJ() > ZeroDeathHandler.health_treshold) {
            return;
        }
        if (ZeroDeathHandler.health_checker) {
            final int nearby_call = this.isHealthChecker();
            if (nearby_call != 0) {
                this.rejoin(2, ZeroDeathHandler.mc.field_71439_g.func_110143_aJ(), nearby_call);
            }
            return;
        }
        this.rejoin(2, ZeroDeathHandler.mc.field_71439_g.func_110143_aJ());
    }
    
    @SubscribeEvent
    public void onDamage(final LivingHurtEvent e) {
    }
    
    public double calculateCurrentFallDamage() {
        double fall_damage = ZeroDeathHandler.mc.field_71439_g.field_70143_R;
        if (fall_damage <= 3.0) {
            return 0.0;
        }
        final double epf = this.getInventoryEPF();
        final double armour_defence = epf * 2.0;
        fall_damage = (1.0 - armour_defence / 100.0) * (ZeroDeathHandler.mc.field_71439_g.field_70143_R - 2.0f);
        final Collection<PotionEffect> pe = (Collection<PotionEffect>)ZeroDeathHandler.mc.field_71439_g.func_70651_bq();
        if (pe.size() > 0) {
            for (final PotionEffect effect : pe) {
                if (effect.func_76456_a() == 8) {
                    fall_damage -= effect.func_76458_c();
                    break;
                }
            }
        }
        return fall_damage;
    }
    
    public double getInventoryEPF() {
        if (ZeroDeathHandler.mc.field_71439_g.func_70035_c() == null) {
            return 0.0;
        }
        int eps_total = 0;
        try {
            for (int i = 0; i < 4; ++i) {
                final ItemStack itemStack = ZeroDeathHandler.mc.field_71439_g.func_70035_c()[i];
                if (itemStack != null) {
                    if (itemStack.func_77973_b() != null) {
                        final Integer val = EnchantmentHelper.func_82781_a(itemStack).get(0);
                        if (val != null) {
                            eps_total += val;
                            if (val == 4) {
                                ++eps_total;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            return 0.0;
        }
        return eps_total;
    }
    
    public boolean isDeadFromFall() {
        final double damage = this.calculateCurrentFallDamage();
        return damage > 0.0 && ZeroDeathHandler.mc.field_71439_g.func_110143_aJ() < damage;
    }
    
    public boolean isPlayerHasArmour() {
        return ZeroDeathHandler.mc.field_71439_g.func_70035_c() != null && ZeroDeathHandler.mc.field_71439_g.func_70035_c()[1] != null;
    }
    
    public int isHealthChecker() {
        final int nearby_player_radius = 15;
        final int nearby_projectile_radius = 10;
        final List<EntityPlayer> entities = (List<EntityPlayer>)ZeroDeathHandler.mc.field_71441_e.field_73010_i;
        for (final EntityPlayer en : entities) {
            if (en != null && en.func_70005_c_() != null) {
                if (en.func_145748_c_() == null) {
                    continue;
                }
                if (en.func_96124_cp() == ZeroDeathHandler.mc.field_71439_g.func_96124_cp()) {
                    continue;
                }
                if (en.func_70005_c_().equals(ZeroDeathHandler.mc.field_71439_g.func_70005_c_())) {
                    continue;
                }
                final double dist = en.func_70011_f(ZeroDeathHandler.mc.field_71439_g.field_70165_t, ZeroDeathHandler.mc.field_71439_g.field_70163_u, ZeroDeathHandler.mc.field_71439_g.field_70161_v);
                if (dist < nearby_player_radius) {
                    return 1;
                }
                continue;
            }
        }
        final BlockPos minPos = new BlockPos(ZeroDeathHandler.mc.field_71439_g.field_70165_t - nearby_projectile_radius, ZeroDeathHandler.mc.field_71439_g.field_70163_u - nearby_projectile_radius, ZeroDeathHandler.mc.field_71439_g.field_70161_v - nearby_projectile_radius);
        final BlockPos maxPos = new BlockPos(ZeroDeathHandler.mc.field_71439_g.field_70165_t + nearby_projectile_radius, ZeroDeathHandler.mc.field_71439_g.field_70163_u + nearby_projectile_radius, ZeroDeathHandler.mc.field_71439_g.field_70161_v + nearby_projectile_radius);
        final AxisAlignedBB box = new AxisAlignedBB(minPos, maxPos);
        final List<EntityArrow> arr_arrows = (List<EntityArrow>)ZeroDeathHandler.mc.field_71441_e.func_72872_a((Class)EntityArrow.class, box);
        if (arr_arrows != null && arr_arrows.size() > 0) {
            for (final EntityArrow en2 : arr_arrows) {
                final Entity sender = en2.field_70250_c;
                if (sender != null && sender instanceof EntityPlayer) {
                    if (sender == ZeroDeathHandler.mc.field_71439_g) {
                        continue;
                    }
                    if (en2.field_70163_u - en2.field_70167_r == 0.0) {
                        continue;
                    }
                    return 2;
                }
            }
        }
        return 0;
    }
    
    public boolean isHoldingWaterBucket() {
        final ItemStack is = ZeroDeathHandler.mc.field_71439_g.func_71045_bC();
        return is != null && is.func_77973_b() == Items.field_151131_as;
    }
    
    public boolean isWaterUnderMe() {
        final EntityPlayerSP player = ZeroDeathHandler.mc.field_71439_g;
        if (player == null) {
            return false;
        }
        if (player.field_70163_u < 10.0) {
            return false;
        }
        final int px = (int)Math.floor(player.field_70165_t);
        final int pz = (int)Math.floor(player.field_70161_v);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        final World world = (World)ZeroDeathHandler.mc.field_71441_e;
        for (int i = (int)player.field_70163_u; i > 0; --i) {
            pos = pos.func_181079_c(px, i, pz);
            final IBlockState state = world.func_180495_p((BlockPos)pos);
            if (state != null) {
                final Block b = state.func_177230_c();
                if (b == Blocks.field_150355_j) {
                    return true;
                }
                if (b != Blocks.field_150350_a) {
                    return false;
                }
            }
        }
        return false;
    }
    
    public void rejoin(final int reason, final double val) {
        this.rejoin(reason, val, 0.0);
    }
    
    public void rejoin(final int reason, final double val, final double extra_val) {
        final long t = new Date().getTime();
        if (t - this.last_time_rejoin < ZeroDeathHandler.MIN_TIME_INTERVAL) {
            return;
        }
        this.last_time_rejoin = t;
        ChatSender.sendText("/leave");
        Main.myTickEvent.zeroDeathHandlerRejoinVar = 10;
        if (ZeroDeathHandler.isWriteInChat) {
            String hover = "&f\u041f\u0440\u0438\u0447\u0438\u043d\u0430: ";
            if (reason == 0) {
                final StringBuilder append = new StringBuilder().append(hover).append("\u041f\u0430\u0434\u0435\u043d\u0438\u0435 \u043d\u0438\u0436\u0435 \u043a\u0440\u043e\u0432\u0430\u0442\u0438 \u043d\u0430 &e").append(ZeroDeathHandler.height_treshold).append("&f \u0431\u043b\u043e\u043a\u043e\u0432. \u0412 \u0442\u0432\u043e\u0435\u043c \u0441\u043b\u0443\u0447\u0430\u0435 \u0431\u044b\u043b\u043e &c").append((int)val).append("&f \u0431\u043b\u043e\u043a");
                final MyChatListener chatListener = Main.chatListener;
                hover = append.append(MyChatListener.getNumberEnding((int)val, "", "\u0430", "\u043e\u0432")).toString();
            }
            else if (reason == 1) {
                final StringBuilder append2 = new StringBuilder().append(hover).append("\u041f\u0430\u0434\u0435\u043d\u0438\u0435 \u0441 &c\u0441\u043c\u0435\u0440\u0442\u0435\u043b\u044c\u043d\u043e\u0439 \u0432\u044b\u0441\u043e\u0442\u044b&f. \u0422\u044b \u043f\u0430\u0434\u0430\u043b \u0441 \u0432\u044b\u0441\u043e\u0442\u044b \u0432 &c").append((int)val).append("&f \u0431\u043b\u043e\u043a");
                final MyChatListener chatListener2 = Main.chatListener;
                hover = append2.append(MyChatListener.getNumberEnding((int)val, "", "\u0430", "\u043e\u0432")).toString();
            }
            else if (reason == 2) {
                hover = hover + "\u043a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0437\u0434\u043e\u0440\u043e\u0432\u044c\u044f <= &e" + ZeroDeathHandler.health_treshold + "&f. \u0412 \u0442\u0432\u043e\u0435\u043c \u0441\u043b\u0443\u0447\u0430\u0435 \u0431\u044b\u043b\u043e &c" + new DecimalFormat("0.0").format(val) + "&f \u0437\u0434\u043e\u0440\u043e\u0432\u044c\u044f";
                if (extra_val == 1.0) {
                    hover += " \u0438 \u0440\u044f\u0434\u043e\u043c \u0431\u044b\u043b &c\u0438\u0433\u0440\u043e\u043a";
                }
                if (extra_val == 2.0) {
                    hover += " \u0438 \u0440\u044f\u0434\u043e\u043c \u043b\u0435\u0442\u0435\u043b\u0430 &c\u0441\u0442\u0440\u0435\u043b\u0430";
                }
                if (extra_val == 3.0) {
                    hover += " \u0438 \u0440\u044f\u0434\u043e\u043c \u043b\u0435\u0442\u0435\u043b &c\u0444\u0430\u0435\u0440\u0431\u043e\u043b";
                }
            }
            else {
                hover += "&c\u041d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u0430...";
            }
            final MyChatListener chatListener3 = Main.chatListener;
            MyChatListener.PREFIX_ZERO_DEATH = "&r&a&lZero&c&lDeath&8 \u25b8 §r";
            final StringBuilder sb = new StringBuilder();
            final MyChatListener chatListener4 = Main.chatListener;
            ChatSender.addHoverText(sb.append(MyChatListener.PREFIX_ZERO_DEATH).append("&f\u041f\u0435\u0440\u0435\u0437\u0430\u0445\u043e\u0434...").toString(), hover);
        }
    }
    
    static {
        ZeroDeathHandler.isActive = false;
        ZeroDeathHandler.height_treshold = 10;
        ZeroDeathHandler.fall_check = true;
        ZeroDeathHandler.health_treshold = 5.0;
        ZeroDeathHandler.health_checker = true;
        ZeroDeathHandler.isWriteInChat = true;
        ZeroDeathHandler.MIN_TIME_INTERVAL = 9000;
    }
}
