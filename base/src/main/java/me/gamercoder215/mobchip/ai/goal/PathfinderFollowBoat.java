package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a Pathfinder for a Creature to follow a Boat
 * @deprecated Superseded by PathfinderFollowPlayerRiddenEntity
 */
@Deprecated
public final class PathfinderFollowBoat extends PathfinderFollowPlayerRiddenEntity {
    /**
     * Constructs a PathfinderFollowBoat.
     * @param c Creature to use
     */
    public PathfinderFollowBoat(@NotNull Creature c) {
        super(c, Boat.class);
    }
}
