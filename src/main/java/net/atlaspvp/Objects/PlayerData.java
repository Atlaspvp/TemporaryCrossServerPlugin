package net.atlaspvp.Objects;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;

public class PlayerData implements Serializable {
    private String name;
    private String uuid;
    private byte[] inventory;
    private PlayerData playerData;

    public PlayerData() {this.playerData = this;}

    public PlayerData(String name, String uuid, ItemStack[] inventory) {
        this.name = name;
        this.uuid = uuid;
        this.inventory = serialiseInventory(inventory);
        this.playerData = this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ItemStack[] getInventory() {
        return deserialiseInventory(inventory);
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = serialiseInventory(inventory);
    }

    public byte[] Serialize() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(playerData);
            out.close();

            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public static PlayerData deserializeTest(byte[] byteArray) {
        PlayerData test = new PlayerData();

        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            test = (PlayerData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return test;
    }

    public static byte[] serialiseInventory(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            System.out.println("Serializing " + items.length + " items.");
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            // Serialize that array
            dataOutput.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }



    public static ItemStack[] deserialiseInventory(byte[] data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
