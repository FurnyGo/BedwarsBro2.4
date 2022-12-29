// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.commands;

import net.minecraft.command.CommandException;
import net.minecraft.entity.Entity;
import com.dimchig.bedwarsbro.ChatSender;
import com.dimchig.bedwarsbro.particles.ParticleController;
import com.dimchig.bedwarsbro.hints.HintsFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandBase;

public class CommandHintsFinderLookAt extends CommandBase
{
    public static String command_text;
    
    public CommandHintsFinderLookAt() {
        CommandHintsFinderLookAt.command_text = CommandHintsFinderLookAt.command_text.replace("/", "");
    }
    
    public boolean func_71519_b(final ICommandSender sender) {
        return true;
    }
    
    public String func_71517_b() {
        return CommandHintsFinderLookAt.command_text;
    }
    
    public String func_71518_a(final ICommandSender sender) {
        return "Makes message rainbow";
    }
    
    public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 4) {
            return;
        }
        try {
            final double posX = Double.parseDouble(args[0]);
            final double posY = Double.parseDouble(args[1]);
            final double posZ = Double.parseDouble(args[2]);
            final int isEmerald = Integer.parseInt(args[3]);
            final Entity player = (Entity)Minecraft.func_71410_x().field_71439_g;
            HintsFinder.lookAtPlayer(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v, posX, posY, posZ);
            if (isEmerald == 1) {
                ParticleController.spawnGenEmeraldParticles(posX, posY, posZ, 1);
            }
            else {
                ParticleController.spawnGenDiamondParticles(posX, posY, posZ, 1);
            }
        }
        catch (Exception ex) {
            ChatSender.addText("&cInvalid arguments!");
        }
    }
    
    static {
        CommandHintsFinderLookAt.command_text = "/bedwarsChatModLookAt";
    }
}
