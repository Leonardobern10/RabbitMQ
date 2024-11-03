package com.example.demo.PubSub;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ConsumidorPubSub2 {

    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {

        // Configurando a fabrica de conexões
        ConnectionFactory factory = new ConnectionFactory();

        // Configurando as credenciais
        factory.setUsername("admin");
        factory.setPassword( "123" );
        factory.setHost("localhost");

        // Tentativa de conexão e criação do canal
        try ( Connection connection = factory.newConnection(); Channel channel = connection.createChannel() ) {

            // Declara a exchange e seu tipo
            channel.exchangeDeclare( EXCHANGE_NAME, "fanout" );

            // Declara uma fila temporaria e exclusiva para este consumidor
            String queueName = channel.queueDeclare().getQueue();

            // Conecta a fila ao exchange
            channel.queueBind( queueName, EXCHANGE_NAME, "" );

            System.out.println("Esperando por mensagens ...");

            // Define o callback para quando uma mensagem for recebida
            Consumer consumer = new DefaultConsumer( channel ) {
                @Override
                public void handleDelivery (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    if (body == null || body.length == 0) {
                        System.out.println("Mensagem recebida está vazia!");
                    } else {
                        String mensagem = new String(body, StandardCharsets.UTF_8);
                        System.out.println("Mensagem recebida: " + mensagem);
                    }
                }
            };

            // Iniciar o consumo da fila
            channel.basicConsume( queueName, true, consumer);
        } catch ( IOException | TimeoutException e ) {
            throw new RuntimeException( e );
        }
    }
}
