package CPLX_Utils;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class CPLX_NET
{
    Socket          socket;
    ServerSocket    serverSocket;

    public void CPLX_Establish_TCP_Connection(String targetAddr, int targetPort,String clientID,String clientAddr,String modeString)
    {
        try {
            
            socket = new Socket(targetAddr, targetPort);
            OutputStream outputStream = socket.getOutputStream();
            if (clientAddr.equals("CURRENT")) { clientAddr = socket.getInetAddress().getHostAddress().toString();}
            String stringBuffer;
            stringBuffer =  modeString+"|"+clientID+"$"+clientAddr;
            outputStream.write(stringBuffer.getBytes());
            new Thread(() -> CPLX_Update_Client(socket,"[cli]clients.log")).start();
            
            

        } catch (Exception e) {
            System.out.print("[CPLX_NET] Unable to Connect to Address: ");
            System.out.print(targetAddr);
            System.out.print(":");
            System.out.println(targetPort);
            System.out.print("\t`");
            System.err.println(e);
        }
    }

    public void CPLX_Start_Listening_TCP(int srcPort,String filename,CPLX_IO cplx_io)
    {
        try {
            serverSocket = new ServerSocket(srcPort);
            System.out.println("[CPLX_NET]Listening on port : " + srcPort);
            //if (!filename.equals("")) { cplx_io.CPLX_Load_File(filename); }
            
            cplx_io.CPLX_Load_File(filename);
            while (true) {
                try {
                    Socket connection = serverSocket.accept();
                    System.out.println("[CPLX_NET] Connection established:\t" + connection.getInetAddress() +":"+ connection.getPort());
                    
                    new Thread(() -> CPLX_Record_Client(connection,filename,cplx_io)).start();

                } catch (Exception e) {
                    System.err.println("[CPLX_NET] Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.print("[CPLX_NET] Unable to start listening on port ");
            System.out.println(srcPort);
            System.out.print("\t`");
            System.err.println(e);
        }
    }

    private void CPLX_Record_Client(Socket connection,String filename,CPLX_IO cplx_io) 
    {
        try 
        {
            byte[]  buffer = new byte[1024];//FETCH / APPEND / DELETE|clientID$clientAddr
            int     mode   = 0;// 0: Append; 1:Fetch; -1:Delete
            InputStream inputStream = connection.getInputStream();
            inputStream.read(buffer);

            if(new String(buffer).trim().contains("FETCH")){mode = 1;}
            if(new String(buffer).trim().contains("APPEND")){mode = 0;}
            if(new String(buffer).trim().contains("DELETE")){mode = -1;}
            
            String stringBuffer = new String(buffer).trim();
            String clientID   = stringBuffer.replace("FETCH", "").replace("APPEND", "").replace("DELETE", "");
            clientID = clientID.substring(clientID.indexOf("|")+1, clientID.indexOf("$"));
            
            String clientAddr = "/"+stringBuffer.substring(stringBuffer.indexOf("$")+1);
            if (clientAddr.equals("/NONE")) {
                clientAddr = connection.getInetAddress().toString().trim();
            }
            if (!clientAddr.contains(".")) { 
                connection.getOutputStream().write(("[CPLX_NET] Invalid Address: " + clientAddr).getBytes()); return; 
            }

            if(mode == 0)
            {
                cplx_io.CPLX_Append_Client(clientID, clientAddr);
                cplx_io.CPLX_Update_File(filename);
                cplx_io.CPLX_Fetch_Client(connection,cplx_io,clientID);
            }
            else if (mode == 1)
            {
                cplx_io.CPLX_Fetch_Client(connection,cplx_io,clientID);
            }
            else if (mode == -1)
            {
                cplx_io.CPLX_Delete_Client(clientID,clientAddr);
                cplx_io.CPLX_Update_File(filename);
                cplx_io.CPLX_Fetch_Client(connection,cplx_io,clientID);
            }
            
        } catch (Exception e) 
        {
            System.out.print("[CPLX_NET] Unable to read from conection to ");
            System.out.println(connection.getInetAddress().toString());
            System.out.print("\t`");
            System.err.println(e);
        }
            
            
    }

    private void CPLX_Update_Client(Socket connection,String filename) 
    {
        try 
        {
            byte[]  buffer = new byte[1024];
            try (InputStream inputStream = connection.getInputStream()) {
                inputStream.read(buffer);
            }
            System.out.println(new String( buffer ) );
        }
        catch (Exception e) 
        {
            System.out.print("[CPLX_NET] Unable to read from conection to ");
            System.out.println(connection.getInetAddress().toString());
            System.out.print("\t`");
            System.err.println(e);
        }
    }
}