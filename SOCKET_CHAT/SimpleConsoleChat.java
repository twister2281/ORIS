import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleConsoleChat {
    private static final int PORT = 5555;
    private static final List<String> DEFAULT_USERS = Arrays.asList("admin", "xuesos");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static void main(String[] args) throws InterruptedException {
        System.out.println("КОНСОЛЬНЫЙ ЧАТ");
        System.out.println("1 - Запустить сервер");
        System.out.println("2 - Запустить клиент");
        System.out.print("Выберите вариант: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) {
            startServer();
        } else if (choice == 2) {
            startClient();
        } else {
            System.out.println("Запуск сервера и клиента вместе...");
            new Thread(() -> startServer()).start();
            Thread.sleep(1000);
            startClient();
        }
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);
            System.out.println("Ожидание подключений...");

            List<Socket> clients = new ArrayList<>();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.println("Новый клиент подключен");

                new Thread(() -> handleClient(clientSocket, clients)).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket, List<Socket> clients) {
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String username = null;
            out.println("Введите ваше имя (admin или xuesos):");

            String input;
            while ((input = in.readLine()) != null) {
                if (DEFAULT_USERS.contains(input)) {
                    username = input;
                    out.println("Добро пожаловать, " + username + "!");
                    System.out.println(username + " присоединился к чату");
                    broadcast(clients, clientSocket,
                            TIME_FORMAT.format(new Date()) + " " + username + " присоединился к чату");
                    break;
                } else {
                    out.println("Неверное имя. Попробуйте еще раз:");
                }
            }

            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("/exit")) {
                    break;
                }
                String message = TIME_FORMAT.format(new Date()) + " " + username + ": " + input;
                System.out.println(message);
                broadcast(clients, clientSocket, message);
            }

        } catch (IOException e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        } finally {
            clients.remove(clientSocket);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void broadcast(List<Socket> clients, Socket sender, String message) {
        for (Socket client : clients) {
            if (client != sender && !client.isClosed()) {
                try {
                    PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);
                    clientOut.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void startClient() {
        try (
                Socket socket = new Socket("localhost", PORT);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
        ) {
            Thread receiver = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Отключен от сервера");
                }
            });
            receiver.start();

            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);

                if (userInput.equalsIgnoreCase("/exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Не удалось подключиться к серверу: " + e.getMessage());
            System.err.println("Убедитесь, что сервер запущен на порту " + PORT);
        }
    }
}