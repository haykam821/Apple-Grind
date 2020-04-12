package io.github.haykam821.applegrind.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(targets = {"net/minecraft/container/GrindstoneContainer$2", "net/minecraft/container/GrindstoneContainer$3"})
public class GrindstoneContainerInputSlotsMixin {
	@Inject(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
	private void allowInsertingGoldenApple(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
		if (stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
			ci.setReturnValue(true);
		}
	}
}