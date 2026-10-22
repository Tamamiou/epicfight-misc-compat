package com.tamamiou.epicfightmisc;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.fml.ModList;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.renderer.patched.layer.WearableItemLayer;

/**
 * 修复 Oreganized 护甲在 Epic Fight 战斗模式下的槽位错位。
 * <p>
 * 现象：护腿看起来像头盔、靴子看起来像胸甲（与数据包无关，删掉所有数据包依旧）。
 * <p>
 * 原因：Oreganized 的琥珀金 / 银质护甲使用自带模型
 * （{@code galena.oreganized.electrum.client.model.ElectrumArmorModel} 等），
 * 这类自定义 HumanoidModel 不会按装备槽隐藏其它部位；
 * Epic Fight 的 {@code WearableItemLayer} 把它转成 SkinnedMesh 时保留了全部部位，
 * 于是胸部/头部几何被一起画到了腿部。
 * <p>
 * 修法：在 {@code ARMOR_MODELS} 里为这些物品显式登记 Epic Fight 自带的对应槽位网格，
 * {@code getArmorModel(...)} 会优先取这张表，从而绕过自动转换。
 * 代价：这些护甲在战斗模式下改用 Epic Fight 的默认护甲造型（贴图仍是 Oreganized 的）。
 */
public final class OreganizedArmorCompat {

    private OreganizedArmorCompat() {
    }

    public static void register() {
        if (!ModList.get().isLoaded("oreganized")) {
            return;
        }

        int count = 0;
        for (Item item : BuiltInRegistries.ITEM) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            if (!"oreganized".equals(id.getNamespace()) || !(item instanceof ArmorItem armor)) {
                continue;
            }
            switch (armor.getType()) {
                case HELMET -> WearableItemLayer.putModel(id, Meshes.HELMET.get());
                case CHESTPLATE -> WearableItemLayer.putModel(id, Meshes.CHESTPLATE.get());
                case LEGGINGS -> WearableItemLayer.putModel(id, Meshes.LEGGINS.get());
                case BOOTS -> WearableItemLayer.putModel(id, Meshes.BOOTS.get());
            }
            count++;
        }
        EpicFightMisc.LOGGER.info("[Epic Fight Misc] registered {} oreganized armor meshes", count);
    }
}
