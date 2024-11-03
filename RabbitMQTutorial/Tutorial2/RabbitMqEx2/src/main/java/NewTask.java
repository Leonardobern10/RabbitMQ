import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    private static final String TASk_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername( "admin" );
        factory.setPassword( "123" );
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASk_QUEUE_NAME, true, false, false, null);

            String message = String.join(" ", "ola", "meu nome");

            channel.basicPublish( "", TASk_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes() );

            System.out.println(" [x] Enviado '" + message + "'");

        }
    }
}
