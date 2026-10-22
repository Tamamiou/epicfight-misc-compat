# Epic Fight Misc Compatibility

把多个模组的武器与护甲接入 Epic Fight（史诗战斗）的兼容模组。

- **唯一的硬依赖是 Epic Fight**：其余模组全部是可选的软联动，不装也能正常启动。
- 兼容数据直接打包在 jar 内，玩家不需要额外安装数据包。

## 功能

### 内置数据包

| 模组 | 内容 |
|---|---|
| 灾变（Cataclysm） | 33 件武器动作、14 件护甲能力（韧性 / 重量） |
| 井然有矿（Oreganized） | 琥珀金 / 银质护甲的护甲能力 |
| 农夫乐事（Farmer's Delight） | 刀、煎锅、西瓜冰棒等武器动作 |
| 农夫乐事附属 | 樱途旅事、暮色风味乐事、末地乐事、血族乐事、借出乐事的 21 把刀 |

### 代码修复

**井然有矿的护甲在战斗模式下槽位错位**（护腿像头盔、靴子像胸甲）。

- 与数据包无关，删掉所有数据包仍然存在；
- 原因：Oreganized 的 `ElectrumArmorModel` / `SilverArmorModel` 是自带 `HumanoidModel`，不会按装备槽隐藏其它部位，Epic Fight 转成 `SkinnedMesh` 时会保留全部几何；
- 修法：用 `WearableItemLayer.putModel(itemId, Meshes.<slot>.get())` 为这些物品显式登记 Epic Fight 自带的网格；
- 代价：这些护甲在战斗模式下改用 Epic Fight 的默认造型，贴图仍是 Oreganized 自己的。

### 软联动：夜幕（NightFall）

检测到夜幕时，部分灾变武器自动套用它的动作组和 AAA Particles 特效：

| 武器 | 动作组 |
|---|---|
| 碎肉机 Meat Shredder | `efn:thornwheel`（Ghiza's Wheel） |
| 歼灭者 / 炼狱熔炉 | `efn:ruinsgreatsword` |
| 焚化者 | `efn:exsiliumgladius` |
| Zweiender | `efn:broadblade` |
| 灵魂撕裂 | `efn:crescentmoon` |
| 潮汐之爪 + 三款 Gauntlet | `efn:beastclaw` |
| Brontes / Ceraunus / Astrape | `efn:meenlance` |

未安装夜幕时，这些武器的夜幕动作组不生效，回退为原版攻击方式（其余武器不受影响）。

## 构建

```bash
# 需要 JDK 21
set JAVA_HOME=C:\Program Files\Java\java21
./gradlew build
```

> 编译前把 **Epic Fight 的 jar** 放到 `libs/epicfight.jar`（仓库不含该文件）。

构建产物会自动复制到 `Documents\Project\MY MOD\Epic Fight Misc Compatibility\`（jar + logo）。

## 依赖

| 模组 | 类型 |
|---|---|
| Epic Fight | 必需 `[21.14.0,)` |
| 井然有矿 / 农夫乐事 / 灾变 / 夜幕 / AAA Particles | 可选 |

## 许可

All Rights Reserved
