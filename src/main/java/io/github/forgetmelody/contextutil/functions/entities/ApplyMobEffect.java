package io.github.forgetmelody.contextutil.functions.entities;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.forgetmelody.contextutil.providers.entities.ContextEntityProvider;
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

public record ApplyMobEffect(
        HolderSet<MobEffect> mobEffects,
        NumberProvider duration,
        NumberProvider amplifier,
        ContextEntityProvider source
) implements ContextEntityFunction {
    public static final MapCodec<ApplyMobEffect> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.MOB_EFFECT).fieldOf("mob_effects").forGetter(ApplyMobEffect::mobEffects),
            NumberProviders.CODEC.fieldOf("duration").forGetter(ApplyMobEffect::duration),
            NumberProviders.CODEC.fieldOf("amplifier").forGetter(ApplyMobEffect::amplifier),
            ContextEntityProvider.DIRECT_CODEC.optionalFieldOf("source", ContextEntityProvider.empty()).forGetter(ApplyMobEffect::source)
    ).apply(instance, ApplyMobEffect::new));

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
    public MapCodec<? extends ContextEntityFunction> codec() {
        return MAP_CODEC;
    }
}
