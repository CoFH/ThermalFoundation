package cofh.thermal.lib.common;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.block.entity.device.DevicePotionDiffuserTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ThermalProxyClient extends ThermalProxy {

    @Override
    public void spawnDiffuserParticles(DevicePotionDiffuserTile tile) {

        int color = FluidHelper.color(tile.getRenderFluid());
        int radius = tile.getRadius();

        Vec3 vec = Vec3.upFromBottomCenterOf(tile.getBlockPos(), 2);
        Level world = tile.world();

        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        ParticleOptions particleData = tile.isInstant() ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;
        double speedY = 0;

        for (int i = 0; i < 4 * radius * radius; ++i) {
            double degrees = world.random.nextDouble() * MathHelper.PI_2;
            double speedMult = world.random.nextDouble() * radius * MathHelper.SQRT_2;
            speedMult *= speedMult;

            double speedX = Math.cos(degrees) * speedMult;
            double speedZ = Math.sin(degrees) * speedMult;
            Particle particle = Minecraft.getInstance().levelRenderer.addParticleInternal(particleData, particleData.getType().getOverrideLimiter(), vec.x + speedX * 0.1D, vec.y - (world.random.nextDouble() + 0.5D), vec.z + speedZ * 0.1D, speedX, speedY, speedZ);
            if (particle != null) {
                float colorMult = 0.75F + world.random.nextFloat() * 0.25F;
                particle.setColor(r * colorMult, g * colorMult, b * colorMult);
            }
        }
    }

}
