# Epic Fight Misc Compatibility

把其他模组的武器与护甲接入 Epic Fight（史诗战斗）的兼容模组。

- **唯一的硬依赖是 Epic Fight**：其余模组全部是可选的软联动，不装也能正常启动。
- 兼容数据直接打包在 jar 内，玩家不需要额外安装数据包。

> ## ⚠️ 测试阶段
>
> **灾变（Cataclysm）兼容目前处于测试阶段**，武器动作、攻击判定、右键技能等数值还在调整，
> 可能出现动作错位、特效缺失甚至崩溃。
>
> 遇到任何 BUG 请提交给我（附上物品名、现象和 `crash-report` / `latest.log`），我会尽快修。

## 功能

### 内置数据包

| 模组 | 内容 |
|---|---|
| 灾变（Cataclysm） | 33 件武器动作、14 件护甲能力（韧性 / 重量）；碎肉机与焚化者用巨剑动作，右键保留原版技能 |
| 农夫乐事（Farmer's Delight） | 刀、煎锅、西瓜冰棒等武器动作 |
| 农夫乐事附属 | 樱途旅事、暮色风味乐事、末地乐事、血族乐事、借出乐事的 21 把刀 |
| 法术与法术书（Farmer's Spell 'n Spellbook） | 4 把刀、汤勺、鸢尾叉、暮光刀锋、魔爪 |
| 森罗厨房：下界（Kaleidoscope Nether） | 原始菜刀、吹箭筒 |

### 软联动：夜幕（NightFall）

检测到夜幕时，部分灾变武器自动套用它的动作组和 AAA Particles 特效：

| 武器 | 动作组 |
|---|---|
| 歼灭者 / 炼狱熔炉 | `efn:ruinsgreatsword` |
| Zweiender | `efn:broadblade` |
| 灵魂撕裂 | `efn:crescentmoon` |
| 潮汐之爪 + 三款 Gauntlet | `efn:beastclaw` |
| Brontes / Ceraunus / Astrape | `efn:meenlance` |

未安装夜幕时，这些武器由模组内置的同名预设回退到 Epic Fight 原生动作（拳套 / 巨剑 / 镰刀 / 长矛），不会退化成原版攻击。

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
| 农夫乐事 / 法术与法术书 / 森罗厨房：下界 / 灾变 / 夜幕 / AAA Particles | 可选 |

## 许可

All Rights Reserved
