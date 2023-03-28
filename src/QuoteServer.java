import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuoteServer {
    private static final int PORT = 9000;
    private static final String QUOTE_1 = "The best way to predict the future is to create it. - Abraham Lincoln";
    private static final String QUOTE_2 = "The only way to do great work is to love what you do. - Steve Jobs";
    private static final String QUOTE_3 = "Believe you can and you're halfway there. - Theodore Roosevelt";
    private static final List<String> QUOTES = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize the list of quotes
        QUOTES.add(QUOTE_1);
        QUOTES.add(QUOTE_2);
        QUOTES.add(QUOTE_3);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Wait for a client to connect
                System.out.println("Waiting for a client to connect...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                // Handle the client request
                handleClientRequest(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Error running the server: " + e.getMessage());
        }
    }

    private static void handleClientRequest(Socket clientSocket) throws IOException {
        // Get the input and output streams of the client socket
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Send a random quote to the client
        String randomQuote = getRandomQuote();
        System.out.println(randomQuote);
        out.println(randomQuote);

        // Close the streams and the client socket
        in.close();
        out.close();
        clientSocket.close();
    }

    private static String getRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(QUOTES.size());
        return QUOTES.get(index);
    }
}