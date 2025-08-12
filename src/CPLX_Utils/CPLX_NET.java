package CPLX_Utils;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class CPLX_NET
{
    Socket          socket;
    ServerSocket    serverSocket;

    public void CPLX_Establish_TCP_Connection(String targetAddr, int targetPort)
    {
        try {
            socket = new Socket(targetAddr, targetPort);
            OutputStream outputStream = socket.getOutputStream();
            byte[] buffer;
            buffer = "Buildesdfasdfr_Ed_".getBytes();
            outputStream.write(buffer);
            outputStream.close();
        } catch (Exception e) {
            System.out.print("[CPLX_NET]Unable to Connect to Address: ");
            System.out.print(targetAddr);
            System.out.print(":");
            System.out.println(targetPort);
            System.out.print("\t`");
            System.err.println(e);
        }
    }

    public void CPLX_Start_Listening_TCP(int srcPort,String filename)
    {
        try {
            serverSocket = new ServerSocket(srcPort);
            System.out.println("[CPLX_NET]Listening on port : " + srcPort);

            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    System.out.println("[CPLX_NET]Connection established:\t" + connection.getInetAddress() +":"+ connection.getPort());
                    
                    // Handle client in a separate thread
                    new Thread(() -> CPLX_Record_Client(connection,filename)).start();
                } catch (Exception e) {
                    System.err.println("[CPLX_NET]Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.print("[CPLX_NET]Unable to start listening on port ");
            System.out.println(srcPort);
            System.out.print("\t`");
            System.err.println(e);
        }
    }

    private void CPLX_Record_Client(Socket connection,String filename) 
    {
        try 
        {
            byte[]  buffer = new byte[1024];
            try (InputStream inputStream = connection.getInputStream()) {
                
                inputStream.read(buffer);
            }
            CPLX_IO cplx_io = new CPLX_IO();
            String clientID   = buffer.toString();
            String clientAddr = connection.getInetAddress().toString().trim();
            cplx_io.CPLX_Update_Client(clientID, clientAddr);
            cplx_io.CPLX_Update_File(filename);
        } catch (Exception e) 
        {
            System.out.print("[CPLX_NET]Unable to read from conection to ");
            System.out.println(connection.getInetAddress().toString());
            System.out.print("\t`");
            System.err.println(e);
        }
            
            
    }
}