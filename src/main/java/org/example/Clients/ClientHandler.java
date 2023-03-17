package org.example.Clients;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.System.out;

@Setter
@Getter
public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlerArrayList =new ArrayList<>();
    private String username;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username  = bufferedReader.readLine();
            clientHandlerArrayList.add(this);
            broadCastmessage("SERVER: "+ username+" has enter a message");
        }
        catch (Exception e){
            closeEverything(socket,bufferedReader,bufferedWriter);

        }
    }

    @Override
    public void run() {
        String messegaes;
        try{
        while(!socket.isClosed()) {
            messegaes = bufferedReader.readLine();
            broadCastmessage(messegaes);
            break;
        }
        }catch(Exception e){
                out.println(e);
            }
        }

    public void removeChat(){
    clientHandlerArrayList.remove(this);
    broadCastmessage("SERVER: "+ username+" has left the message");
}

    public void broadCastmessage(String messages){
        for (ClientHandler clientHandler:clientHandlerArrayList){
            try {
                if (!clientHandler.username.equals(username)){
                    clientHandler.bufferedWriter.write(messages);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }

            }catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                out.println(e);
            }
        }
    }
 public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
     removeChat();
     try {
         if (socket!=null)
             socket.close();
         if (bufferedReader!=null)
             bufferedReader.close();
         if (bufferedWriter!=null)
             bufferedWriter.close();
     }catch (Exception e){
         out.println(e);
     }
}

}
