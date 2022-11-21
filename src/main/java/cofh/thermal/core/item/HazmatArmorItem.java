package cofh.thermal.core.item;

import cofh.core.client.renderer.entity.model.ArmorFullSuitModel;
import cofh.core.event.ArmorEvents;
import cofh.core.item.ArmorItemCoFH;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

import static cofh.lib.util.helpers.StringHelper.getTextComponent;

public class HazmatArmorItem extends ArmorItemCoFH {

    public HazmatArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {

        super(materialIn, slot, builder);

        ArmorEvents.registerHazardResistArmor(this, RESISTANCE_RATIO[slot.getIndex()]);
        if (slot == EquipmentSlot.FEET) {
            ArmorEvents.registerFallResistArmor(this, 6.0D);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        tooltip.add(getTextComponent("info.thermal.hazmat_armor").withStyle(ChatFormatting.GOLD));

        if (this.slot == EquipmentSlot.HEAD) {
            tooltip.add(getTextComponent("info.thermal.hazmat_helmet").withStyle(ChatFormatting.GOLD));
        }
        if (this.slot == EquipmentSlot.FEET) {
            tooltip.add(getTextComponent("info.thermal.hazmat_boots").withStyle(ChatFormatting.GOLD));
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
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {

        consumer.accept(new IClientItemExtensions() {

            @Override
            @Nonnull
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {

                return armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.FEET ? _default : ArmorFullSuitModel.INSTANCE.get();
            }
        });
    }

}
