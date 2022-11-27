package ml.denisd3d.mc2discord.forge.mixin;

import com.mojang.authlib.GameProfile;
import ml.denisd3d.mc2discord.core.Mc2Discord;
import ml.denisd3d.mc2discord.forge.account.LinkCommand;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.UUID;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    // Relaying others mods messages
    @Inject(method = "broadcastMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/ChatType;Ljava/util/UUID;)V", at = @At("HEAD"))
    public void broadcastMessage(Component component, ChatType p_11266_, UUID p_11267_, CallbackInfo ci) {
        if (Thread.currentThread().getStackTrace()[3].getClassName().startsWith("net.minecraft") || Thread.currentThread().getStackTrace()[3].getClassName().startsWith("ml.denisd3d.mc2discord")) {
            return;
        }
        Mc2Discord.INSTANCE.messageManager.sendInfoMessage(component.getString());
    }

    // Accounts
    @Inject(method = "canPlayerLogin(Ljava/net/SocketAddress;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/network/chat/Component;", at = @At(value = "HEAD"), cancellable = true)
    public void initializeConnectionToPlayer(SocketAddress p_206258_1_, GameProfile p_206258_2_, CallbackInfoReturnable<Component> cir) {
        if (Mc2Discord.INSTANCE != null && Mc2Discord.INSTANCE.m2dAccount != null && Mc2Discord.INSTANCE.config.features.account_linking && Mc2Discord.INSTANCE.config.account.force_link) {
            String result = Mc2Discord.INSTANCE.m2dAccount.generateCodeOrNull(p_206258_2_, p_206258_2_.getId());
            if (result != null) {
                cir.setReturnValue(LinkCommand.getCopiableTextComponent(result));
            }
        }
    }
}