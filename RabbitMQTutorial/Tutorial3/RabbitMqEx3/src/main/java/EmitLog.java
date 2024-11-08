import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername( "admin" );
        factory.setPassword( "123" );
        factory.setHost( "localhost" );

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare( EXCHANGE_NAME , "fanout" );

            String message = args.length < 1 ? "info: Hello World!" : String.join( "", args );

            channel.basicPublish( EXCHANGE_NAME, "", null, message.getBytes( StandardCharsets.UTF_8 ) );
            System.out.println("[x] Enviado '" + message + "'");
        }
    }
}
