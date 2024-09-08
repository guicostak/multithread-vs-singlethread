import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    // Variáveis atômicas para contagem e soma
    private static AtomicLong contador = new AtomicLong(0);
    private static AtomicLong soma = new AtomicLong(0);

    public static void main(String[] args) throws Exception {
        var inicioExecucao = System.currentTimeMillis();

        // Obter o número máximo de threads (núcleos disponíveis)
        int numThreads = Runtime.getRuntime().availableProcessors();
        long range = 1000000L / numThreads;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<List<Long>>> futuros = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            final long inicio = i * range;
            final long fim = (i == numThreads - 1) ? 1000000L : (i + 1) * range;

            Future<List<Long>> futuro = executor.submit(() -> computarMultiplosDeTres(inicio, fim));
            futuros.add(futuro);
        }

        executor.shutdown();

        List<Long> todosMultiplos = new ArrayList<>();

        for (Future<List<Long>> futuro : futuros) {
            List<Long> multiplos = futuro.get();
            todosMultiplos.addAll(multiplos);
        }

        todosMultiplos.sort(Long::compareTo);

        for (Long multiplo : todosMultiplos) {
            System.out.println(multiplo);
        }

        System.out.println("###############");
        System.out.println("Quantidade de números: " + contador.get());
        System.out.println("Somatório dos valores: " + soma.get());
        System.out.println("###############");
        var finalExecucao = System.currentTimeMillis();
        System.out.println("Tempo de execução: " + (finalExecucao - inicioExecucao) + " ms");
    }

    // Função que calcula os múltiplos de 3 no intervalo fornecido
    public static List<Long> computarMultiplosDeTres(long inicio, long fim) {
        List<Long> multiplos = new ArrayList<>();
        for (long i = inicio; i <= fim; i++) {
            if (i % 3 == 0) {
                multiplos.add(i);

                contador.incrementAndGet();
                soma.addAndGet(i);
            }
        }
        return multiplos;
    }
}
