package CPLX_Utils;
import java.io.*;
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
            ClientVec.addElement(IOBuffer.substring(0,IOBuffer.indexOf("/")));
            ClientVec.addElement(IOBuffer.substring(IOBuffer.indexOf("/")+1,IOBuffer.length()));
        }
        scanner.close();
        return ClientVec;
        }
        catch(IOException e)
        {
            System.out.print("[CPLX_IO]Unable to load file:\t");
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

        }
        catch(IOException e)
        {
            System.out.print("[CPLX_IO]Unable to write to file:\t");
            System.out.println(filename);
            System.out.print("\t`");
            System.err.println(e);
        }
    }
    public void CPLX_Update_Client(String clientID, String clientAddr)
    {

        if(ClientVec.contains(clientID))
        {
            ClientVec.set((ClientVec.indexOf(clientID)+1),clientAddr);
            return;
        }
        ClientVec.addElement(clientID);
        ClientVec.addElement(clientAddr);
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
}
