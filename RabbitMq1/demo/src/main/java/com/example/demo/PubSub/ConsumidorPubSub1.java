package com.example.demo.PubSub;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ConsumidorPubSub1 {

    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {

        // Configurando a fábrica de conexões
        ConnectionFactory factory = new ConnectionFactory();

        // Configurando as credenciais
        factory.setUsername("admin");
        factory.setPassword("123");
        factory.setHost("localhost");

        // Tentativa de conexão
        try ( Connection connection = factory.newConnection(); Channel channel = connection.createChannel() ) {

            // Declara um exchange do tipo 'fanout'
            channel.exchangeDeclare( EXCHANGE_NAME, "fanout" );

            // Declara uma fila temporária e exclusiva para este consumidor
            String queueName = channel.queueDeclare().getQueue();

            // Liga a fila ao exchange
            channel.queueBind( queueName, EXCHANGE_NAME, "" );

            System.out.println("Esperando por mensagens...");

            // Define o callback para quando uma mensagem for recebida
            Consumer consumer = new DefaultConsumer( channel ) {
                @Override
                public void handleDelivery (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                    String mensagem = new String (body, StandardCharsets.UTF_8 );
                    System.out.println("Consumidor 1 recebeu: '" + mensagem + "'");
                }
            };
            channel.basicConsume( queueName, true, consumer );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        } catch ( TimeoutException e ) {
            throw new RuntimeException( e );
        }
    }
}
