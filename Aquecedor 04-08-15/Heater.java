
/**
 * Escreva a descrição da classe Heater aqui.
 * 
 * @Ivan (seu nome) 
 * @0.1 (número de versão ou data)
 */
public class Heater
{
    private int temperatura;
    private int min;
    private int max;
    private int increment;
    
    public Heater(int x, int y){
        temperatura = 15;
        min = x;
        max = y;
        increment = 5;
    }
    
    public void warmer(){
        if (temperatura+increment <= max ){
            temperatura+=increment;
    } else {
        System.out.println("Temperatura máxima atingida");
    }
}

    public void cooler(){
        if (temperatura-increment >= min ){
            temperatura-=increment;
    } else {
        System.out.println("Temperatura mínima atingida");
    }
}

    public int getTemperatura(){
        return temperatura;
    }
    
    public void setIncrement(int valor){
        if ( valor > 0 ){    
        increment = valor;
    } else {
        System.out.println("Utilize um valor positivo");
    }
}
}
