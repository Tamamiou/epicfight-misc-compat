package com.tamamiou.epicfightmisc;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.skill.guard.GuardSkill;

/**
 * 让灾变的「碎肉机」「焚化者」在战斗模式下保留原版右键。
 * <p>
 * 这两件武器的右键是模组自己的技能（刺轮旋转 / 火焰法术），
 * 但 Epic Fight 会把右键映射成格挡（GUARD），于是播放防御动作、
 * 并且会打断原版使用动作导致地面法阵之类的特效不渲染。
 * <p>
 * 做法：拦截 Epic Fight 的技能施放请求，只要手持这两件武器就把格挡取消，
 * 右键交还给物品自身的 {@code use()}。
 */
public final class CataclysmUseCompat {

    private CataclysmUseCompat() {
    }

    public static void register() {
        if (!ModList.get().isLoaded("cataclysm")) {
            return;
        }

        EpicFightEventHooks.Player.CAST_SKILL.registerEvent(event -> {
            if (!(event.getSkillContainer().getSkill() instanceof GuardSkill)) {
                return;
            }
            ItemStack stack = event.getPlayerPatch().getOriginal().getMainHandItem();
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            if (id == null || !"cataclysm".equals(id.getNamespace())) {
                return;
            }
            String path = id.getPath();
            if ("meat_shredder".equals(path) || "the_incinerator".equals(path)) {
                event.cancel();
                EpicFightMisc.LOGGER.debug("[Epic Fight Misc] cancelled guard for {}, using item instead", path);
            }
        });
    }
}
