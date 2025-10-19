package com.example.addon;

import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;

public class ElytraSwap extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    
    private final Setting<Integer> durabilityThreshold = sgGeneral.add(new IntSetting.Builder()
        .name("durability-threshold")
        .description("Durabilidade mínima da elytra antes de trocar (1-100)")
        .default_(10)
        .min(1)
        .max(100)
        .build()
    );

    private final Setting<Boolean> autoSwap = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-swap")
        .description("Trocar automaticamente quando durabilidade atinge o limite")
        .default_(true)
        .build()
    );

    public ElytraSwap() {
        super(Category.Movement, "Elytra Swap", "Troca automática de elytra quando perto de quebrar");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (!autoSwap.get()) return;
        
        // Pega a elytra que está equipada no chest
        ItemStack equippedElytra = mc.player.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST);
        
        // Verifica se é elytra e se está perto de quebrar
        if (equippedElytra.getItem() == Items.ELYTRA) {
            int durability = equippedElytra.getMaxDamage() - equippedElytra.getDamage();
            int maxDurability = equippedElytra.getMaxDamage();
            int durabilityPercent = (durability * 100) / maxDurability;
            
            if (durabilityPercent <= durabilityThreshold.get()) {
                // Procura uma elytra nova no inventário
                int newElytraSlot = findElytraInInventory();
                
                if (newElytraSlot != -1) {
                    swapElytra(newElytraSlot);
                    info("Elytra trocada! Durabilidade anterior: " + durabilityPercent + "%");
                }
            }
        }
    }

    private int findElytraInInventory() {
        // Procura no inventário (slots 0-26, slot 6 é o chest)
        for (int i = 0; i < mc.player.getInventory().main.size(); i++) {
            ItemStack stack = mc.player.getInventory().main.get(i);
            
            if (stack.getItem() == Items.ELYTRA && stack.getDamage() < stack.getMaxDamage() - 1) {
                return i;
            }
        }
        return -1;
    }

    private void swapElytra(int inventorySlot) {
        if (mc.interactionManager == null) return;
        
        // Converte slot do inventário para slot da janela
        int windowSlot = inventorySlot + 9; // Slots 0-8 são a hotbar, 9+ são o inventário
        
        // Clica na elytra nova (pick up)
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, windowSlot, 0, SlotActionType.PICKUP, mc.player);
        
        // Clica no slot do chest (put down)
        mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 6, 0, SlotActionType.PICKUP, mc.player);
    }

    @Override
    public void onDeactivate() {
        super.onDeactivate();
    }
}
