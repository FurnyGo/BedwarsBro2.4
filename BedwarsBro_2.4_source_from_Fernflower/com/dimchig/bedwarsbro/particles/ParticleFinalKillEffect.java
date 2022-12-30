package com.dimchig.bedwarsbro.particles;

import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.world.World;
import scala.util.Random;

public class ParticleFinalKillEffect extends EntityAuraFX {

   public ParticleFinalKillEffect(World parWorld, double parX, double parY, double parZ) {
      super(parWorld, parX, parY, parZ, 0.0D, 0.0D, 0.0D);
      Random rnd = new Random();
      this.func_70536_a(65);
      this.field_70544_f = 1.2F;
      this.func_70538_b(1.0F, 1.0F, 1.0F);
      this.field_70547_e = 50 + rnd.nextInt(30);
      this.field_70545_g = 1000.0F;
   }
}
