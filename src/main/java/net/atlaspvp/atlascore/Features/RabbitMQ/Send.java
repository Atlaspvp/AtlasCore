package net.atlaspvp.atlascore.Features.RabbitMQ;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;


public class Send {

    //private final static String QUEUE_NAME = "hello";

    public static void distributePlayer(UUID playerUUID) throws Exception {
        String queueName = "Online_Players";

        ConnectionFactory factory = RabbitMQ.factory;

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueName, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

    }
}
