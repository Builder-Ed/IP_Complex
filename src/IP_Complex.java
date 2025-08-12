import CPLX_Utils.*;
public class IP_Complex
{
    public static void print_usage()
    {
        System.out.println("Usage: java -jar IP_Complex.jar [OPTION]...");
        System.out.println("\tserver <port>\t\t\tserver mode, listen on <port>");
        System.out.println("\tserver <port> <filepath>\tserver mode, load recorded client and listen on <port>");
        System.out.println("\tclient <address>\t\tclient mode, connect to <address> on port 8800");
        System.out.println("\tclient <address> <port>\t\tclient mode, connect to <address> on <port>");
    }
    public static void server(int srcPort,String filename)
    {
        CPLX_NET  cplx_net = new CPLX_NET();
        CPLX_IO   cplx_io  = new CPLX_IO();
        if(filename != null) {cplx_io.CPLX_Load_File(filename);}
        else{ filename = "clients.log"; }
        if(srcPort == 0) { cplx_net.CPLX_Start_Listening_TCP(8800,filename); }
        else { cplx_net.CPLX_Start_Listening_TCP(srcPort,filename); }
    }

    public static void client(String targetAddr, int targetPort)
    {
        CPLX_NET  cplx_net  = new CPLX_NET();
        cplx_net.CPLX_Establish_TCP_Connection(targetAddr, targetPort);
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
            if(args.length < 2) { return; }
            String targetAddr = args[1];
            if(args.length < 3){client(targetAddr,8800);}
            int targetPort;
            try{ targetPort = Integer.parseInt(args[2]); client(targetAddr, targetPort); return;}
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