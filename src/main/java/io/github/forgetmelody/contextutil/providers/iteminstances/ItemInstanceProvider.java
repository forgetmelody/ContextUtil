package io.github.forgetmelody.contextutil.providers.iteminstances;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.forgetmelody.contextutil.ContextUtil;
import io.github.forgetmelody.contextutil.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

public interface ItemInstanceProvider extends LootContextUser {
    Codec<ItemInstanceProvider> DIRECT_CODEC = Codec.lazyInitialized(() -> {
        Codec<ItemInstanceProvider> dispatched = BuiltInRegistries.ITEM_STACK_PROVIDER_TYPE
                .byNameCodec()
                .dispatch(ItemInstanceProvider::codec, Function.identity());
        return Codec.either(Constant.INLINE_CODEC, dispatched).xmap(Either::unwrap, ItemInstanceProvider::wrap);
    });
    Codec<ItemInstanceProvider> NETWORK_CODEC = MapCodec.unitCodec(Empty::new);

    private static Either<Constant, ItemInstanceProvider> wrap(ItemInstanceProvider provider) {
        return provider instanceof Constant constant ? Either.left(constant) : Either.right(provider);
    }

    static ItemInstanceProvider constant(ItemStackTemplate item) {
        return new Constant(item);
    }

    static ItemInstanceProvider constant(Item item) {
        return new Constant(new ItemStackTemplate(item));
    }

    static ItemInstanceProvider tool() {
        return Tool.INSTANCE;
    }

    static void bootstrap(RegisterEvent.RegisterHelper<MapCodec<? extends ItemInstanceProvider>> context) {
        context.register(ContextUtil.id("empty"), Empty.MAP_CODEC);
        context.register(ContextUtil.id("constant"), Constant.MAP_CODEC);
    }

    @Nullable ItemInstance get(LootContext context);

    default @Nullable ItemStack getSafeItemStack(LootContext context) {
        ItemInstance instance = this.get(context);
        if (instance instanceof ItemStack itemStack) {
            return itemStack.copy();
        }

        if (instance instanceof ItemStackTemplate template) {
            return template.create();
        }

        return null;
    }

    MapCodec<? extends ItemInstanceProvider> codec();

}
