package com.internals.halcyonhorizons.server.level.structure.piece;

import com.internals.halcyonhorizons.HalcyonHorizons;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class HorizonsStructurePieceRegistry {
    public static final DeferredRegister<StructurePieceType> DEF_REG = DeferredRegister.create(Registries.STRUCTURE_PIECE, HalcyonHorizons.MODID);

    public static final RegistryObject<StructurePieceType> INFIRMARY_FLUFF = DEF_REG.register("infirmary_fluff", () -> InfirmaryFluffPiece::new);
}