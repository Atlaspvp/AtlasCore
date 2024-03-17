package net.atlaspvp.atlascore.Features.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class Data {
    public static Channel channel = RabbitMQ.getChannel();
    public static String routingKey = "change.factions";

    public static void sendData(Serializable saving, String QUEUEName) {

        try {
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
        }

    }

    public static void activateListener() {
        try {
            channel.queueBind("factiontest", "factions", routingKey);

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
                     String mes = new String(message.getBody(), "UTF-8");
                     ObjectInputStream ois = new ObjectInputStream(bis)) {
                    // Read the object from the byte array
                    hashMap = (HashMap<String, String>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /*public static byte[] pullData(String QUEUEName) {

    }*/
}
