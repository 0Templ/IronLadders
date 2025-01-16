package com.nine.ironladders.common.component;

import com.mojang.serialization.Codec;
import com.nine.ironladders.IronLadders;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class CustomComponents {

    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, IronLadders.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> MORPH_TYPE =
            COMPONENTS.register("morph_type", () -> DataComponentType.<String>builder().persistent
                    (Codec.STRING.orElse(" "))
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8).cacheEncoding().build());

}
