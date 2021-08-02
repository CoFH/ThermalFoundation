package cofh.thermal.core.item;

import cofh.core.item.ItemCoFH;
import cofh.lib.block.IDismantleable;
import cofh.lib.block.IWrenchable;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.BlockHelper;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static cofh.lib.util.references.CoreReferences.WRENCHED;

public class WrenchItem extends ItemCoFH {

    private final Multimap<Attribute, AttributeModifier> toolAttributes;

    public WrenchItem(Properties builder) {

        super(builder);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> multimap = ImmutableMultimap.builder();
        multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 0.0D, AttributeModifier.Operation.ADDITION));

        this.toolAttributes = multimap.build();
    }

    protected boolean useDelegate(ItemStack stack, ItemUseContext context) {

        World world = context.getWorld();
        BlockPos pos = context.getPos();
        PlayerEntity player = context.getPlayer();

        if (player == null || world.isAirBlock(pos)) {
            return false;
        }
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (player.isSecondaryUseActive() && block instanceof IDismantleable && ((IDismantleable) block).canDismantle(world, pos, state, player)) {
            if (Utils.isServerWorld(world)) {
                BlockRayTraceResult target = new BlockRayTraceResult(context.getHitVec(), context.getFace(), context.getPos(), context.isInside());
                ((IDismantleable) block).dismantleBlock(world, pos, state, target, player, false);
            }
            player.swingArm(context.getHand());
            return true;
        } else if (!player.isSecondaryUseActive()) {
            if (block instanceof IWrenchable && ((IWrenchable) block).canWrench(world, pos, state, player)) {
                BlockRayTraceResult target = new BlockRayTraceResult(context.getHitVec(), context.getFace(), context.getPos(), context.isInside());
                ((IWrenchable) block).wrenchBlock(world, pos, state, target, player);
                return true;
            }
            return BlockHelper.attemptRotateBlock(state, world, pos);
        }
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        target.addPotionEffect(new EffectInstance(WRENCHED, 60, 0, false, false));
        stack.damageItem(1, attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.FAIL;
        }
        return player.canPlayerEdit(context.getPos(), context.getFace(), context.getItem()) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResultType.PASS;
        }
        return player.canPlayerEdit(context.getPos(), context.getFace(), stack) && useDelegate(stack, context) ? ActionResultType.SUCCESS : ActionResultType.PASS;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {

        return slot == EquipmentSlotType.MAINHAND ? this.toolAttributes : ImmutableMultimap.of();
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {

        return new ItemStack(this);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {

        return true;
    }

}
