package me.gamercoder215.mobchip.abstraction.v1_21_R4;

import me.gamercoder215.mobchip.nbt.EntityNBT;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

final class EntityNBT1_21_R4 extends NBTSection1_21_R4 implements EntityNBT {

    private final Mob mob;

    public EntityNBT1_21_R4(Mob m) {
        super(m);
        this.mob = m;
        net.minecraft.world.entity.Mob handle = ChipUtil1_21_R4.toNMS(m);
        CompoundTag root = new CompoundTag();
        handle.saveWithoutId(root);
    }

    @Override
    public @NotNull Mob getEntity() {
        return mob;
    }
}
