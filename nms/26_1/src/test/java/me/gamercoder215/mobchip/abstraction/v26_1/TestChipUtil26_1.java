package me.gamercoder215.mobchip.abstraction.v26_1;

import me.gamercoder215.mobchip.ai.gossip.GossipType;
import me.gamercoder215.mobchip.util.OptimizedSmallEnumSet;
import net.minecraft.DetectedVersion;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.Difficulty;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

public class TestChipUtil26_1 {

    @BeforeAll
    public static void init() {
        SharedConstants.setVersion(DetectedVersion.BUILT_IN);

        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Test Bukkit-NMS Conversion")
    public void testNMSConversion() {
        // Entities
        for (EntityType t : EntityType.values()) {
            if (t.getEntityClass() == null) {
                continue;
            }
            Assertions.assertNotNull(ChipUtil26_1.toNMS(t.getEntityClass()));
        }

        // Other
        for (Difficulty d : Difficulty.values()) Assertions.assertNotNull(ChipUtil26_1.toNMS(d));
        for (GossipType t : GossipType.values()) Assertions.assertNotNull(ChipUtil26_1.toNMS(t));

        Assertions.assertNotNull(ChipUtil26_1.toNMS(m -> m.damage(2)));
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Test NMS-Bukkit Conversion")
    public void testBukkitConversion() {
        // For some reason if we read the registry directly, the type arguments are not available, but if we iterate
        // over the fields directly, they are.
        for (Field field : net.minecraft.world.entity.EntityType.class.getDeclaredFields()) {
            if (field.getType() != net.minecraft.world.entity.EntityType.class) {
                continue;
            }
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Assertions.assertNotNull(ChipUtil26_1.fromNMS((Class<? extends Entity>)type.getActualTypeArguments()[0], org.bukkit.entity.Entity.class));
        }

        // Other
        for (net.minecraft.world.Difficulty d : net.minecraft.world.Difficulty.values()) Assertions.assertNotNull(ChipUtil26_1.fromNMS(d));
        for (net.minecraft.world.entity.ai.gossip.GossipType t : net.minecraft.world.entity.ai.gossip.GossipType.values()) Assertions.assertNotNull(ChipUtil26_1.fromNMS(t));
    }

    @Test
    @DisplayName("Test ChipUtil26_1#getFlags")
    public void testGetFlags() {
        OptimizedSmallEnumSet<Goal.Flag> set = new OptimizedSmallEnumSet<>(Goal.Flag.class);
        set.addUnchecked(Goal.Flag.MOVE);
        set.addUnchecked(Goal.Flag.LOOK);

        Assertions.assertTrue(set.hasElement(Goal.Flag.MOVE));
        Assertions.assertTrue(set.hasElement(Goal.Flag.LOOK));

        Set<Goal.Flag> flags = ChipUtil26_1.getFlags(set.getBackingSet());
        Assertions.assertTrue(flags.contains(Goal.Flag.MOVE));
        Assertions.assertTrue(flags.contains(Goal.Flag.LOOK));
    }

}
