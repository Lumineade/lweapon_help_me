package net.luminyra.lweapon.cca.index;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;


public abstract class PlayerComponent implements AutoSyncedComponent {
    protected final Player owner;

    public PlayerComponent(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public abstract ComponentKey<? extends PlayerComponent> getComponent();

    public void sync() {
        this.sync(false);
    }

    public void sync(boolean verbose) {
        this.getComponent().sync(this.owner);

        if (verbose) {
            owner.sendSystemMessage(Component.literal("Syncing...").withStyle(ChatFormatting.GRAY));
        }
    }

    public Level getWorld() {
        return this.owner.level();
    }
}