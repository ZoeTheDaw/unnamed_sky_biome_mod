package com.internals.halcyonhorizons;

import com.internals.halcyonhorizons.server.block.HorizonsBlockRegistry;
import com.internals.halcyonhorizons.server.item.HorizonsItemRegistry;
import com.internals.halcyonhorizons.server.level.biome.HorizonsBiomeRegistry;
import com.internals.halcyonhorizons.server.level.structure.HorizonsStructureRegistry;
import com.internals.halcyonhorizons.server.level.structure.piece.HorizonsStructurePieceRegistry;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(HalcyonHorizons.MODID)
public class HalcyonHorizons {
    public static final String MODID = "halcyonhorizons";
    private static final ResourceLocation PACKET_NETWORK_NAME = new ResourceLocation("halcyonhorizons:main_channel");
    public static final Logger LOGGER = LogUtils.getLogger();

    public HalcyonHorizons() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        HorizonsBlockRegistry.DEF_REG.register(modEventBus);
        HorizonsItemRegistry.DEF_REG.register(modEventBus);
        HorizonsStructureRegistry.DEF_REG.register(modEventBus);
        HorizonsStructurePieceRegistry.DEF_REG.register(modEventBus);
        HorizonsBiomeRegistry.init();
    }



}