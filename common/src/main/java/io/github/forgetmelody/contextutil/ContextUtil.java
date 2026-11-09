package io.github.forgetmelody.contextutil;

import net.minecraft.resources.Identifier;

public final class ContextUtil {
    public static final String MOD_ID = "context_util";

    private ContextUtil() {
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
