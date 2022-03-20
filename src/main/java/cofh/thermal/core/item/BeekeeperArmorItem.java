package cofh.thermal.core.item;

import cofh.core.event.ArmorEvents;
import cofh.core.item.ArmorItemCoFH;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class BeekeeperArmorItem extends ArmorItemCoFH {

    public BeekeeperArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {

        super(materialIn, slot, builder);

        ArmorEvents.registerStingResistArmor(this, RESISTANCE_RATIO[slot.getIndex()]);
    }

}
