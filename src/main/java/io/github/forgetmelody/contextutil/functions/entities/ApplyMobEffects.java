package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.entities.EntityProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public record ApplyMobEffects(
        HolderSet<MobEffect> mobEffects,
        NumberProvider duration,
        NumberProvider amplifier,
        EntityProvider source
) implements EntityFunction {
    public static final MapCodec<ApplyMobEffects> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.MOB_EFFECT).fieldOf("mob_effects").forGetter(ApplyMobEffects::mobEffects),
            NumberProviders.CODEC.fieldOf("duration").forGetter(ApplyMobEffects::duration),
            NumberProviders.CODEC.fieldOf("amplifier").forGetter(ApplyMobEffects::amplifier),
            EntityProvider.DIRECT_CODEC.optionalFieldOf("source", EntityProvider.empty()).forGetter(ApplyMobEffects::source)
    ).apply(instance, ApplyMobEffects::new));

    @Override
    public void apply(Entity entity, LootContext context) {
        if (entity instanceof LivingEntity livingEntity) {
            int duration = Math.max(0, this.duration.getInt(context) * 20);
            int amplifier = Math.max(0, this.amplifier.getInt(context));
            Entity source = this.source.get(context);
            for (Holder<MobEffect> mobEffect : this.mobEffects) {
                livingEntity.addEffect(
                        new MobEffectInstance(
                                mobEffect,
                                duration,
                                amplifier
                        ),
                        source
                );
            }
        }
    }

    @Override
    public MapCodec<? extends EntityFunction> codec() {
        return MAP_CODEC;
    }
}
