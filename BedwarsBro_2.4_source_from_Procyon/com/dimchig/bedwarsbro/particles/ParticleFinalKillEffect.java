// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.particles;

import scala.util.Random;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityAuraFX;

public class ParticleFinalKillEffect extends EntityAuraFX
{
    public ParticleFinalKillEffect(final World parWorld, final double parX, final double parY, final double parZ) {
        super(parWorld, parX, parY, parZ, 0.0, 0.0, 0.0);
        final Random rnd = new Random();
        this.func_70536_a(65);
        this.field_70544_f = 1.2f;
        this.func_70538_b(1.0f, 1.0f, 1.0f);
        this.field_70547_e = 50 + rnd.nextInt(30);
        this.field_70545_g = 1000.0f;
    }
}
