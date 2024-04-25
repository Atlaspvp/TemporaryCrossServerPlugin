package net.atlaspvp.crossservertest;

import net.atlaspvp.RemoteMethods.PrimaryMethods;
import net.atlaspvp.RemoteMethods.Secondary.SecondaryImp;
import net.atlaspvp.RemoteMethods.SecondaryMethods;
import net.atlaspvp.crossservertest.Listeners.Chat;
import net.atlaspvp.crossservertest.Listeners.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public final class CrossServerTest extends JavaPlugin {
    public static String ip = "172.18.0.1";
    private static PrimaryMethods primary;

    @Override
    public void onEnable() {
        getCommand("test").setExecutor(new TestCommand());
        getServer().getPluginManager().registerEvents(new Chat(), this);
        Configuration.Initialize(this);
        Primary();
        Secondary();
    }

    @Override
    public void onDisable() {
        try {
            primary.closeSecondary(Configuration.getPort());
            System.out.println("Send Closing Request");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Primary() {
        int port = 27007;

        try {
            System.out.println("Starting try");
            Registry registry = LocateRegistry.getRegistry(ip, port);
            System.out.println("register gotten");
            primary = (PrimaryMethods) registry.lookup("Methods");

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void Secondary() {
        int port = Configuration.getPort();

        try{
            SecondaryImp server = new SecondaryImp();
            SecondaryMethods remoteMethods = (SecondaryMethods) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("SecondaryMethods", remoteMethods);

            primary.connectSecondary(Configuration.getServerName(), Configuration.getPort());

            System.out.println("Server ready");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static PrimaryMethods getPrimary() {
        return primary;
    }


}
