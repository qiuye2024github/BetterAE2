package cn.qiuye.betterae2.mixin.eae2;

import cn.qiuye.betterae2.common.definition.BAE2Items;

import appeng.api.networking.IGridNode;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.storage.StorageCells;
import appeng.api.upgrades.IUpgradeableObject;
import appeng.api.upgrades.UpgradeInventories;
import appeng.blockentity.storage.IOPortBlockEntity;
import appeng.core.definitions.AEItems;
import appeng.util.inv.AppEngInternalInventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.glodblock.github.extendedae.common.tileentities.TileExIOPort;
import com.glodblock.github.extendedae.util.Ae2Reflect;
import com.glodblock.github.glodium.util.GlodUtil;
import org.spongepowered.asm.mixin.*;

@Mixin(TileExIOPort.class)
public class TileExIOPortMixin extends IOPortBlockEntity implements IUpgradeableObject {

    @Mutable
    @Final
    @Shadow(remap = false)
    private AppEngInternalInventory inputCells;

    @Final
    @Shadow(remap = false)
    private static int NUMBER_OF_CELL_SLOTS;

    public TileExIOPortMixin(BlockPos pos, BlockState blockState) {
        super(GlodUtil.getTileType(TileExIOPort.class, TileExIOPort::new, EPPItemAndBlock.EX_IO_PORT), pos, blockState);
        Ae2Reflect.setIOPortUpgrade(this, UpgradeInventories.forMachine(EPPItemAndBlock.EX_IO_PORT, 5, this::saveChanges));
        this.inputCells = Ae2Reflect.getInputCellInv(this);
    }

    /**
     * @author ~
     * @reason .
     */
    @Overwrite(remap = false)
    public TickRateModulation tickingRequest(IGridNode node, int ticksSinceLastCall) {
        if (!this.getMainNode().isActive()) {
            return TickRateModulation.IDLE;
        }

        TickRateModulation ret = TickRateModulation.SLEEP;

        int speedUpgrades = this.getUpgrades().getInstalledUpgrades(AEItems.SPEED_CARD);
        int superSpeedUpgrades = this.getUpgrades().getInstalledUpgrades(BAE2Items.SUPER_SPEED_CARD);
        long itemsToMove = 8192;

        if (speedUpgrades > 0 && superSpeedUpgrades == 0) {
            switch (speedUpgrades) {
                case 1 -> itemsToMove *= 2;
                case 2 -> itemsToMove *= 8;
                case 3 -> itemsToMove *= 32;
                case 4 -> itemsToMove *= 128;
                case 5 -> itemsToMove *= 512;
            }
        } else if (superSpeedUpgrades > 0 && speedUpgrades == 0) {
            switch (superSpeedUpgrades) {
                case 1 -> itemsToMove *= 512;
                case 2 -> itemsToMove *= 1024;
                case 3 -> itemsToMove *= 2048;
                case 4 -> itemsToMove *= 4096;
                case 5 -> itemsToMove *= 8192;
            }
        } else if (speedUpgrades > 0 && superSpeedUpgrades > 0) {
            switch (speedUpgrades) {
                case 1 -> itemsToMove *= 256;
                case 2 -> itemsToMove *= 512;
                case 3 -> itemsToMove *= 1024;
                case 4 -> itemsToMove *= 2048;
                case 5 -> itemsToMove *= 4096;
            }
        }

        var grid = getMainNode().getGrid();
        if (grid == null) {
            return TickRateModulation.IDLE;
        } else {
            for (int x = 0; x < NUMBER_OF_CELL_SLOTS; x++) {
                var cell = this.inputCells.getStackInSlot(x);
                var cellInv = StorageCells.getCellInventory(cell, null);
                if (cellInv == null) {
                    Ae2Reflect.moveSlotInCell(this, x);
                    continue;
                }
                if (itemsToMove > 0) {

                    itemsToMove = Ae2Reflect.transferItemsFromCell(this, grid, cellInv, itemsToMove);

                    if (itemsToMove > 0) {
                        ret = TickRateModulation.IDLE;
                    } else {
                        ret = TickRateModulation.URGENT;
                    }
                }
                if (itemsToMove > 0 && matchesFullnessMode(cellInv) && Ae2Reflect.moveSlotInCell(this, x)) {
                    ret = TickRateModulation.URGENT;
                }
            }
        }
        return ret;
    }
}
