import CPLX_Utils.*;
public class IP_Complex
{
    
    public static void main(String[] args) {

        CPLX_NET cplxnet= new CPLX_NET();
        //cplxnet.CPLX_Start_Listening(1024);
        cplxnet.CPLX_Establish_Connection("0.0.0.0",1024);
        
    }
}