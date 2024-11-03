import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
// Será utilizada para bufferizar as
// mensagens enviadas para nós pelos servidor
import com.rabbitmq.client.DeliverCallback;

/* A configuração é simples:
*
* 1 - Instanciamos a ConnectionFactory
* 2 - Definimos as credenciais
* 3 - Criamos uma conexão
* 4 - Criamos um canal
* 5 - Declaramos a fila
* 6 - Consumimos a mensagem
* */
public class Recv {

    // Definimos uma constante para o nome da fila
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername( "admin" );
        factory.setPassword("123");
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel =connection.createChannel();

        /*
        * Como podemos iniciar o consumidor antes do publicador,
        * queremos garantir que a fila exista antes de tentarmos
        * consumir mensagens dela.
        * */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Wainting for messages. To exit press CTRL + C");

        /*
        * Por que não usamos uma instrução try-with-resources para fechar automaticamente
        * o canal e a conexão? Se fizermos isso, o programa simplesmente avançaria, fecharia
        * tudo e sairia! Isso seria inconveniente, pois queremos que o processo continue ativo
        * enquanto o consumidor estiver escutando assincronamente por mensagens.
        * */

        /*
        * Estamos prestes a dizer ao servidor para nos entregar as mensagens da fila.
        * Como ele nos enviará mensagens assincronamente, fornecemos um callback na forma de
        * um objeto que bufferiza as mensagens até que estejamos prontos para usá-las. Isso é o
        * que uma subclasse de DeliverCallback faz.
        * */
        DeliverCallback deliverCallback = (consumeTag, delivery) -> {
            String message = new String (delivery.getBody(), "UTF-8");
            System.out.println("[x] Received '" + message + "'");
        };
        channel.basicConsume( QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }

}
