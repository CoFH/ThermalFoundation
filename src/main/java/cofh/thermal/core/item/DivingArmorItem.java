package cofh.thermal.core.item;

import cofh.core.item.ArmorItemCoFH;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static net.minecraftforge.common.ForgeMod.SWIM_SPEED;

public class DivingArmorItem extends ArmorItemCoFH {

    protected static final double[] SWIM_SPEED_BONUS = new double[]{0.60D, 0.30D, 0.10D, 0.0D};
    protected static final int AIR_DURATION = 1800;

    private Multimap<Attribute, AttributeModifier> armorAttributes;

    public DivingArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {

        super(materialIn, slot, builder);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> multimap = ImmutableMultimap.builder();
        armorAttributes = multimap.build();
    }

    public void setup() {

        ImmutableMultimap.Builder<Attribute, AttributeModifier> multimap = ImmutableMultimap.builder();
        multimap.putAll(super.getAttributeModifiers(slot));
        multimap.put(SWIM_SPEED.get(), new AttributeModifier(UUID_SWIM_SPEED[slot.getIndex()], "Swim Speed", SWIM_SPEED_BONUS[slot.getIndex()], AttributeModifier.Operation.ADDITION));
        armorAttributes = multimap.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {

        return slot == this.slot ? armorAttributes : ImmutableMultimap.of();
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {

        if (this.slot == EquipmentSlotType.HEAD) {
            if (player.getAir() < player.getMaxAir() && world.rand.nextInt(5) > 0) {
                player.setAir(player.getAir() + 1);
            }
            // TODO: Revisit
            //            if (!player.areEyesInFluid(FluidTags.WATER)) {
            //                Utils.addPotionEffectNoEvent(player, new EffectInstance(Effects.WATER_BREATHING, AIR_DURATION, 0, false, false, true));
            //            }
        }
    }

}
