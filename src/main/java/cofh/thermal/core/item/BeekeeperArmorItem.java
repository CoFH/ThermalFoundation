package cofh.thermal.core.item;

import cofh.core.event.ArmorEvents;
import cofh.core.item.ArmorItemCoFH;
import cofh.lib.client.renderer.entity.model.ArmorFullSuitModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class BeekeeperArmorItem extends ArmorItemCoFH {

    public BeekeeperArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {

        super(materialIn, slot, builder);

        ArmorEvents.registerStingResistArmor(this, RESISTANCE_RATIO[slot.getIndex()]);
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
