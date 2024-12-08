package com.internals.halcyonhorizons.server.level.structure.piece;

import com.internals.halcyonhorizons.server.block.HorizonsBlockRegistry;
import com.internals.halcyonhorizons.server.level.biome.HorizonsBiomeRegistry;
import com.internals.halcyonhorizons.server.misc.HorizonsMath;
import com.internals.halcyonhorizons.server.misc.VoronoiGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.NotNull;

public class InfirmaryFluffPiece extends AbstractSkyGenerationStructurePiece {

    public InfirmaryFluffPiece(BlockPos chunkCorner, BlockPos holeCenter, int bowlHeight, int bowlRadius) {
        super(HorizonsStructurePieceRegistry.INFIRMARY_FLUFF.get(), chunkCorner, holeCenter, bowlHeight, bowlRadius);
    }

    public InfirmaryFluffPiece(CompoundTag tag) {
        super(HorizonsStructurePieceRegistry.INFIRMARY_FLUFF.get(), tag);
    }

    public InfirmaryFluffPiece(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag tag){
        this(tag);
    }

    public void postProcess(@NotNull WorldGenLevel level, @NotNull StructureManager featureManager, @NotNull ChunkGenerator chunkGen, @NotNull RandomSource random, @NotNull BoundingBox boundingBox, @NotNull ChunkPos chunkPos, @NotNull BlockPos blockPos) {
        int cornerX = this.chunkCorner.getX();
        int cornerY = this.chunkCorner.getY();
        int cornerZ = this.chunkCorner.getZ();
        boolean flag = false;
        BlockPos.MutableBlockPos carve = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos carveAbove = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos carveBelow = new BlockPos.MutableBlockPos();
        carve.set(cornerX, cornerY, cornerZ);
        carveAbove.set(cornerX, cornerY, cornerZ);
        carveBelow.set(cornerX, cornerY, cornerZ);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                MutableBoolean doFloor = new MutableBoolean(false);
                for (int y = 15; y >= 0; y--) {
                    carve.set(cornerX + x, Mth.clamp(cornerY + y, level.getMinBuildHeight(), level.getMaxBuildHeight()), cornerZ + z);
                    if (inCircle(carve) && !checkedGetBlock(level, carve).is(Blocks.BEDROCK)) {
                        flag = true;
                        checkedSetBlock(level, carve, HorizonsBlockRegistry.FLUFFPULP.get().defaultBlockState());
                        surroundCornerOfLiquid(level, carve);
                        carveBelow.set(carve.getX(), carve.getY() - 1, carve.getZ());
                        doFloor.setTrue();
                    }
                }
            }
        }
        if (flag) {
            replaceBiomes(level, HorizonsBiomeRegistry.AVIAN_INFIRMARY, 32);
        }
    }

    private void surroundCornerOfLiquid(WorldGenLevel level, Vec3i center) {
        BlockPos.MutableBlockPos offset = new BlockPos.MutableBlockPos();
        for (Direction dir : Direction.values()) {
            offset.set(center);
            offset.move(dir);
            BlockState state = checkedGetBlock(level, offset);
            if (!state.getFluidState().isEmpty()) {
                checkedSetBlock(level, offset, Blocks.AIR.defaultBlockState());
            }
        }
    }

    private boolean inCircle(BlockPos carve) {
        float wallNoise = (HorizonsMath.sampleNoise3D(carve.getX(), (int) (carve.getY() * 0.1F), carve.getZ(), 40) + 1.0F) * 0.5F;
        double yDist = HorizonsMath.smin(1F - Math.abs(this.cloudCenter.getY() - carve.getY()) / (height * 0.5F), 1.0F, 0.3F);
        double distToCenter = carve.distToLowCornerSqr(this.cloudCenter.getX(), carve.getY(), this.cloudCenter.getZ());
        double targetRadius = yDist * (radius * wallNoise) * radius;
        return distToCenter < targetRadius;
    }
}