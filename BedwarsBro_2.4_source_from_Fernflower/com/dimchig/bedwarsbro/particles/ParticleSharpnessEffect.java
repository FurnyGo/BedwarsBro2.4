package com.dimchig.bedwarsbro.particles;

import java.util.Random;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.world.World;

public class ParticleSharpnessEffect extends EntityCrit2FX {

   public ParticleSharpnessEffect(World parWorld, double parX, double parY, double parZ) {
      super(parWorld, parX, parY, parZ, 0.0D, 0.0D, 0.0D);
      Random rnd = new Random();
      this.field_70547_e = 7 + rnd.nextInt(3);
   }
}
