package com.example.demo.PontoAPonto;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Produtor {

    public static void main(String[] args) {
        /*Cria uma fábrica de conexões*/

        // Classe usada para configurar e criar conexões com o servidor RabbitMQ
        ConnectionFactory factory = new ConnectionFactory();

        // Configurando nome de usuário
        factory.setUsername( "admin" );

        // Configurando a senha
        factory.setPassword( "123" );

        // Definindo o endereço do host
        factory.setHost( "localhost" );

        // Estabelecendo uma conexão com o servidor do RabbitMQ
        try ( Connection connection = factory.newConnection() ) {

            // Criando um canal após o estabelecimento da conexão
            // Todas as operações como: declarar filas, publicar e consumir mensagens são feitas através do canal
            Channel channel = connection.createChannel();

            // Declarando a fila "hello". Se ela não existir, será criada.
            //   -> (nome da fila, se a permanece após reinicio, se a fila pode ser acessada por multiplos consumidores,
            //   se a fila será auto-deletada quando o ultimo consumidor desconectar, argumentos adicionais)
            channel.queueDeclare("hello", false, false, false, null);

            // Definição de uma String
            String mensagem = "Ola mundo!";

            // método utilizado para publicar mensagens em uma fila
            //  -> ( nome do exchange, nome da fila para onde a mensagem será enviada, propriedades adicionais da mensagem,
            //  conversão da mensagem para bytes utilizando UTF-8)
            channel.basicPublish( "", "hello", null, mensagem.getBytes( StandardCharsets.UTF_8 ));

        } catch ( IOException | TimeoutException e ) {
            e.printStackTrace();
        }
    }
}
