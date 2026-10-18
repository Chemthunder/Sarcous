package org.chemthunder.sarcous.core.cca.chunk;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.chunk.Chunk;
import org.chemthunder.sarcous.core.Sarcous;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

/**
 * @author Chemthunder
 */
public class MinedChunkComponent implements AutoSyncedComponent {
    public static final ComponentKey<MinedChunkComponent> KEY = ComponentRegistry.getOrCreate(
            Sarcous.id("mined_chunk"),
            MinedChunkComponent.class
    );
    private final Chunk chunk;

    private static final int RESOURCE_COUNT_FALLBACK = 3;

    private int resourceCount = 3; // replace with random integer

    public MinedChunkComponent(Chunk chunk) {
        this.chunk = chunk;
    }

    public void sync() {
        KEY.sync(chunk);
    }

    public void readData(ReadView readView) {
        resourceCount = readView.getInt("ResourceCount", RESOURCE_COUNT_FALLBACK);
    }

    public void writeData(WriteView writeView) {
        writeView.putInt("ResourceCount", resourceCount);
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
        sync();
    }
}
