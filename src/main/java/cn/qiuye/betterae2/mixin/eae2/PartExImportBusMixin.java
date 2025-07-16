package cn.qiuye.betterae2.mixin.eae2;

import appeng.api.parts.IPartItem;
import appeng.parts.automation.ImportBusPart;

import com.glodblock.github.extendedae.common.parts.PartExImportBus;
import com.glodblock.github.extendedae.config.EPPConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PartExImportBus.class)
public class PartExImportBusMixin extends ImportBusPart {

    public PartExImportBusMixin(IPartItem<?> partItem) {
        super(partItem);
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    protected int getOperationsPerTick() {
        int EAEConfig = EPPConfig.busSpeed;
        int result = super.getOperationsPerTick();
        int BAE2Config = 4096;
        return EAEConfig * result * BAE2Config;
    }
}
