package org.BsXinQin.kinswathe.mixin.roles.licensed_villain;

import com.llamalad7.mixinextras.sugar.Local;
import dev.doctor4t.wathe.cca.GameRoundEndComponent;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.game.gamemode.MurderGameMode;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.component.CustomWinnerComponent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MurderGameMode.class)
public class LicensedVillainKeepGameMixin {

    @Inject(method = "tickServerGameLoop", at = @At(value = "FIELD", target = "Ldev/doctor4t/wathe/game/GameFunctions$WinStatus;NONE:Ldev/doctor4t/wathe/game/GameFunctions$WinStatus;", ordinal = 3, opcode = Opcodes.GETSTATIC), cancellable = true)
    private void LicensedvillainKeepGame(ServerWorld world, GameWorldComponent gameWorld, CallbackInfo ci, @Local(name = "winStatus") GameFunctions.WinStatus winStatus) {
        List<ServerPlayerEntity> players = world.getPlayers();
        List<ServerPlayerEntity> alivePlayers = players.stream().filter(GameFunctions::isPlayerAliveAndSurvival).toList();
        boolean licensedvillainAlive = false;
        for (ServerPlayerEntity player : alivePlayers) {
            if (gameWorld.isRole(player, KinsWathe.LICENSED_VILLAIN)) {
                licensedvillainAlive = true;
                break;
            }
        }
        if (alivePlayers.size() == 1 && licensedvillainAlive) {
            CustomWinnerComponent customWinner = CustomWinnerComponent.KEY.get(world);
            customWinner.setWinningTextId("licensed_villain");
            if (!alivePlayers.isEmpty()) {
                customWinner.setWinners(alivePlayers);
            }
            customWinner.setColor(KinsWathe.LICENSED_VILLAIN.color());
            customWinner.sync();
            GameRoundEndComponent gameRoundEnd = GameRoundEndComponent.KEY.get(world);
            gameRoundEnd.setRoundEndData(players, GameFunctions.WinStatus.KILLERS);
            GameFunctions.stopGame(world);
        }
        if (licensedvillainAlive && (winStatus == GameFunctions.WinStatus.KILLERS || winStatus == GameFunctions.WinStatus.PASSENGERS)) {
            ci.cancel();
        }
    }
}