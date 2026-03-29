package me.gamercoder215.mobchip.ai.goal;

import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Pathfinder for using a spear
 */
public class PathfinderSpearUse extends Pathfinder {
    private double speedModifierWhenCharging;
    private double speedModifierWhenRepositioning;
    private float approachDistance;
    private float targetInRangeRadius;

    /**
     * Constructs a Pathfinder.
     *
     * @param entity Entity to use
     */
    public PathfinderSpearUse(@NotNull Mob entity, double speedModifierWhenCharging, double speedModifierWhenRepositioning, float approachDistance, float targetInRangeRadius) {
        super(entity);
        this.speedModifierWhenCharging = speedModifierWhenCharging;
        this.speedModifierWhenRepositioning = speedModifierWhenRepositioning;
        this.approachDistance = approachDistance;
        this.targetInRangeRadius = targetInRangeRadius;
    }

    @Override
    public @NotNull PathfinderFlag[] getFlags() {
        return new PathfinderFlag[] { PathfinderFlag.MOVEMENT, PathfinderFlag.LOOKING };
    }

    @Override
    public String getInternalName() {
        return "PathfinderGoalSpearUse";
    }

    public double getSpeedModifierWhenCharging() {
        return speedModifierWhenCharging;
    }

    public void setSpeedModifierWhenCharging(double speedModifierWhenCharging) {
        this.speedModifierWhenCharging = speedModifierWhenCharging;
    }

    public double getSpeedModifierWhenRepositioning() {
        return speedModifierWhenRepositioning;
    }

    public void setSpeedModifierWhenRepositioning(double speedModifierWhenRepositioning) {
        this.speedModifierWhenRepositioning = speedModifierWhenRepositioning;
    }

    public float getApproachDistance() {
        return approachDistance;
    }

    public void setApproachDistance(float approachDistance) {
        this.approachDistance = approachDistance;
    }

    public float getTargetInRangeRadius() {
        return targetInRangeRadius;
    }

    public void setTargetInRangeRadius(float targetInRangeRadius) {
        this.targetInRangeRadius = targetInRangeRadius;
    }
}
