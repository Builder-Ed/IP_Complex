package CPLX_Utils;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CPLX_NET
{
    Socket          socket;
    ServerSocket    serverSocket;
    InetAddress[]           clientIPList = new InetAddress[1024];
    int             clientNum = 0;

    public void CPLX_Establish_Connection(String targetAddr, int targetPort)
    {
        try {
            socket = new Socket(targetAddr, targetPort);
        } catch (Exception e) {
            System.out.print("[CPLX_NET]Unable to Connect to Address: ");
            System.out.print(targetAddr);
            System.out.print(":");
            System.out.println(targetPort);
            System.out.print("\t`");
            System.err.println(e);
        }
    }

    public void CPLX_Start_Listening(int srcPort)
    {
        try {
            serverSocket = new ServerSocket(srcPort);
            System.out.println("[CPLX_NET]Listening on port : " + srcPort);
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    System.out.println("[CPLX_NET]Connection established:\t" + connection.getInetAddress() + connection.getPort());
                    
                    // Handle client in a separate thread
                    new Thread(() -> CPLX_RECORD_CLIENT(connection)).start();
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

    private void CPLX_RECORD_CLIENT(Socket connection) 
    {
            clientIPList[clientNum] = connection.getInetAddress();
            clientNum++;
            System.out.println(clientIPList[clientNum-1]);
    }
}