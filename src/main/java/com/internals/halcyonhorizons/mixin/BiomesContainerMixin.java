package com.internals.halcyonhorizons.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelChunkSection.class)

public class BiomesContainerMixin {
    /// Trying to make the container public class access
    @Shadow
    public PalettedContainerRO<Holder<Biome>> biomes;
}
