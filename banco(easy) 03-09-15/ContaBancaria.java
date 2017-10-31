
/**
 * Escreva a descrição da classe ContaBancaria aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ContaBancaria
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private String cliente;
    private int num_conta;
    private double saldo;

    /**
     * COnstrutor para objetos da classe ContaBancaria
     */
    public ContaBancaria(String nome, int num)
    {
        // inicializa variáveis de instância
       cliente = nome;
       num_conta = num;
       saldo = 0;
    }
    
    public void sacar(double valor)
    {
        if ( valor > 0 ){
            if ( (saldo - valor) >=0 ){
                saldo -= valor;
            }
        } else {
             System.out.println ("Digite um valor positivo > 0");
        }

    }
    
    public void depositar( double valor ){
          if ( valor > 0 ){
                saldo += valor;
        } else {
             System.out.println ("Digite um valor positivo > 0");
        }
    }
    
    public String toString(){
        return "Nome do cliente: " + cliente + " Nº da conta: " + num_conta + " Saldo: " + saldo;
    }
    
    public double getSaldo(){
        return saldo;
    }
    
    public String getCliente(){
        return cliente;
    }
    
    public int getNumConta(){
        return num_conta;
    }
    
    public void setSaldo(double valor){
        saldo = valor;
    }
}
