package com.tamamiou.epicfightmisc;

import java.util.Map;
import java.util.function.Function;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.registry.deferred.holders.DeferredWeapon;
import yesman.epicfight.registry.entries.EpicFightItemCapabilityPresets;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

/**
 * 夜幕（NightFall / efn）缺席时的动作回退。
 * <p>
 * 灾变的部分武器在数据里写成 {@code "type": "efn:thornwheel"} 这类夜幕动作组。
 * 若玩家没装夜幕，Epic Fight 解析不到该类型，这些武器在战斗模式下就会退化成原版攻击。
 * <p>
 * 这里在夜幕缺席时，用同名的预设 id 注册一份 Epic Fight 原生动作，
 * 让这些武器至少还能保留战斗动作；装回夜幕时本类不注册任何内容，
 * 由夜幕自己的 {@code EFNWeaponCapabilityPresets} 接管。
 */
public final class WeaponPresetFallback {

    private WeaponPresetFallback() {
    }

    public static void register() {
        EpicFightEventHooks.Registry.WEAPON_CAPABILITY_PRESET.registerEvent(event -> {
            if (ModList.get().isLoaded("efn")) {
                return;
            }

            @SuppressWarnings("unchecked")
            Map<ResourceLocation, Function<Item, CapabilityItem.Builder<?>>> types =
                    (Map<ResourceLocation, Function<Item, CapabilityItem.Builder<?>>>) (Map<?, ?>) event.getTypeEntry();

            fallback(types, "efn:beastclaw", EpicFightItemCapabilityPresets.FIST);
            fallback(types, "efn:ruinsgreatsword", EpicFightItemCapabilityPresets.GREATSWORD);
            fallback(types, "efn:broadblade", EpicFightItemCapabilityPresets.GREATSWORD);
            fallback(types, "efn:crescentmoon", EpicFightItemCapabilityPresets.HOE);
            fallback(types, "efn:meenlance", EpicFightItemCapabilityPresets.SPEAR);

            EpicFightMisc.LOGGER.info("[Epic Fight Misc] NightFall not found, registered 5 fallback weapon presets");
        });
    }

    private static void fallback(Map<ResourceLocation, Function<Item, CapabilityItem.Builder<?>>> types,
                                 String id, DeferredWeapon preset) {
        types.put(ResourceLocation.parse(id), item -> preset.value());
    }
}
