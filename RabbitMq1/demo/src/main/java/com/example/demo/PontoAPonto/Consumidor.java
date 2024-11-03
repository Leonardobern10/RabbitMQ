package com.example.demo.PontoAPonto;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Consumidor {

    public static void main(String[] args) throws IOException, TimeoutException {
        /*Cria uma fábrica de conexões*/
        ConnectionFactory factory = new ConnectionFactory();

        // Definição das credenciais no objeto factory
        factory.setUsername( "admin" );
        factory.setPassword( "123" );
        factory.setHost( "localhost" );

        // Tentativa de estabelecimento de conexão com servidor RabbitMQ
        try ( Connection connection = factory.newConnection()) {

            // Cria um canal após o estabelecimento da conexão para
            // a realização das operações: declarar filas, publicar e consumir mensagens
            Channel channel = connection.createChannel();

            // Declaração da primeira fila. Se não existe, será criada
            channel.queueDeclare("hello", false, false, false, null);

            /* Este é o callback que será invocado toda vez que uma mensagem
            for entregue ao consumidor.
            */
            Consumer consumer = new DefaultConsumer( channel ) {

                /*
                * Este método é chamado quando uma mensagem chega
                *
                * Body -> contém o corpo da mensagem em formato de byte array.
                * O array de bytes da mensagem é convertido em um string UTF-8
                * e, em seguida, impresso no console;
                * */
                @Override
                public void handleDelivery (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String mensagem = new String(body, StandardCharsets.UTF_8 );
                    System.out.println("Mensagem recebida: " + mensagem);
                }
            };

            /* Método utilizado para consumir mensagens em uma fila
            *
            * "hello" -> A fila do qual ele está consumindo
            * true -> indica que a confirmação de recebimento é automática
            * */
            channel.basicConsume( "hello", true, consumer );
        } catch ( IOException | TimeoutException e ) {
            e.printStackTrace();
        }
    }
}
