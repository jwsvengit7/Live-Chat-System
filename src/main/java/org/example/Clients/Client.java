package org.example.Clients;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


import static java.lang.System.out;
import static org.example.ASCIIColors.ASCIIColors.ANSI_BLUE;
import static org.example.ASCIIColors.ASCIIColors.ANSI_GREEN;


@Getter
@Setter
public class Client {
    private String username;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket,String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username  = username;

        }catch (Exception e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void sendMessage(){
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messages = scanner.nextLine();
                bufferedWriter.write(ANSI_GREEN+username + ": " +ANSI_BLUE+ messages);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (Exception e){
            closeEverything(socket,bufferedReader,bufferedWriter);
            out.println(e);
        }
    }

    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){

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
    public  void listOfMessage(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String msgI;
                while (socket.isConnected()){
                    try{
                        msgI=bufferedReader.readLine();
                        out.println(msgI);
                    }catch (IOException e){
                        out.println(e);
                    }
                }
            }

        }).start();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        out.println("Enter your username");
        String names = scanner.nextLine();
       // if (names.equals(clientHandlerArrayList.get(0).getUsername()) && clientHandlerArrayList.get(0).ge)
        Socket socket1 = new Socket("localhost",1234);
        Client client = new Client(socket1,names);
        client.listOfMessage();
        client.sendMessage();


    }
}
