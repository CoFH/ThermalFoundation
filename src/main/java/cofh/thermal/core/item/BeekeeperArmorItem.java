package cofh.thermal.core.item;

import cofh.core.event.ArmorEvents;
import cofh.core.item.ArmorItemCoFH;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;

public class BeekeeperArmorItem extends ArmorItemCoFH {

    public BeekeeperArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {

        super(materialIn, slot, builder);

        ArmorEvents.registerStingResistArmor(this, RESISTANCE_RATIO[slot.getIndex()]);
    }

}
