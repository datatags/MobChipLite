package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to follow something a player is riding
 */
public /* final */ class PathfinderFollowPlayerRiddenEntity extends Pathfinder {
    private final Class<? extends Entity> typeToFollow;
    /**
     * Constructs a PathfinderFollowBoat.
     * @param c Creature to use
     * @param typeToFollow The type of entity to follow. On 1.21.11 and lower, Boat.class will be used regardless of
     *                     this setting.
     */
    public PathfinderFollowPlayerRiddenEntity(@NotNull Creature c, Class<? extends Entity> typeToFollow) {
        super(c);
        this.typeToFollow = typeToFollow;
    }

    /**
     * Get the type of entity this pathfinder follows.
     * @return The entity type.
     */
    public Class<? extends Entity> getTypeToFollow() {
        return typeToFollow;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[0];
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalFollowPlayerRiddenEntity";
    }
}
