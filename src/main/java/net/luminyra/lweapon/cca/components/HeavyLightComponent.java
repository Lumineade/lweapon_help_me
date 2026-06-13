package net.luminyra.lweapon.cca.components;

import net.luminyra.lweapon.LumisWeapons;
import net.luminyra.lweapon.cca.index.ModEntityComponents;
import net.luminyra.lweapon.cca.index.PlayerComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HeavyLightComponent extends PlayerComponent implements ServerTickingComponent {
    //Dying Light Passive start

    public int light;   //light meter
    public int initlwaitticks; //ticks until it should start passively draining light
    public int lwaitticks;
    public int lwaitage = 1;
    public int ldrainage;
    public int warnTicks;

    public float playerAttackStrengthScale;

    //CHANGE THESE LUMI
    public final int LIGHT_ADDITION = 5;
    public final int INITIAL_WAIT_TICKS = 20;
    public final int TICKS_UNTIL_DRAIN = 20;
    public final int LIGHT_THRESHOLD = 150;
    public final int TICKS_UNTIL_EXPLOSION = 30;

    //Dying Light Passive end

    public HeavyLightComponent(Player owner) {
        super(owner);
    }

    @Override
    public ComponentKey<? extends PlayerComponent> getComponent() {
        return ModEntityComponents.HEAVY_LIGHT_COMPONENT;
    }

    @Override
    public void readData(ValueInput valueInput) {
        light = valueInput.getIntOr("light", 0);
    }

    @Override
    public void writeData(ValueOutput valueOutput) {
        valueOutput.putInt("light", light);
    }

    @Override
    public void serverTick() {
        //Dying Light Passive start

        if (owner.getAttackStrengthScale(0f) > playerAttackStrengthScale) playerAttackStrengthScale = owner.getAttackStrengthScale(0f);
        owner.getAttribute(Attributes.ATTACK_SPEED).addOrUpdateTransientModifier(new AttributeModifier(LumisWeapons.id("light"), 1 * ((double) light / 100), AttributeModifier.Operation.ADD_VALUE));
        if (initlwaitticks > 0) {
            lwaitage++;
            initlwaitticks--;
            if (initlwaitticks == 0) {
                ldrainage = 0;
            }
        } else {
            ldrainage++;
            if (light > 0 && ldrainage % lwaitage == 0) {
                light--;
                if (lwaitage > 1) lwaitage--;
            }
        }
        if (light > LIGHT_THRESHOLD) {
            warnTicks++;
            if (warnTicks > TICKS_UNTIL_EXPLOSION) {
                lightExplosion();
                reset();
            }
        } else {
            warnTicks = 0;
        }

        //Dying Light Passive end

        this.sync();
        owner.sendOverlayMessage(Component.literal(light + "/100").withStyle(light > 120 ? ChatFormatting.RED : light > 90 ? ChatFormatting.YELLOW : ChatFormatting.GREEN));
    }

    private void lightExplosion() {
        //Todo: make an actual explosion
        owner.level().explode(owner, owner.getX(), owner.getY(), owner.getZ(),50, false, Level.ExplosionInteraction.MOB);
    }

    private void reset() {
        light = 0;
        initlwaitticks = 0;
        lwaitage = 1;
        lwaitticks = 0;
        ldrainage = 0;
        warnTicks = 0;
    }

    public void addLight(int amount) {
        initlwaitticks = TICKS_UNTIL_DRAIN;
        lwaitticks = INITIAL_WAIT_TICKS;
        lwaitage = 0;
        ldrainage = 0;
        light += (int) (amount * playerAttackStrengthScale);
        playerAttackStrengthScale = 0;
        this.sync();
    }
}
