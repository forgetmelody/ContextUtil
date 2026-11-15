package io.github.forgetmelody.contextutil;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(ContextUtil.MOD_ID)
public class ContextUtil {
    public static final String MOD_ID = "contextutil";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ContextUtil(IEventBus modEventBus, ModContainer modContainer) {

    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(name, MOD_ID);
    }
}
