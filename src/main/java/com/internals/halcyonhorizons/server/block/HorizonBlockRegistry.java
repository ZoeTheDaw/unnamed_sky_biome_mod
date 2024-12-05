package com.internals.halcyonhorizons.server.block;

import com.internals.halcyonhorizons.HalcyonHorizons;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class HorizonsBlockRegistry {
    
    
    public static final DeferredRegister<Block> DEF_REG = DeferredRegister.create(ForgeRegistries.BLOCKS, HalcyonHorizons.MODID);
    public static final RegistryObject<Block> FLUFFPULP = registerBlockAndItem("fluffpulp", () -> new Block(), 0);

    private static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block) {
        return registerBlockAndItem(name, block, 0);
    }

    private static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, int itemType) {
        RegistryObject<Block> blockObj = DEF_REG.register(name, block);
        HorizonsItemRegistry.DEF_REG.register(name, getBlockSupplier(itemType, blockObj));
        return blockObj;
    }

    private static RegistryObject<Block> registerBlockAndItemEdible(String name, Supplier<Block> block, FoodProperties foodProperties) {
        RegistryObject<Block> blockObj = DEF_REG.register(name, block);
        HorizonsItemRegistry.DEF_REG.register(name, () -> new BlockItemWithSupplier(blockObj, new Item.Properties().food(foodProperties)));
        return blockObj;
    }


}
