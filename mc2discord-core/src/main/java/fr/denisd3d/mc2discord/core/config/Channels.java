package fr.denisd3d.mc2discord.core.config;

import com.electronwill.nightconfig.core.conversion.Conversion;
import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.PreserveNotNull;
import discord4j.common.util.Snowflake;
import fr.denisd3d.mc2discord.core.M2DUtils;
import fr.denisd3d.mc2discord.core.config.converters.SnowflakeConverter;
import ml.denisd3d.config4j.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Channels {
    @Path("Channels")
    @PreserveNotNull
    public List<Channel> channels = new ArrayList<>();

    public static class Channel {
        @Path("id")
        @Comment("config.channels.id.comment")
        @Conversion(SnowflakeConverter.class)
        @PreserveNotNull
        public Snowflake channel_id = M2DUtils.NIL_SNOWFLAKE;

        @Path("subscriptions")
        @Comment("config.channels.subscriptions.comment")
        @PreserveNotNull
        public List<String> subscriptions = new ArrayList<>();

        @Path("mode")
        @Comment("config.channels.mode.comment")
        @PreserveNotNull
        public SendMode mode = SendMode.WEBHOOK;

        public Channel(String... subscriptions) {
            this.subscriptions.addAll(Arrays.asList(subscriptions));
        }

        @SuppressWarnings("unused")
        public Channel() { // Allow NightConfig to create new instances
        }

        public enum SendMode {
            WEBHOOK, PLAIN_TEXT, EMBED
        }
    }

}