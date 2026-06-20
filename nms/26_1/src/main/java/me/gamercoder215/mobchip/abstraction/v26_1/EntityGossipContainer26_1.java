package me.gamercoder215.mobchip.abstraction.v26_1;

import me.gamercoder215.mobchip.ai.gossip.EntityGossipContainer;
import me.gamercoder215.mobchip.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import org.bukkit.craftbukkit.entity.CraftVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EntityGossipContainer26_1 implements EntityGossipContainer {
    protected final ChipUtil26_1 wrapper = ChipUtil26_1.instance();
    private final GossipContainer handle;
    private final Villager entity;

    public EntityGossipContainer26_1(Villager v) {
        this.entity = v;
        this.handle = ((CraftVillager) v).getHandle().getGossips();
    }

    @Override
    public @NotNull Villager getEntity() {
        return entity;
    }

    @Override
    public void decay() {
        handle.decay();
    }

    @Override
    public int getReputation(@NotNull Entity en, @Nullable GossipType... types) throws IllegalArgumentException {
        return handle.getReputation(en.getUniqueId(), g -> Arrays.asList(types).contains(wrapper.fromNMS(g)));
    }

    @Override
    public void put(@NotNull Entity en, @NotNull GossipType type, int maxCap) throws IllegalArgumentException {
        handle.add(en.getUniqueId(), wrapper.toNMS(type), maxCap, Villager.ReputationEvent.UNSPECIFIED);
    }

    @Override
    public void remove(@NotNull Entity en, @NotNull GossipType type) throws IllegalArgumentException {
        handle.remove(en.getUniqueId(), wrapper.toNMS(type), Villager.ReputationEvent.UNSPECIFIED);
    }

    @Override
    public void removeAll(@NotNull GossipType type) throws IllegalArgumentException {
        handle.remove(wrapper.toNMS(type), Villager.ReputationEvent.UNSPECIFIED);
    }
}
