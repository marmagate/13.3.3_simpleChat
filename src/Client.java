import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {

    Socket socket;
    ChatServer chatServer;
    Scanner in;
    PrintStream out;
    String nickname;

    public Client(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
    }

    void receive(String message) {
        out.println(message);
    }

    void getNickname() {
        this.nickname = in.nextLine();
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!\nInput your nickname: ");
            getNickname();
            String input = in.nextLine();
            while (!input.equals("bye")) {
                chatServer.sendAll(nickname + ": " + input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}