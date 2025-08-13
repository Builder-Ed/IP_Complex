import CPLX_Utils.*;
public class IP_Complex
{
    public static void print_usage()
    {
        System.out.println("Usage: java -jar IP_Complex.jar [OPTION]...");
        System.out.println("\tserver <port>\t\t\tserver mode, listen on <port>");
        System.out.println("\tserver <port> <filepath>\tserver mode, load recorded client and listen on <port>");
        System.out.println("\tclient <address> <clientID>\t\tclient mode, connect to <address> on port 8800 using <clientID>");
        System.out.println("\tclient <address> <port> <clientID>\t\tclient mode, connect to <address> on <port> using <clientID>");
    }
    public static void server(int srcPort,String filename)
    {
        CPLX_NET  cplx_net = new CPLX_NET();
        CPLX_IO   cplx_io  = new CPLX_IO();
        if(filename != null) {cplx_io.CPLX_Load_File(filename);}
        else{ filename = "clients.log"; }
        if(srcPort == 0) { cplx_net.CPLX_Start_Listening_TCP(8800,filename,cplx_io); }
        else { cplx_net.CPLX_Start_Listening_TCP(srcPort,filename,cplx_io); }
    }

    public static void client(String targetAddr, int targetPort, String clientID)
    {
        CPLX_NET  cplx_net  = new CPLX_NET();
        cplx_net.CPLX_Establish_TCP_Connection(targetAddr, targetPort, clientID);
    }
    public static void main(String[] args) throws Exception {

        if(args.length <= 0) { print_usage(); return;}
        if("server".equals(args[0].toLowerCase())) 
        {
            if(args.length < 2) { server(0,null); return; }
            int srcPort = 0;
            try{ srcPort = Integer.parseInt(args[1]); }
            catch(NumberFormatException e)
            {
                System.out.print("[IP_Complex]Invalid parameter for server port:\t");
                System.out.println(args[1]);
                System.out.print("\t`");
                System.err.println(e);
            }
            if(args.length < 3) { server(srcPort,null); return; }
            server(srcPort,args[2]);
        }
        else if("client".equals(args[0].toLowerCase())) 
        {
            if(args.length < 3) { print_usage(); return; }
            String targetAddr = args[1]; 
            if(args.length < 4){ client(targetAddr, 8800, args[2]); return;}
            int targetPort;
            try{ targetPort = Integer.parseInt(args[2]); client(targetAddr, targetPort, args[3]); return;}
            catch(NumberFormatException e)
            {
                System.out.print("[IP_Complex]Invalid parameter for client:\t");
                System.out.println(args[1]);
                System.out.print("\t`");
                System.err.println(e);
                return;
            }
        }
        else if("--help".equals(args[0].toLowerCase()))
        {
            print_usage();
        }
        else
        {
            print_usage();
        }
        //CPLX_NET cplxnet = new CPLX_NET();
        //CPLX_IO  cplx_io  = new CPLX_IO();
        //CPLX_NET  cplx_net  = new CPLX_NET();
        //cplx_io.CPLX_Load_File("filename");
        
        
        
        //cplxnet.CPLX_Start_Listening(1024);
        //cplxnet.CPLX_Establish_Connection("0.0.0.0",1024);
        
    }
}