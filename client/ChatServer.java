import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    public static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Chat Server Started...");
        try (ServerSocket serverSocket = new ServerSocket(1234)) {

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                client.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, ClientHandler exclude) {
        for (ClientHandler client : clients) {
            if (client != exclude) {
                client.sendMessage(message);
            }
        }
    }

    static void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your nickname:");
            username = in.readLine();

            System.out.println(username + " connected.");
            ChatServer.broadcast(username + " joined the chat.", this);

            String msg;
            while ((msg = in.readLine()) != null) {
                msg = decrypt(msg);

                // Private chat
                if (msg.startsWith("@")) {
                    String[] parts = msg.split(" ", 2);
                    String target = parts[0].substring(1);

                    for (ClientHandler c : ChatServer.clients) {
                        if (c.username.equals(target)) {
                            c.sendMessage("(Private) " + username + ": " + parts[1]);
                        }
                    }
                }
                // Group chat
                else {
                    ChatServer.broadcast(username + ": " + msg, this);
                }
            }

        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            ChatServer.removeClient(this);
        }
    }

    // Decryption
    private String decrypt(String msg) {
        StringBuilder sb = new StringBuilder();
        for (char c : msg.toCharArray()) {
            sb.append((char) (c - 2));
        }
        return sb.toString();
    }
}
