package net.atlaspvp.atlascore.Features.RabbitMQ;

import com.rabbitmq.client.*;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Data {
    public static Channel channel = RabbitMQ.getChannel();
    public static String routingKey = "change.factions";

    public static void sendData(Serializable saving, String QUEUEName) {

        ConnectionFactory factory = RabbitMQ.getFactory();

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel(); // Create a new channel in rabbitMQ

            channel.queueDeclare(QUEUEName, false, false, false, null);
            channel.exchangeDeclare("faction", "topic");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(saving);
            out.close();

            channel.basicPublish("factions", routingKey, null, bos.toByteArray());

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }




        /*try {
            Channel channel = RabbitMQ.getFactory().newConnection().createChannel();

            channel.queueDeclare(QUEUEName, false, false, false, null);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(saving);
            out.close();
            byte[] serializedSaved = bos.toByteArray();

            channel.basicPublish("factions", routingKey, null, serializedSaved);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }*/

    }


    public static void activateListener() {
        ConnectionFactory factory = RabbitMQ.getFactory();

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel(); // Create a new channel in rabbitMQ

            //channel.queueDeclare("factiontest", false, false, false, null);
            //channel.exchangeDeclare("faction", "topic");


            channel.queueBind("factiontest", "factions", routingKey);

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                Bukkit.getServer().getLogger().info("message received");
                String deserializedMessage = new String(message.getBody(), "UTF-8");
                Bukkit.getServer().getLogger().info(deserializedMessage);
            };
            channel.basicConsume("factiontest", true, deliverCallback, consumerTag -> { });


        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }


        /*try {
            channel.queueBind("factiontest", "factions", routingKey);

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                String deserializedMessage = new String(message.getBody(), "UTF-8");
                Bukkit.getServer().getLogger().info(deserializedMessage);
            };
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }*/
    }

    /*public static byte[] pullData(String QUEUEName) {

    }*/
}
