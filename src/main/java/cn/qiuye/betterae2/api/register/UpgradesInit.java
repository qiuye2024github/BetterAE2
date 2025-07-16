package cn.qiuye.betterae2.api.register;

import cn.qiuye.betterae2.common.definition.BAE2Items;

import appeng.api.upgrades.Upgrades;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEParts;
import appeng.core.localization.GuiText;

public class UpgradesInit {

    public static void init() {
        String itemIoBusGroup = GuiText.IOBuses.getTranslationKey();

        Upgrades.add(BAE2Items.SUPER_SPEED_CARD, AEBlocks.IO_PORT, 3);

        Upgrades.add(BAE2Items.SUPER_SPEED_CARD, AEParts.IMPORT_BUS, 4, itemIoBusGroup);
        Upgrades.add(BAE2Items.SUPER_SPEED_CARD, AEParts.EXPORT_BUS, 4, itemIoBusGroup);
    }
}
