package com.internals.halcyonhorizons.server.level.structure.piece;

import com.internals.halcyonhorizons.HalcyonHorizons;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractSkyGenerationStructurePiece extends StructurePiece {
    protected final BlockPos chunkCorner;
    protected final BlockPos cloudCenter;
    protected final int height;
    protected final int radius;
    private static boolean replaceBiomesError;

    public AbstractSkyGenerationStructurePiece(StructurePieceType pieceType, BlockPos chunkCorner, BlockPos holeCenter, int height, int radius) {
        this(pieceType, chunkCorner, holeCenter, height, radius, chunkCorner.getY() - 2, chunkCorner.getY() + 16);
    }

    public AbstractSkyGenerationStructurePiece(StructurePieceType pieceType, BlockPos chunkCorner, BlockPos holeCenter, int height, int radius, int minY, int maxY) {
        super(pieceType, 0, createBoundingBox(chunkCorner, minY, maxY));
        this.chunkCorner = chunkCorner;
        this.cloudCenter = holeCenter;
        this.height = height;
        this.radius = radius;
    }

    public AbstractSkyGenerationStructurePiece(StructurePieceType pieceType, CompoundTag tag) {
        super(pieceType, tag);
        this.chunkCorner = new BlockPos(tag.getInt("TPX"), tag.getInt("TPY"), tag.getInt("TPZ"));
        this.cloudCenter = new BlockPos(tag.getInt("HCX"), tag.getInt("HCY"), tag.getInt("HCZ"));
        this.height = tag.getInt("Height");
        this.radius = tag.getInt("Radius");
    }

    private static BoundingBox createBoundingBox(BlockPos origin, int minY, int maxY) {
        ChunkPos chunkPos = new ChunkPos(origin);
        return new BoundingBox(chunkPos.getMinBlockX(), minY, chunkPos.getMinBlockZ(), chunkPos.getMaxBlockX(), maxY, chunkPos.getMaxBlockZ());
    }

    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, CompoundTag tag) {
        tag.putInt("TPX", this.chunkCorner.getX());
        tag.putInt("TPY", this.chunkCorner.getY());
        tag.putInt("TPZ", this.chunkCorner.getZ());
        tag.putInt("HCX", this.cloudCenter.getX());
        tag.putInt("HCY", this.cloudCenter.getY());
        tag.putInt("HCZ", this.cloudCenter.getZ());
        tag.putInt("Height", this.height);
        tag.putInt("Radius", this.radius);
    }

    public void replaceBiomes(WorldGenLevel level, ResourceKey<Biome> with, int belowLevel) {
        if(replaceBiomesError){
            return;
        }
        try {
            Holder<Biome> biomeHolder = level.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(with);
            ChunkAccess chunkAccess = level.getChunk(this.chunkCorner);
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            pos.set(this.chunkCorner.getX());
            if (!biomeHolder.is(Biomes.PLAINS)) {
                while (pos.getY() > level.getMinBuildHeight()) {
                    pos.move(0, -8, 0);
                    int sectionIndex = chunkAccess.getSectionIndex(pos.getY());
                    if (sectionIndex >= 0 && sectionIndex < chunkAccess.getSections().length) {
                        LevelChunkSection section = chunkAccess.getSection(sectionIndex);
                        PalettedContainer<Holder<Biome>> container = section.getBiomes().recreate();
                        for (int biomeX = 0; biomeX < 4; ++biomeX) {
                            for (int biomeY = 0; biomeY < 4; ++biomeY) {
                                for (int biomeZ = 0; biomeZ < 4; ++biomeZ) {
                                    container.getAndSetUnchecked(biomeX, biomeY, biomeZ, biomeHolder);
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            replaceBiomesError = true;
            HalcyonHorizons.LOGGER.warn("Could not replace biomes for Halcyon Horizons. Error will show only once - likely a world-gen mod incompatibility");
        }
    }

    public void checkedSetBlock(WorldGenLevel level, BlockPos position, BlockState state) {
        if (this.getBoundingBox().isInside(position)) {
            level.setBlock(position, state, 128);
        }
    }

    public BlockState checkedGetBlock(WorldGenLevel level, BlockPos position) {
        if (this.getBoundingBox().isInside(position)) {
            return level.getBlockState(position);
        } else {
            return Blocks.VOID_AIR.defaultBlockState();
        }
    }

    public BlockState checkedGetBlockIgnoreY(WorldGenLevel level, BlockPos position) {
        if (this.getBoundingBox().isInside(position.getX(), this.getBoundingBox().minY(), position.getZ())) {
            return level.getBlockState(position);
        } else {
            return Blocks.VOID_AIR.defaultBlockState();
        }
    }

}