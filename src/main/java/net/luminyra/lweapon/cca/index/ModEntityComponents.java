package net.luminyra.lweapon.cca.index;

import net.luminyra.lweapon.LumisWeapons;
import net.luminyra.lweapon.cca.components.HeavyLightComponent;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class ModEntityComponents implements EntityComponentInitializer {
    public static final ComponentKey<HeavyLightComponent> HEAVY_LIGHT_COMPONENT =
            ComponentRegistry.getOrCreate(LumisWeapons.id("heavy_light_component"), HeavyLightComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.beginRegistration(Player.class, HEAVY_LIGHT_COMPONENT).end(HeavyLightComponent::new);
    }
}
