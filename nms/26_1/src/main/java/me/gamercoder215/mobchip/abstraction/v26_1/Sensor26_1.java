package me.gamercoder215.mobchip.abstraction.v26_1;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Set;
import java.util.stream.Collectors;

public class Sensor26_1 extends Sensor<LivingEntity> {
    protected final ChipUtil26_1 wrapper = ChipUtil26_1.instance();
    private final me.gamercoder215.mobchip.ai.sensing.Sensor<?> s;

    public Sensor26_1(me.gamercoder215.mobchip.ai.sensing.Sensor<?> s) {
        this.s = s;
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity en) {
        s.run(wrapper.fromNMS(level), wrapper.fromNMS(en));
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return s.required().stream().map(wrapper::toNMS).collect(Collectors.toSet());
    }

    public me.gamercoder215.mobchip.ai.sensing.Sensor<?> getSensor() {
        return s;
    }

}
