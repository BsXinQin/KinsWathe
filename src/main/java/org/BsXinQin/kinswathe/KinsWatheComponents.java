package org.BsXinQin.kinswathe;

import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.BsXinQin.kinswathe.component.CustomWinnerComponent;
import org.BsXinQin.kinswathe.component.GameSafeComponent;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.BsXinQin.kinswathe.roles.dreamer.DreamerComponent;
import org.BsXinQin.kinswathe.roles.hunter.HunterComponent;
import org.BsXinQin.kinswathe.roles.kidnapper.KidnapperComponent;
import org.BsXinQin.kinswathe.roles.physician.PhysicianComponent;
import org.BsXinQin.kinswathe.roles.robot.RobotComponent;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class KinsWatheComponents implements EntityComponentInitializer, WorldComponentInitializer {

    @Override
    public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, GameSafeComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(GameSafeComponent::new);
        registry.beginRegistration(PlayerEntity.class, AbilityPlayerComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(AbilityPlayerComponent::new);
        registry.beginRegistration(PlayerEntity.class, CookComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(CookComponent::new);
        registry.beginRegistration(PlayerEntity.class, DreamerComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(DreamerComponent::new);
        registry.beginRegistration(PlayerEntity.class, HunterComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(HunterComponent::new);
        registry.beginRegistration(PlayerEntity.class, KidnapperComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(KidnapperComponent::new);
        registry.beginRegistration(PlayerEntity.class, RobotComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(RobotComponent::new);
        registry.beginRegistration(PlayerEntity.class, PhysicianComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PhysicianComponent::new);
    }

    @Override
    public void registerWorldComponentFactories(@NotNull WorldComponentFactoryRegistry registry) {
        registry.register(ConfigWorldComponent.KEY, ConfigWorldComponent::new);
        registry.register(CustomWinnerComponent.KEY, CustomWinnerComponent::new);
    }
}