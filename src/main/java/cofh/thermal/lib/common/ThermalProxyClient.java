package cofh.thermal.lib.common;

import cofh.core.util.helpers.FluidHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.tileentity.device.DevicePotionDiffuserTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ThermalProxyClient extends ThermalProxy {

    @Override
    public void spawnDiffuserParticles(DevicePotionDiffuserTile tile) {

        int color = FluidHelper.color(tile.getRenderFluid());
        int radius = tile.getRadius();

        Vector3d vector3d = Vector3d.copyCenteredWithVerticalOffset(tile.getPos(), 2);
        World world = tile.world();

        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        IParticleData particleData = tile.isInstant() ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;
        double speedY = 0;

        for (int i = 0; i < 4 * radius * radius; ++i) {
            double degrees = world.rand.nextDouble() * MathHelper.PI_2;
            double speedMult = world.rand.nextDouble() * radius * MathHelper.SQRT_2;
            speedMult *= speedMult;

            double speedX = Math.cos(degrees) * speedMult;
            double speedZ = Math.sin(degrees) * speedMult;
            Particle particle = Minecraft.getInstance().worldRenderer.addParticleUnchecked(particleData, particleData.getType().getAlwaysShow(), vector3d.x + speedX * 0.1D, vector3d.y - (world.rand.nextDouble() + 0.5D), vector3d.z + speedZ * 0.1D, speedX, speedY, speedZ);
            if (particle != null) {
                float colorMult = 0.75F + world.rand.nextFloat() * 0.25F;
                particle.setColor(r * colorMult, g * colorMult, b * colorMult);
            }
        }
    }

}
