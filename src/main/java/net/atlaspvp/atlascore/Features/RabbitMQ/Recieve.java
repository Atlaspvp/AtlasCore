package net.atlaspvp.atlascore.Features.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import net.atlaspvp.atlascore.Features.RabbitMQ.Players.OnlinePlayers;

import java.util.UUID;

public class Recieve {

    /* private final static String QUEUE_NAME = "queue_name";

    public static void receive() throws Exception {
        ConnectionFactory factory = RabbitMQ.factory;
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages.");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }*/



    public static void receiveOnlinePlayer() throws Exception {
        String QUEUE_NAME = "Online_Players";
        ConnectionFactory factory = RabbitMQ.factory;
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for players.");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String uuid = new String(delivery.getBody(), "UTF-8");
            System.out.println("Player received");
            OnlinePlayers.logOnPlayer(UUID.fromString(uuid));
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

    }

}
