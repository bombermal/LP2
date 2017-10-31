
public class ContaEspecial extends ContaBancaria
{
    private double limite;

   
    public ContaEspecial(double lim, String cliente, int num_conta)
    {
        super(cliente, num_conta);
        limite = lim;
       
    }

    public void sacar(double val)
    {
        double saldoTemp = getSaldo();
        
        if ( val > 0 ) {
            if ( (saldoTemp - val)>= 0) {
                saldoTemp -= val;
            }
    }
    }
}
