package org.example.Server;

import lombok.Getter;
import lombok.Setter;
import org.example.Clients.ClientHandler;
import org.example.Enums.EnumID;
import org.example.Users.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static java.lang.System.out;
import static org.example.Users.User.ListSide;

@Getter
@Setter

public class Server {
    private ServerSocket serverSocket;
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    public void startServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                out.println("SOCKET IS OPEN");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }

        }catch (IOException e){
            out.println("Error Programing"+e);
        }
    }
public void closeServerSocket(){
        try{
            if (serverSocket!=null){
                serverSocket.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
}
    public static void main(String[] args) {
        User jack = new User("jackson",23, EnumID.BOY);
        User chidera = new User("chidera",23, EnumID.GIRL);
        ListSide.add(jack);
        ListSide.add(chidera);
        out.println(ListSide);
        out.println(ListSide.size());
        try {

            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();


        }catch (Exception e){
            System.out.println(e);
        }
    }

}
