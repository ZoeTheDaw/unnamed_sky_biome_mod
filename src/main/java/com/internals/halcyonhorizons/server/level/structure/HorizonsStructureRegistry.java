package com.internals.halcyonhorizons.server.level.structure;

import com.internals.halcyonhorizons.HalcyonHorizons;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class HorizonsStructureRegistry {
    public static final DeferredRegister<StructureType<?>> DEF_REG = DeferredRegister.create(Registries.STRUCTURE_TYPE, HalcyonHorizons.MODID);
    public static final RegistryObject<StructureType<InfirmaryFluffStructure>> INFIRMARY_FLUFF = DEF_REG.register("infirmary_fluff", () -> () -> InfirmaryFluffStructure.CODEC);
}