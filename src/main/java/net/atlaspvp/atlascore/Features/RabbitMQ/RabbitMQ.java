package net.atlaspvp.atlascore.Features.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import net.atlaspvp.atlascore.Features.Essentials.Commands;
import net.atlaspvp.atlascore.Struct.Feature;
import org.bukkit.plugin.Plugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQ extends Feature {
    private boolean state;
    public static ConnectionFactory factory;
    public static Connection connection;
    public static Channel channel;

    public static int RabbitPort = 27003;
    public static String RabbitIP = "172.18.0.1";

    public static String QUEUE_NAME = "atlascore";
    public static String EXCHANGE = "exchange";

    public void onEnable(Plugin plugin) {
        //create factory
        factory = new ConnectionFactory();
        factory.setHost(RabbitIP);
        factory.setPort(RabbitPort);
        factory.setUsername("adfeahajulort");
        factory.setPassword("oyqiwebyif");


        //start the reciever
        try {
            Recieve.receive();
            Send.send();
            state = true; // Set state to true after successful initialization
        } catch (Exception e) {
            state = false; // Set state to false if initialization fails
            throw new RuntimeException(e);
        }


    }

    public void onDisable() {

    }

    public boolean getState() {
        return state;
    }


}
