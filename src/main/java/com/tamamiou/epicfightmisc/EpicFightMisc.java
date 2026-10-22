package com.tamamiou.epicfightmisc;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

/**
 * Epic Fight Misc Compatibility
 * <p>
 * 把其他模组的武器与护甲接入 Epic Fight：
 * 1) 内置数据包提供武器动作与护甲能力；
 * 2) 代码层处理数据包做不到的部分（夜幕缺席时的武器预设回退）。
 */
@Mod(EpicFightMisc.MODID)
public class EpicFightMisc {

    public static final String MODID = "epicfight_misc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EpicFightMisc(IEventBus modEventBus) {
        // 夜幕缺席时给使用夜幕动作组的武器补一份原生预设（两侧都需要）
        WeaponPresetFallback.register();
        LOGGER.info("[Epic Fight Misc] loaded");
    }
}
