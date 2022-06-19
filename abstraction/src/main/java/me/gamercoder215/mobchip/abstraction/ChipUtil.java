package me.gamercoder215.mobchip.abstraction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.level.Level;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Represents an Abstract Wrapper
 */
public interface ChipUtil {

    // Conversions

    ItemStack convert(net.minecraft.world.item.ItemStack item);

    net.minecraft.world.item.ItemStack convert(ItemStack item);

    SoundEvent convert(Sound s);

    Sound convert(SoundEvent s);

    ServerPlayer convert(Player p);

    net.minecraft.world.entity.monster.hoglin.Hoglin convert(Hoglin p);

    net.minecraft.world.entity.monster.piglin.Piglin convert(Piglin p);

    AgeableMob convert(Ageable a);

    ServerLevel convert(World w);

    List<net.minecraft.world.entity.LivingEntity> convert(List<LivingEntity> list);

    net.minecraft.world.entity.Entity convert(Entity en);

    net.minecraft.world.entity.Mob convert(Mob m);

    net.minecraft.world.entity.LivingEntity convert(LivingEntity en);

    PathfinderMob convert(Creature c);

    ResourceLocation convert(NamespacedKey key);

    RangedAttribute convert(Attribute a);

    // Defaults (Requires no CraftBukkit)

    default DamageSource convert(EntityDamageEvent.DamageCause c) {
        return switch (c) {
            case FIRE -> DamageSource.IN_FIRE;
            case LIGHTNING -> DamageSource.LIGHTNING_BOLT;
            case FIRE_TICK -> DamageSource.ON_FIRE;
            case SUFFOCATION -> DamageSource.IN_WALL;
            case LAVA -> DamageSource.LAVA;
            case HOT_FLOOR -> DamageSource.HOT_FLOOR;
            case CRAMMING -> DamageSource.CRAMMING;
            case DROWNING -> DamageSource.DROWN;
            case STARVATION -> DamageSource.STARVE;
            case CONTACT -> DamageSource.CACTUS;
            case MAGIC -> DamageSource.MAGIC;
            case FALL -> DamageSource.FALL;
            case FLY_INTO_WALL -> DamageSource.FLY_INTO_WALL;
            case VOID -> DamageSource.OUT_OF_WORLD;
            case WITHER -> DamageSource.WITHER;
            case FALLING_BLOCK -> DamageSource.FALLING_BLOCK;
            case DRAGON_BREATH -> DamageSource.DRAGON_BREATH;
            case FREEZE -> DamageSource.FREEZE;
            case DRYOUT -> DamageSource.DRY_OUT;
            default -> DamageSource.GENERIC;
        };
    }

    default LivingEntity convert(net.minecraft.world.entity.LivingEntity en) {
        return (LivingEntity) en.getBukkitEntity();
    }

    default Mob convert(net.minecraft.world.entity.Mob mob) {
        return (Mob) mob.getBukkitEntity();
    }

    default Creature convert(PathfinderMob mob) {
        return (Creature) mob.getBukkitEntity();
    }

    default net.minecraft.world.entity.EntityType<?> convert(org.bukkit.entity.EntityType type) {
        return Registry.ENTITY_TYPE.get(convert(type.getKey()));
    }

    default BlockPos convert(Location loc) {
        return new BlockPos(loc.getX(), loc.getY(), loc.getZ());
    }

    default net.minecraft.world.Difficulty convert(Difficulty d) {
        return net.minecraft.world.Difficulty.byName(d.name().toLowerCase());
    }

    default Difficulty convert(net.minecraft.world.Difficulty d) {
        try {
            return Difficulty.valueOf(d.getKey().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Difficulty.NORMAL;
        }
    }

    default World convert(Level l) {
        return l.getWorld();
    }

    default Location convert(World w, BlockPos pos) {
        return new Location(w, pos.getX(), pos.getY(), pos.getZ());
    }

    default <T extends org.bukkit.entity.Entity> Class<? extends T> toBukkitClass(Class<T> bukkit, Class<? extends net.minecraft.world.entity.Entity> clazz) {
        try {
            Method m = clazz.getDeclaredMethod("getBukkitEntity");
            m.setAccessible(true);

            return m.getReturnType().asSubclass(bukkit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    default Class<? extends net.minecraft.world.entity.Entity> toNMSClass(Class<? extends Entity> clazz) {
        try {
            return clazz.getDeclaredField("entity").getType().asSubclass(net.minecraft.world.entity.Entity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    default Class<? extends net.minecraft.world.entity.LivingEntity> toLivingNMSClass(Class<? extends LivingEntity> clazz) {
        try {
            return clazz.getDeclaredField("entity").getType().asSubclass(net.minecraft.world.entity.LivingEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}