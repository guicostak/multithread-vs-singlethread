public class Main {
    public static void main(String[] args) {
        var inicioExecucao = System.currentTimeMillis();
        int contador = 0;
        long soma = 0;

        for (int i = 0; i <= 1000000; i++) {
            if (i % 3 == 0) {
                System.out.println(i);
                contador++;
                soma += i;
            }
        }

        System.out.println("###############");
        System.out.println("Quantidade de números: " + contador);
        System.out.println("Somatório dos valores: " + soma);
        System.out.println("###############");
        var finalExecucao = System.currentTimeMillis();
        System.out.println("Tempo de execução: " + (finalExecucao - inicioExecucao));
    }
}
