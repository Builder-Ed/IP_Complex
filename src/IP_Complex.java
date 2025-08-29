import CPLX_Utils.*;
public class IP_Complex
{
    public static void print_usage()
    {
        System.out.println("Usage: java -jar IP_Complex.jar [OPTION]...");
        System.out.println("\tserver <port>\t\t\tserver mode, listen on <port>");
        System.out.println("\tserver <port> <filepath>\tserver mode, load recorded client and listen on <port>");
        System.out.println("\tclient append <address> <clientID> <IP>\t\tclient mode, connect to <address> on port 8800 using <clientID> and add <IP> to this clientID. If <IP> is empty, the IP of the current device will we appended.");
        System.out.println("\tclient append <address> <port> <clientID> <IP>\t\tclient mode, connect to <address> on <port> using <clientID> and add <IP> to this clientID. If <IP> is empty, the IP of the current device will we appended.");
        System.out.println("\tclient fetch <address> <clientID>\t\tclient mode, connect to <address> on port 8800 using <clientID> and get existing IP(s) under this clientID");
        System.out.println("\tclient fetch <address> <port> <clientID>\t\tclient mode, connect to <address> on <port> using <clientID> and get existing IP(s) under this clientID");
        System.out.println("\tclient delete <address> <clientID> <IP>\t\tclient mode, connect to <address> on port 8800 using <clientID> and delete <IP> under this clientID");
        System.out.println("\tclient delete <address> <port> <clientID> <IP>\t\tclient mode, connect to <address> on <port> using <clientID> and delete <IP> under this clientID");
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

    public static void client(String targetAddr, int targetPort, String clientID, String clientAddr,String modeString)
    {
        CPLX_NET  cplx_net  = new CPLX_NET();
        cplx_net.CPLX_Establish_TCP_Connection(targetAddr, targetPort, clientID, clientAddr, modeString);
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
            if(args.length < 3) { server(srcPort,"clients.log"); return; }
            server(srcPort,args[2]);
        }
        else if("client".equals(args[0].toLowerCase())) 
        {
            if(args.length < 4) { print_usage(); return; }
            String targetAddr = args[2]; 
            int targetPort = 8800;

            

            switch (args[1].toLowerCase()) {
                case "fetch"://client fetch <address> <port> <clientID>
                    try{ targetPort = Integer.parseInt(args[3]); }
                    catch(NumberFormatException e)
                    {
                        System.out.print("[IP_Complex]Invalid parameter for client:\t");
                        System.out.println(args[1]);
                        System.out.print("\t`");
                        System.err.println(e);
                        targetPort = 8800;
                    }
                    if(args.length < 4 || args.length > 5) { print_usage(); return; }
                    client(targetAddr,targetPort,args[4],"NONE","FETCH"); 
                    return;
                case "delete"://client delete <address> <port> <clientID> <IP>
                    if(args.length < 5 || args.length > 6) { print_usage(); return; }
                    if (args.length == 5) {client(targetAddr,targetPort,args[3],args[4],"DELETE"); }
                    else if (args.length == 6) {client(targetAddr,targetPort,args[4],args[5],"DELETE"); }
                    return;
                case "append"://client append <address> <port> <clientID> <IP>
                    if(args[args.length-1].toLowerCase().equals("current")){args[args.length-1] = "CURRENT";}
                    if(args.length < 5 || args.length > 6) { print_usage(); return; }
                    if (args.length == 5) {client(targetAddr,targetPort,args[3],args[4],"APPEND"); }
                    else if (args.length == 6) {
                    try{ targetPort = Integer.parseInt(args[3]); }
                    catch(NumberFormatException e)
                    {
                        System.out.print("[IP_Complex]Invalid parameter for client:\t");
                        System.out.println(args[1]);
                        System.out.print("\t`");
                        System.err.println(e);
                        targetPort = 8800;
                    }
                        client(targetAddr,targetPort,args[4],args[5],"APPEND"); }
                    return;
                default:
                    print_usage();
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