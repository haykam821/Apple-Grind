package io.github.haykam821.applegrind.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.container.GrindstoneContainer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(GrindstoneContainer.class)
public class GrindstoneContainerMixin {
	@Shadow
	private Inventory craftingInventory;

	@Shadow
	private Inventory resultInventory;

	@Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
	private void updateGoldenAppleResult(CallbackInfo ci) {
		ItemStack firstInput = this.craftingInventory.getInvStack(0);
		ItemStack secondInput = this.craftingInventory.getInvStack(1);

		if (firstInput.getItem() != Items.ENCHANTED_GOLDEN_APPLE && !firstInput.isEmpty()) return;
		if (secondInput.getItem() != Items.ENCHANTED_GOLDEN_APPLE && !secondInput.isEmpty()) return;

		int goldenApples = firstInput.getCount() + secondInput.getCount();
		if (goldenApples > 0) {
			this.resultInventory.setInvStack(0, new ItemStack(Items.GOLDEN_APPLE, goldenApples));
			((GrindstoneContainer) (Object) this).sendContentUpdates();

			ci.cancel();
		}
	}
}