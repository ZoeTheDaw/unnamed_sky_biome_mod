package com.internals.halcyonhorizons.server.level.structure;


import com.internals.halcyonhorizons.server.level.biome.HorizonsBiomeRegistry;
import com.internals.halcyonhorizons.server.level.structure.piece.InfirmaryFluffPiece;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class InfirmaryFluffStructure extends AbstractSkyGenerationStructure {
    private static final int FLUFF_WIDTH_RADIUS = 100;
    private static final int FLUFF_HEIGHT_RADIUS = 30;

    public static final int FLUFF_Y_CENTER = 201;

    public static final Codec<InfirmaryFluffStructure> CODEC = simpleCodec(InfirmaryFluffStructure::new);

    public InfirmaryFluffStructure(StructureSettings settings) {
        super(settings, HorizonsBiomeRegistry.AVIAN_INFIRMARY);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext p_226571_) {
        return Optional.empty();
    }

    @Override
    protected StructurePiece createPiece(BlockPos offset, BlockPos center, int heightBlocks, int widthBlocks, RandomState randomState) {
        return new InfirmaryFluffPiece(offset, center, heightBlocks, widthBlocks);
    }

    @Override
    public int getGenerateYHeight(WorldgenRandom random, int x, int y) {
        return FLUFF_Y_CENTER;
    }

    @Override
    public int getWidthRadius(WorldgenRandom random) {
        return FLUFF_WIDTH_RADIUS;
    }

    @Override
    public int getHeightRadius(WorldgenRandom random, int seaLevel) {
        return FLUFF_HEIGHT_RADIUS;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return HorizonsStructureRegistry.INFIRMARY_FLUFF.get();
    }
}

