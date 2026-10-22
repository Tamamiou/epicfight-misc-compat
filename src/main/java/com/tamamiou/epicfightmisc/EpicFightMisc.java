package com.tamamiou.epicfightmisc;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

/**
 * Epic Fight Misc Compatibility
 * <p>
 * 汇集"除匠魂以外"的 Epic Fight 兼容内容：
 * 1) 内置数据包（Oreganized 护甲能力、农夫乐事武器动作……）；
 * 2) 数据包做不到的代码修复（例如自定义护甲模型在战斗模式下按槽位错位）。
 */
@Mod(EpicFightMisc.MODID)
public class EpicFightMisc {

    public static final String MODID = "epicfight_misc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EpicFightMisc(IEventBus modEventBus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(this::clientSetup);
        }
        LOGGER.info("[Epic Fight Misc] loaded");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(OreganizedArmorCompat::register);
    }
}
