package me.gamercoder215.mobchip.abstraction.v26_1;

import me.gamercoder215.mobchip.ai.behavior.BehaviorResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BehaviorResult26_1 extends BehaviorResult {
    protected final ChipUtil26_1 wrapper = ChipUtil26_1.instance();
    private final BehaviorControl b;
    private final LivingEntity mob;
    private final ServerLevel l;

    public <T extends LivingEntity> BehaviorResult26_1(BehaviorControl<T> b, T mob) {
        this.b = b;
        this.mob = mob;
        this.l = wrapper.toNMS(Bukkit.getWorld(mob.level().getWorld().getUID()));

        b.tryStart(l, mob, 0);
    }

    @Override
    public @NotNull Status getStatus() {
        return wrapper.fromNMS(b.getStatus());
    }

    @Override
    public void stop() {
        b.doStop(l, mob, 0);
    }
}
