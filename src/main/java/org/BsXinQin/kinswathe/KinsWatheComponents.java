package org.BsXinQin.kinswathe;

import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.BsXinQin.kinswathe.component.CustomWinnerComponent;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.BsXinQin.kinswathe.roles.kidnapper.KidnapperComponent;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class KinsWatheComponents implements EntityComponentInitializer, WorldComponentInitializer {

    public KinsWatheComponents() {}

    /// 注册事件
    public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, AbilityPlayerComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(AbilityPlayerComponent::new);
        registry.beginRegistration(PlayerEntity.class, CookComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(CookComponent::new);
        registry.beginRegistration(PlayerEntity.class, KidnapperComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(KidnapperComponent::new);

    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry worldComponentFactoryRegistry) {
        worldComponentFactoryRegistry.register(ConfigWorldComponent.KEY, ConfigWorldComponent::new);
        worldComponentFactoryRegistry.register(CustomWinnerComponent.KEY, CustomWinnerComponent::new);
    }
}