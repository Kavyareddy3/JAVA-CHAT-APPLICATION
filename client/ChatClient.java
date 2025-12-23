import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatClient extends Application {

    private PrintWriter out;
    private BufferedReader in;
    private TextArea chatArea;
    private TextField inputField;

    @Override
    public void start(Stage stage) throws Exception {

        Socket socket = new Socket("localhost", 1234);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        chatArea = new TextArea();
        chatArea.setEditable(false);

        inputField = new TextField();
        inputField.setPromptText("Type your message here...");
        inputField.setOnAction(e -> sendMessage()); // Press Enter to send

        Button sendBtn = new Button("Send");
        sendBtn.setOnAction(e -> sendMessage());

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(chatArea, inputField, sendBtn);

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Java Chat Client");
        stage.show();

        new Thread(this::receiveMessages).start();
    }

    private void sendMessage() {
        String msg = inputField.getText();
        if (!msg.isEmpty()) {
            out.println(encrypt(msg));
            inputField.clear();
        }
    }

    private void receiveMessages() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                chatArea.appendText(msg + "\n");
            }
        } catch (IOException e) {
            chatArea.appendText("Connection closed.\n");
        }
    }

    // Encryption (Caesar Cipher)
    private String encrypt(String msg) {
        StringBuilder sb = new StringBuilder();
        for (char c : msg.toCharArray()) {
            sb.append((char) (c + 2));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
