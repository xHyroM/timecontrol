package net.fabricmc.example.mixin;

import net.fabricmc.example.accessor.MinecraftServerAccessor;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerManager.class})
public abstract class PlayerManagerMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method={"onPlayerConnect"}, at={@At(value="TAIL")})
    private void onGameJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        ((MinecraftServerAccessor)this.server).sendTimeStatus(player);
    }

    @Inject(method={"respawnPlayer"}, at={@At(value="TAIL")})
    private void onGameJoin(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        ((MinecraftServerAccessor)this.server).sendTimeStatus(player);
    }
}
