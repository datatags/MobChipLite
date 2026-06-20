package me.gamercoder215.mobchip.abstraction.v26_1;

import me.gamercoder215.mobchip.ai.memories.Memory;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import me.gamercoder215.mobchip.util.StackTraceLogger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SensorDefault26_1 implements Sensor<LivingEntity> {
    protected final ChipUtil26_1 wrapper = ChipUtil26_1.instance();
    private final net.minecraft.world.entity.ai.sensing.Sensor<?> handle;

    public SensorDefault26_1(net.minecraft.world.entity.ai.sensing.Sensor<?> handle) {
        this.handle = handle;
    }

    public net.minecraft.world.entity.ai.sensing.Sensor<?> getHandle() {
        return handle;
    }

    @Override
    public @NotNull List<Memory<?>> required() {
        return handle.requires().stream().map(wrapper::fromNMS).collect(Collectors.toList());
    }

    @Override
    public int getScanRate() {
        try {
            Field scan = net.minecraft.world.entity.ai.sensing.Sensor.class.getDeclaredField("scanRate");
            scan.setAccessible(true);
            return scan.getInt(handle);
        } catch (ReflectiveOperationException e) {
            StackTraceLogger.printStackTrace(e);
        }

        return DEFAULT_SCAN_RATE;
    }

    @Override
    public @NotNull Class<LivingEntity> getEntityClass() {
        return LivingEntity.class; // not stored in the handle
    }

    @Override
    public void run(@NotNull World w, LivingEntity entity) {
        try {
            Method doTick = net.minecraft.world.entity.ai.sensing.Sensor.class.getDeclaredMethod("doTick", ServerLevel.class, net.minecraft.world.entity.LivingEntity.class);
            doTick.setAccessible(true);
            doTick.invoke(handle, wrapper.toNMS(w), wrapper.toNMS(entity));
        } catch (ReflectiveOperationException e) {
            StackTraceLogger.printStackTrace(e);
        }
    }

    @NotNull
    @Override
    public NamespacedKey getKey() {
        AtomicReference<NamespacedKey> key = new AtomicReference<>(NamespacedKey.minecraft("unknown"));

        BuiltInRegistries.SENSOR_TYPE.stream()
                .filter(s -> s.create().equals(handle))
                .findFirst()
                .ifPresent(s -> key.set(wrapper.fromNMS(BuiltInRegistries.SENSOR_TYPE.getKey(s))));

        return key.get();
    }
}
