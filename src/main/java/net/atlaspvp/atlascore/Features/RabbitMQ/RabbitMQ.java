package net.atlaspvp.atlascore.Features.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import net.atlaspvp.atlascore.Features.Essentials.Commands;
import net.atlaspvp.atlascore.Struct.Feature;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQ extends Feature {
    private boolean state;
    public static ConnectionFactory factory;
    public static int RabbitPort = 27003;
    public static String RabbitIP = "172.18.0.1";

    public static String serverID;



    public void onEnable(Plugin plugin) {
        //init();
        //Bukkit.getServer().getName();
        //FileConfiguration config = plugin.getConfig();
        //serverID = config.getString("serverID");
    }

    public void onDisable() {

    }


    private void init() {
        //create factory
        factory = new ConnectionFactory();
        factory.setHost(RabbitIP);
        factory.setPort(RabbitPort);
        factory.setUsername("adfeahajulort");
        factory.setPassword("oyqiwebyif");


        //start the reciever
        try {
            //Recieve.receive();
            state = true; // Set state to true after successful initialization
        } catch (Exception e) {
            state = false; // Set state to false if initialization fails
            throw new RuntimeException(e);
        }
    }


    private void registerReciever(String queueName) {

    }

    public boolean getState() {
        return state;
    }


}
