package com.internals.halcyonhorizons.server.block;

import com.github.alexthe666.citadel.item.BlockItemWithSupplier;
import com.internals.halcyonhorizons.HalcyonHorizons;
import com.internals.halcyonhorizons.server.item.HorizonsItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class HorizonsBlockRegistry {

    public static final BlockBehaviour.Properties FLUFFPULP_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(0.5F, .6F).sound(SoundType.WOOL);
    
    
    public static final DeferredRegister<Block> DEF_REG = DeferredRegister.create(ForgeRegistries.BLOCKS, HalcyonHorizons.MODID);
    public static final RegistryObject<Block> FLUFFPULP = registerBlockAndItem("fluffpulp", () -> new Block(FLUFFPULP_PROPERTIES).defaultBlockState().getBlock());

    private static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, int itemType) {
        RegistryObject<Block> blockObj = DEF_REG.register(name, block);
        HorizonsItemRegistry.DEF_REG.register(name, getBlockSupplier(itemType, blockObj));
        return blockObj;
    }

    private static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block) {
        return registerBlockAndItem(name, block, 0);
    }

    private static Supplier<? extends BlockItemWithSupplier> getBlockSupplier(int itemType, RegistryObject<Block> blockObj) {
        switch (itemType) {
            default:
                return () -> new BlockItemWithSupplier(blockObj, new Item.Properties());

        }
    }


}
