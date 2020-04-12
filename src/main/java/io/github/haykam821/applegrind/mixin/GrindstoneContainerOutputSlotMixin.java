package io.github.haykam821.applegrind.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

@Mixin(targets = "net/minecraft/container/GrindstoneContainer$4")
public class GrindstoneContainerOutputSlotMixin {
	@Inject(method = "onTakeItem", at = @At("RETURN"), cancellable = true)
	private void allowInsertingGoldenApple(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
		if (stack.getItem() == Items.GOLDEN_APPLE && !player.world.isClient()) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

			Identifier advancementID = new Identifier("applegrind", "story/disenchant_golden_apple");
			Advancement advancement = player.getServer().getAdvancementLoader().get(advancementID);
			serverPlayer.getAdvancementTracker().grantCriterion(advancement, "impossible");
		}
	}
}