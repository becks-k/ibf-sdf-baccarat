package sg.edu.nus.iss.baccarat.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class NetworkIO {
    private DataInputStream dis;
    private DataOutputStream dos;

    public NetworkIO(Socket socket) throws IOException{
        dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public String readFrom() throws IOException {
        return dis.readUTF();
    }

    public void writeTo(String input) throws IOException {
        dos.writeUTF(input);
        dos.flush();
    }

    public void close() throws IOException {
        dos.close();
        dis.close();
    }
    
}
