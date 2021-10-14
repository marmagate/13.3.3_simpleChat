import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    ServerSocket serverSocket;
    ArrayList<Client> clientsArray = new ArrayList<>();

    public ChatServer() throws IOException {
        // создаем серверный сокет на порту 1234
        serverSocket = new ServerSocket(1234);
    }

    void sendAll(String message) {
        for (Client client : clientsArray) {
            client.receive(message);
        }
    }

    public void run() throws IOException {
        while (true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            // создаем клиента на своей стороне
            Client client = new Client(socket, this);
            clientsArray.add(client);
            // запускаем поток
            Thread thread = new Thread(client);
            thread.start();
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}