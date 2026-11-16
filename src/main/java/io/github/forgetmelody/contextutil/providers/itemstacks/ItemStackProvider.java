package io.github.forgetmelody.contextutil.providers.itemstacks;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Function;

public interface ItemStackProvider extends LootContextUser {
    Codec<ItemStackProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<ItemStackProvider> dispatched = BuiltInRegistries.ITEM_STACK_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(ItemStackProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, ItemStackProvider::wrap);
    });
    Codec<ItemStackProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    private static Either<Constant, ItemStackProvider> wrap(ItemStackProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    static ItemStackProvider constant(ItemStackTemplate item) {
        return new Constant(item);
    }

    static ItemStackProvider constant(Item item) {
        return new Constant(new ItemStackTemplate(item));
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends ItemStackProvider>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("constant"), Constant.MAP_CODEC);
    }

    ItemStack get(LootContext context);

    MapCodec<? extends ItemStackProvider> codec();

}
