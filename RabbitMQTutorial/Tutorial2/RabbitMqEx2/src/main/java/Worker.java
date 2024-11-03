import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername( "admin" );
        factory.setPassword( "123" );
        factory.setHost( "localhost" );

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Aguardando mensagens. Para sair, pressione CTRL + C");

        // Define a quantidade máxima de mensagens não reconhecidas que o consumidor pode
        // receber, ajudando a balancear a carga entre múltiplos consumidores.
        channel.basicQos( 1 );

        // Interface que lida com a entrega de mensagens
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // Quando uma mensagem é recebida, ela é convertida em uma string
            // e exibida no console
            String message = new String (delivery.getBody(), "UTF-8");
            System.out.println("[x] Recebido '" + message + "'");

            try {
                doWork(message);
            } finally {
                System.out.println("[x] Feito");
                // Informa ao RabbitMQ que a mensagem foi processada com sucesso
                channel.basicAck( delivery.getEnvelope().getDeliveryTag(), false );
            }
        };

        channel.basicConsume( TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {} );
    }

    private static void doWork (String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep( 1000 );
                } catch ( InterruptedException _ignored ) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
