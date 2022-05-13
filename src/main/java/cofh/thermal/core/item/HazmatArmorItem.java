package cofh.thermal.core.item;

import cofh.core.event.ArmorEvents;
import cofh.core.item.ArmorItemCoFH;
import cofh.lib.client.renderer.entity.model.ArmorFullSuitModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class HazmatArmorItem extends ArmorItemCoFH {

    protected static final int AIR_DURATION = 600;

    public HazmatArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {

        super(materialIn, slot, builder);

        ArmorEvents.registerHazardResistArmor(this, RESISTANCE_RATIO[slot.getIndex()]);
        if (slot == EquipmentSlot.FEET) {
            ArmorEvents.registerFallResistArmor(this, 6.0D);
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {

        if (this.slot == EquipmentSlot.HEAD) {
            if (player.getAirSupply() < player.getMaxAirSupply() && world.random.nextInt(3) > 0) {
                player.setAirSupply(player.getAirSupply() + 1);
            }
            // TODO: Revisit
            //            if (!player.areEyesInFluid(FluidTags.WATER)) {
            //                Utils.addPotionEffectNoEvent(player, new EffectInstance(Effects.WATER_BREATHING, AIR_DURATION, 0, false, false, true));
            //            }
        }
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {

        consumer.accept(new IItemRenderProperties() {

            @Override
            @Nonnull
            public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {

                return armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.FEET ? _default : ArmorFullSuitModel.INSTANCE.get();
            }
        });
    }

}
