package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a pathfinder used by Wandering Traders to look at the player. Closely related to {@link PathfinderLookAtEntity}
 * @param <T> Type of LivingEntity to look at
 */
public final class PathfinderInteract<T extends LivingEntity> extends PathfinderLookAtEntity<T> {
    /**
     * Constructs a PathfinderInteract
     * @param entity Mob to use
     * @param filter Filter class to look at
     * @param lookRange Range of blocks to find selected class filter
     * @param probability Probability (0.0 - 1.0) to look at something. <strong>Called every tick, recommended to be a low number. See {@link #DEFAULT_PROBABILITY}.</strong>
     */
    public PathfinderInteract(@NotNull Mob entity, Class<T> filter, float lookRange, float probability) {
        super(entity, filter, lookRange, probability);
    }

    /**
     * Constructs a PathfinderInteract
     * @param entity Mob to use
     * @param filter Filter class to look at
     * @param lookRange Range of blocks to find selected class filter
     */
    public PathfinderInteract(@NotNull Mob entity, Class<T> filter, float lookRange) {
        super(entity, filter, lookRange);
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.LOOKING, PathfinderFlag.MOVEMENT };
    }

    @Override
    public String getInternalName() {
        return "Interact";
    }
}
