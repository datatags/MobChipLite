package me.gamercoder215.mobchip.ai.goal;

import org.jetbrains.annotations.ApiStatus;

/**
 * Represents information about a Pathfinder
 */
public interface PathfinderInfo {

    /**
     * Returns this Pathfinder's Name.
     * @return Pathfinder Name
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Returns the pathfinder's internal name. This may match the actual NMS name of the pathfinder, but it may not.
     * @return Internal Name
     */
    @ApiStatus.Internal
    String getInternalName();

}
