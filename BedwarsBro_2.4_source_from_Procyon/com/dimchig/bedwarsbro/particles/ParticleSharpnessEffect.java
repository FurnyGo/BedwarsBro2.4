// 
// Decompiled by Procyon v0.5.36
// 

package com.dimchig.bedwarsbro.particles;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityCrit2FX;

public class ParticleSharpnessEffect extends EntityCrit2FX
{
    public ParticleSharpnessEffect(final World parWorld, final double parX, final double parY, final double parZ) {
        super(parWorld, parX, parY, parZ, 0.0, 0.0, 0.0);
        final Random rnd = new Random();
        this.field_70547_e = 7 + rnd.nextInt(3);
    }
}
