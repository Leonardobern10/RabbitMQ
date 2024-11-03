import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Consumidor {

    private static final String QUEUE_NAME = "firstQueue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("admin");
        factory.setPassword("123");
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Aguardando mensagens... Pressione CTRL+C para sair!");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String (delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Mensagem recebida '" + message + "'");
        };

        channel.basicConsume( QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }
}
