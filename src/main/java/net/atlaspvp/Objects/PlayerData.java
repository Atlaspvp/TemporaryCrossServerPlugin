package net.atlaspvp.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.UUID;

@ToString
@Getter
@Setter
public class PlayerData implements Serializable {
    private final PlayerData playerData;
    private final UUID playerId;
    private final String playerName;
    private final GameMode gamemode; // TODO convert into VALUE
    private final int totalExperience;
    private final int level;
    private final float exp;
    private final byte[][] inventory;
    private final byte[][] enderchest;
    private final double maxHealth;
    private final double health;
    private final boolean isHealthScaled;
    private final double healthScale;
    private final int foodLevel;
    private final float saturation;
    private final float exhaustion;
    private final int maxAir;
    private final int remainingAir;
    private final int fireTicks;
    private final int maxNoDamageTicks;
    private final int noDamageTicks;
    private final float fallDistance;
    private final Vector velocity; // TODO convert into REGEX string
    private final int heldItemSlot;

    public PlayerData(Player player) {
        this.playerData = this;
        this.inventory = serializeItems(player.getInventory().getContents());
        this.playerId = player.getUniqueId();
        this.playerName = player.getName();
        this.gamemode = player.getGameMode();
        this.totalExperience = player.getTotalExperience();
        this.level = player.getLevel();
        this.exp = player.getExp();
        this.enderchest = serializeItems(player.getEnderChest().getContents());
        this.maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        this.health = player.getHealth();
        this.isHealthScaled = player.isHealthScaled();
        this.healthScale = player.getHealthScale();
        this.foodLevel = player.getFoodLevel();
        this.saturation = player.getSaturation();
        this.exhaustion = player.getExhaustion();
        this.maxAir = player.getMaximumAir();
        this.remainingAir = player.getRemainingAir();
        this.fireTicks = player.getFireTicks();
        this.maxNoDamageTicks = player.getMaximumNoDamageTicks();
        this.noDamageTicks = player.getNoDamageTicks();
        this.velocity = player.getVelocity();
        this.fallDistance = player.getFallDistance();
        this.heldItemSlot = player.getInventory().getHeldItemSlot();
    }

    public ItemStack[] getInventoryContents() {
        return deserializeItems(inventory);
    }

    public ItemStack[] getEnderchestContents() {
        return deserializeItems(enderchest);
    }

    public byte[] toByteArray() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new BukkitObjectOutputStream(bos)) {
            out.writeObject(this);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private static byte[][] serializeItems(ItemStack[] items) {
        byte[][] itemByteArray = new byte[items.length][];
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            itemByteArray[i] = item != null ? item.serializeAsBytes() : null;
        }
        return itemByteArray;
    }

    private static ItemStack[] deserializeItems(byte[][] items) {
        ItemStack[] itemsArray = new ItemStack[items.length];
        for (int i = 0; i < items.length; i++) {
            byte[] itemBytes = items[i];
            itemsArray[i] = itemBytes != null ? ItemStack.deserializeBytes(itemBytes) : null;
        }
        return itemsArray;
    }

    public static void applyData(Player player, PlayerData data) { // unfinished
        player.getInventory().setContents(data.getInventoryContents());
        player.setGameMode(data.getGamemode());
    }

    public static PlayerData fromByteArray(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new BukkitObjectInputStream(bis)) {
            return (PlayerData) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
