
public class ContaPoupanca extends ContaBancaria
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private int Dia_de_rendimento;

    public ContaPoupanca(int dia, String cliente, int num_conta)
    {
        super(cliente, num_conta);
        Dia_de_rendimento = dia;
    }

    public void calcularNovoSaldo(double taxaR)
    {
       double new_sald = getSaldo();
       new_sald = new_sald + ( new_sald/taxaR);
       setSaldo(new_sald);
    }
    
    public String toString(){
        return "Nome do cliente: " + getCliente() + " Nº da conta: " + getNumConta() + " Saldo: " + getSaldo();
    }
}
