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
    private static Connection connection;
    private static Channel channel;
    private static ConnectionFactory factory;
    public static int RabbitPort = 27003;
    public static String RabbitIP = "172.18.0.1";

    public void onEnable(Plugin plugin) {
        state = true;
        initConnection();
        Data.activateListener();
    }

    public void onDisable() {

    }

    public boolean getState() {
        return state;
    }

    public static void initConnection() {
        factory = new ConnectionFactory();
        factory.setUsername("adfeahajulort");
        factory.setPassword("oyqiwebyif");
        factory.setPort(RabbitPort);
        factory.setHost(RabbitIP);

        /*try (Connection connect = factory.newConnection()){
            connection = connect;
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            return;
        }

        try (Channel chan = connection.createChannel()) {
            chan.exchangeDeclare("faction", "topic");
            channel = chan;

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
            return;
        }*/

    }


    public static Connection getConnection() {
        return connection;
    }

    public static Channel getChannel() {
        return channel;
    }


    public static ConnectionFactory getFactory() {
        return factory;
    }
}
