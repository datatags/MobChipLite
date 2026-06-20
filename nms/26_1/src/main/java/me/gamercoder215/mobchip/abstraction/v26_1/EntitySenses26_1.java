package me.gamercoder215.mobchip.abstraction.v26_1;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.mobchip.ai.sensing.EntitySenses;
import me.gamercoder215.mobchip.ai.sensing.Sensor;
import me.gamercoder215.mobchip.util.StackTraceLogger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class EntitySenses26_1 implements EntitySenses {
    protected final ChipUtil26_1 wrapper = ChipUtil26_1.instance();
    private final Mob m;
    private final net.minecraft.world.entity.Mob nmsMob;

    private final Map<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> sensorsHandle = new HashMap<>();

    public EntitySenses26_1(Mob m) {
        this.m = m;
        this.nmsMob = wrapper.toNMS(m);

        try {
            Field sensorsF = Brain.class.getDeclaredField("sensors");
            sensorsF.setAccessible(true);
            var sensors = (Map<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>>) sensorsF.get(nmsMob.getBrain());

            sensorsHandle.putAll(sensors);
        } catch (ReflectiveOperationException e) {
            StackTraceLogger.printStackTrace(e);
        }
    }

    private void save() {
        try {
            Field sensorsF = Brain.class.getDeclaredField("sensors");
            sensorsF.setAccessible(true);

            sensorsF.set(nmsMob.getBrain(), sensorsHandle);
        } catch (ReflectiveOperationException e) {
            StackTraceLogger.printStackTrace(e);
        }
    }

    @Override
    public @NotNull Mob getEntity() {
        return m;
    }

    @Override
    public @NotNull List<Sensor<?>> getSensors() {
        return sensorsHandle.values()
                .stream()
                .map(wrapper::fromNMS)
                .collect(Collectors.toList());
    }

    @Override
    public void addSensor(@NotNull Sensor<?> sensor) throws IllegalArgumentException {
        if (!wrapper.existsSensor(sensor.getKey())) throw new IllegalArgumentException("Unregistered Sensor: " + sensor.getKey());

        sensorsHandle.put(wrapper.toNMSType(sensor), wrapper.toNMS(sensor));
        save();
    }

    @Override
    public void removeSensor(@NotNull Sensor<?> sensor) {
        if (!wrapper.existsSensor(sensor.getKey())) throw new IllegalArgumentException("Unregistered Sensor: " + sensor.getKey());
        removeSensor(sensor.getKey());
    }

    @Override
    public void removeSensor(@NotNull NamespacedKey key) {
        if (!wrapper.existsSensor(key)) throw new IllegalArgumentException("Unregistered Sensor: " + key);

        Identifier keyH = wrapper.toNMS(key);
        Iterator<Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>>> it = sensorsHandle.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<SensorType<?>, net.minecraft.world.entity.ai.sensing.Sensor<?>> entry = it.next();
            SensorType<?> currentType = entry.getKey();
            Identifier currentKey = BuiltInRegistries.SENSOR_TYPE.getKey(currentType);

            if (currentKey.equals(keyH)) {
                it.remove();
                break;
            }
        }

        sensorsHandle.clear();
        sensorsHandle.putAll(ImmutableList.copyOf(it)
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        save();
    }

    @Override
    public boolean hasSensor(@NotNull NamespacedKey key) {
        AtomicBoolean b = new AtomicBoolean(false);

        for (SensorType<?> t : sensorsHandle.keySet()) {
            Identifier currentKey = BuiltInRegistries.SENSOR_TYPE.getKey(t);
            if (wrapper.toNMS(key).equals(currentKey)) {
                b.set(true);
                break;
            }
        }

        return b.get();
    }
}
