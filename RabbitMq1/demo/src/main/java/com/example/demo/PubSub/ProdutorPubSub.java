package com.example.demo.PubSub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ProdutorPubSub {
    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {

        // Configurando a fábrica de conexões
        ConnectionFactory factory = new ConnectionFactory();

        // Configurando as credenciais
        factory.setUsername( "admin" );
        factory.setPassword( "123" );
        factory.setHost( "localhost" );

        // Tentativa de conexão
        try ( Connection connection = factory.newConnection(); Channel channel = connection.createChannel() ) {

            // Declara um exchange do tipo 'fanout'
            channel.exchangeDeclare( EXCHANGE_NAME, "fanout" );

            // Mensagem que será enviada
            String mensagem = "Esta é uma mensagem de broadcast para todos os consumidores!";

            // Publica a mensagem no exchange
            channel.basicPublish( EXCHANGE_NAME, "", null, mensagem.getBytes( StandardCharsets.UTF_8 ) );
            System.out.println("Mensagem publicada: '" + mensagem + "'");

        }
    }
}
