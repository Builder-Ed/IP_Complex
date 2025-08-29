package CPLX_Utils;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class CPLX_IO
{
    File file;
    Scanner scanner;
    public Vector<String> ClientVec = new Vector<String>(32,32);
    public Vector<String> CPLX_Load_File(String filename)
    {
        try
        {
        file = new File(filename);
        scanner = new Scanner(file);
        while(scanner.hasNextLine())
        {
            String IOBuffer = scanner.nextLine();
            if (IOBuffer.contains("/")) {
                ClientVec.addElement(IOBuffer.substring(0,IOBuffer.indexOf("/")));
                ClientVec.addElement(IOBuffer.substring(IOBuffer.indexOf("/")+1,IOBuffer.length()));
            }
        }
        scanner.close();
        return ClientVec;
        }
        catch(IOException e)
        {
            System.out.print("[CPLX_IO] Unable to load file:\t");
            System.out.println(filename);
            System.out.print("\t`");
            System.err.println(e);
            return null;
        }
    }
    public void CPLX_Update_File(String filename)
    {
        try
        {
            RandomAccessFile raf = new RandomAccessFile(filename,"rw");
            raf.seek(0);
            for(int n = 0; n < ClientVec.size() / 2; n++)
            {
                raf.write(ClientVec.get(n * 2).getBytes());
                //raf.write(("/").getBytes());
                raf.write(ClientVec.get(n * 2 + 1).getBytes());
                raf.write(("\n").getBytes());
            }
            raf.close();
        }
        catch(IOException e)
        {
            System.out.print("[CPLX_IO] Unable to write to file:\t");
            System.out.println(filename);
            System.out.print("\t`");
            System.err.println(e);
        }
    }
    public void CPLX_Append_Client(String clientID, String clientAddr)
    {

        if(ClientVec.contains(clientID) && ClientVec.contains(clientAddr))
        {
            for(int n = 0; n < ClientVec.size() / 2; n++)
            {
                if(ClientVec.get(2 * n).equals(clientID) 
                && ClientVec.get(2 * n + 1).equals(clientAddr)
                ) 
                { 
                System.out.println(ClientVec); return ; }
            }
            //ClientVec.set((ClientVec.indexOf(clientID)+1),clientAddr);
        }
        ClientVec.addElement(clientID);
        ClientVec.addElement(clientAddr);
        System.out.println(ClientVec);
    }


    public void CPLX_Delete_Client(String clientID, String clientAddr)
    {

        if(ClientVec.contains(clientID) && ClientVec.contains(clientAddr))
        {
            for(int n = 0; n < ClientVec.size() / 2; n++)
            {
                if(ClientVec.get(2 * n).equals(clientID) 
                && ClientVec.get(2 * n + 1).equals(clientAddr)
                ) 
                { ClientVec.remove(2 * n); ClientVec.remove(2 * n); 
                System.out.println("[CPLX_IO] Removed " + clientAddr + " from " + clientID); 
                System.out.println(ClientVec); return ; }
            }
            //ClientVec.set((ClientVec.indexOf(clientID)+1),clientAddr);

        }
        System.out.println("[CPLX_IO] No Matching Address found for " + clientAddr + clientID); 
        System.out.println(ClientVec);
    }

    public String CPLX_Check_Client(String clientID)
    {
        if(ClientVec.contains(clientID))
        {
            return ClientVec.get(ClientVec.indexOf(clientID)+1);
        }
        return null;
    }
    public void CPLX_Fetch_Client(Socket connection,CPLX_IO cplx_io,String clientID) 
    {
        try 
        {
            OutputStream outputStream = connection.getOutputStream();
            String  outputString = new String();
            for(int n = 0; n < cplx_io.ClientVec.size() / 2; n++)
            {
                if(!clientID.toLowerCase().equals("all")){
                    if(cplx_io.ClientVec.get(2 * n).equals(clientID)){
                        outputString += cplx_io.ClientVec.get(2 * n);
                        outputString += "\t";
                        outputString += cplx_io.ClientVec.get(2 * n + 1);
                        outputString += "\n";
                    }
                }
                else
                {
                    outputString += cplx_io.ClientVec.get(2 * n);
                    outputString += "\t";
                    outputString += cplx_io.ClientVec.get(2 * n + 1);
                    outputString += "\n";
                }
            }
            if (outputString.equals("")) {
                outputString = "[CPLX_IO] clientID '" + clientID + "' non-existent!";
            }
            outputStream.write(outputString.getBytes());
        } catch (Exception e) 
        {
            System.out.print("[CPLX_NET] Unable to write to conection ");
            System.out.println(connection.getInetAddress().toString());
            System.out.print("\t`");
            System.err.println(e);
        }
            
            
    }
    
}
