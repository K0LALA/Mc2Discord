package ml.denisd3d.mc2discord.forge.storage;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.server.players.StoredUserList;

import javax.annotation.Nonnull;
import java.io.File;

public class DiscordIdList extends StoredUserList<GameProfile, DiscordIdEntry> {
    public DiscordIdList(File saveFile) {
        super(saveFile);
    }

    @Nonnull
    protected StoredUserEntry<GameProfile> createEntry(@Nonnull JsonObject entryData) {
        return new DiscordIdEntry(entryData);
    }

    @Override
    @Nonnull
    protected String getKeyForUser(GameProfile obj) {
        return obj.getId().toString();
    }
}
