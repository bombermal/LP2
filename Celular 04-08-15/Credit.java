
/**
 * Escreva a descrição da classe Credit aqui.
 * 
 * @Ivan 
 * @0.1
 */
public class Credit
{
 private int saldo;
 
    public Credit(){
        saldo = 1000;
    }
    
    public Credit(int x){
             if ( x > 0){
             saldo = x;
        } else {
            System.out.println("Adicione um valor positivo.");
            saldo = 0;
        }
    }
    
    public int getSaldo(){
        return saldo;
    }
    
    public void setSaldo(int x){
         if ( x > 0){
             saldo = x;
        } else {
            System.out.println("Adicione um valor positivo.");
        }
    }
    
    public void topUp(int x){
         if ( x > 0){
             saldo += x;
        } else {
            System.out.println("Adicione um valor positivo.");
        }
    }
   
     public void topDown(int x){
        if ( saldo-x >= 0 ){
            if ( x > 0){
             saldo -= x;
            } else {
                System.out.println("Adicione um valor positivo.");
            }
        } else{
        System.out.printf("valor não permitido, seu saldo é: R$ %d \n", saldo);
    }
    }
    
    public void fazLigacao(){
        if ( saldo-45>=0 ){
            saldo -=45;
        } else {
            System.out.println("Saldo insuficiente para fazer chamada");
        }
    }
 
    public void mandaSms(){
        if ( saldo-35>=0 ){
            saldo -=45;
        } else {
            System.out.println("Saldo insuficiente para mensagem");
        }
    }
    
}
