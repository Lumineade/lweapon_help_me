package net.luminyra.lweapon;

import net.fabricmc.api.ModInitializer;

import net.luminyra.lweapon.Item.ModItems;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LumisWeapons implements ModInitializer {
	public static final String MOD_ID = "lweapon";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}

	public static Identifier id(String identifier) {
		return Identifier.fromNamespaceAndPath(MOD_ID, identifier);
	}
}