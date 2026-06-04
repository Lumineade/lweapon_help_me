package net.luminyra.lweapon.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.luminyra.lweapon.Item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.Arrays;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.NIPZIUM_INGOT, ModelTemplates.FLAT_ITEM);

        createSimpleGuiVarying(itemModelGenerators, ModItems.HEAVY_LIGHT);

        //itemModelGenerators.declareCustomModelItem(ModItems.HEAVY_LIGHT);

        //bro im actuall y gonig to kill a small child im goin im to im actually going to do it this time
    }

    public static void createSimpleGuiVarying(ItemModelGenerators generator, Item item) {
        Identifier id = BuiltInRegistries.ITEM.getKey(item);

        generator.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
        generator.itemModelOutput.accept(item,
                ItemModelUtils.select(
                        new DisplayContext(),
                        ItemModelUtils.plainModel(id.withPath(st -> "item/" + st + "_in_hand")),
                        ItemModelUtils.when(
                                Arrays.asList(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED),
                                ItemModelUtils.plainModel(id.withPath(st -> "item/" + st))
                        )
                )
        );
    }
}
