package Peer.Business;

import Tracker.Model.Torrent;

import java.io.*;
import java.net.Socket;

public class ProtocolCommunicator extends Thread{

    private String ip;
    private int port;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private OutputStream   outputStream;


    @Override
    public void run() {
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("连接成功");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            outputStream = socket.getOutputStream();
            String msg = null;
            while ((msg = bufferedReader.readLine()) != null) {

                String [] msgs = msg.split(" ");

                if(msgs[0].equals("")){

                }


            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void Send(String str) {
        printWriter.println(str);
    }
}
