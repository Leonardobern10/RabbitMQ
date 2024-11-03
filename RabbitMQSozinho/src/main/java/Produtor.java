import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Produtor {

    private static final String QUEUE_NAME = "firstQueue";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername("admin");
        factory.setPassword("123");
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = "Segunda mensagem!";

            channel.basicPublish( "", QUEUE_NAME, null, message.getBytes() );

            System.out.println("[x] Mensagem enviada: '" + message + "'");
        }
    }
}
