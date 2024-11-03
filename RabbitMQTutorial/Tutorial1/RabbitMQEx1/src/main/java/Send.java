import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {

    // Definindo como constante o nome da fila
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername( "admin" );
        factory.setPassword( "123" );

        // Se quiséssemos nos conectar a um nó em outra máquina,
        // simplesmente especificaríamos o nome do host ou endereço
        // Ip aqui.
        factory.setHost( "localhost" );

        // Utilizamos try-with-resources pois Connection e Channel
        // implementam a interface java.lang.AutoCloseable
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Para enviar precisamos declarar uma fila para onde
            // enviaremos as mensagens
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Definição da mensagem
            String message = "Como é bom quando as coisas funcionam!";

            // Publicação da mensagem na exchange padrão para que seja
            // direcionada para a fila QUEUE_NAME
            channel.basicPublish( "", QUEUE_NAME, null, message.getBytes() );

            // Retorno de logs para verificar o envio
            System.out.println(" [x] Sent '" + message + "'");

            // Declarar uma fila é idempotente: ela só será criada se ainda
            // não existir. O conteudo da mensagem é um array de bytes
            // então é possivel codificar o que quiser ali.
        }
    };
}
