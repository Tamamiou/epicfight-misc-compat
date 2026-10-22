# Epic Fight Misc Compatibility

Epic Fight（史诗战斗）的**杂项兼容**模组：把“除匠魂以外”的兼容内容集中到一个 jar 里。

匠魂（Tinkers' Construct）相关的兼容仍然由 [Tinker Also Soul](https://github.com/Tamamiou/tinker-also-soul) 负责，两者互不重叠。

## 它做两件事

### 1. 硬编数据包

兼容数据直接打包在 `src/main/resources/data/` 下，玩家不需要额外装数据包：

| 命名空间 | 内容 |
|---|---|
| `oreganized` | 琥珀金 / 银质护甲的 Epic Fight 护甲能力（`stun_armor`、`weight`） |
| `farmersdelight` | 各类刀、煎锅、西瓜冰棒的武器动作（`epicfight:dagger` / `epicfight:sword` 等） |

### 2. 数据包做不到的代码修复

**Oreganized 护甲在战斗模式下的槽位错位**（护腿像头盔、靴子像胸甲）。

- 现象与数据包无关：删掉所有数据包仍然存在；
- 原因：Oreganized 的 `ElectrumArmorModel` / `SilverArmorModel` 是自带 `HumanoidModel`，不会按装备槽隐藏其它部位，Epic Fight 把它们转成 `SkinnedMesh` 时会保留全部几何；
- 修法：用 `WearableItemLayer.putModel(itemId, Meshes.<slot>.get())` 为这些物品显式登记 Epic Fight 自带的网格，绕过自动转换（`src/main/java/.../OreganizedArmorCompat.java`）；
- 代价：这些护甲在战斗模式下改用 Epic Fight 的默认护甲造型，贴图仍是 Oreganized 自己的。

## 构建

```bash
# 需要 JDK 21
set JAVA_HOME=C:\Program Files\Java\java21        # Windows
./gradlew build
```

> 编译前请把 **Epic Fight 的 jar** 放到 `libs/epicfight.jar`（仓库不包含该文件）。
> 也可以改成从 Modrinth Maven 依赖，见 `build.gradle` 里的 `repositories`。

产物：`build/libs/epicfight_misc-<version>.jar`，丢进 `mods` 即可。

## 依赖

| 模组 | 类型 | 说明 |
|---|---|---|
| Epic Fight | 必需 | `[21.14.0,)` |
| Oreganized | 可选 | 不装时护甲修复自动跳过 |
| Farmer's Delight | 可选 | 不装时武器动作数据自然失效 |

## 目录结构

```
src/main/
├─ java/com/tamamiou/epicfightmisc/
│  ├─ EpicFightMisc.java            主类
│  └─ OreganizedArmorCompat.java    护甲网格修复
├─ resources/data/                  内置数据包
└─ templates/META-INF/neoforge.mods.toml
```

## 许可

All Rights Reserved
