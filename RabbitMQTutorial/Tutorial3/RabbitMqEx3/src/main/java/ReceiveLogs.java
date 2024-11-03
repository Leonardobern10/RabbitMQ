import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername( "admin" );
        factory.setPassword( "123" );
        factory.setHost( "localhost" );

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare( EXCHANGE_NAME, "fanout" );

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind( queueName, EXCHANGE_NAME, "" );

        System.out.println(" [*] Aguardando mensagens. Para sair, pressione CTRL + C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String (delivery.getBody(), "UTF-8");
            System.out.println(" [x] Recebido '" + message + "'");
        };
        channel.basicConsume( queueName, true, deliverCallback, consumerTag -> {} );
    }
}
