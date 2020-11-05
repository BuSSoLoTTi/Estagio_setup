package Aplicacao;


import Coletor.Scraper;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Main {


    public static void main(String[] args) {
        List<String> linkProdutos = new CopyOnWriteArrayList<>();
        Set<String> linkconheceidos = ConcurrentHashMap.newKeySet();
        ExecutorService executor = Executors.newFixedThreadPool(50);
        Scraper scraper = null;
        try {
            scraper = new Scraper("https://www.magazineluiza.com.br/");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        List<String> linknovos = scraper.getLinkJsoup();
        List<String> pesquisar = new CopyOnWriteArrayList<>(linknovos);

        System.out.println(pesquisar.size());

        while (linkProdutos.size() < 100) {
            for (int i = 0; i < 5; i++) {
                executor.execute(() -> getLinksNew(pesquisar.remove(0), linkconheceidos, pesquisar, linkProdutos));
            }
            try {
                ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executor;
                while (poolExecutor.getQueue().size()>1){
                }
                System.out.printf("nova rodada de tarefas \n" +
                        "linkProdutos: %d\n" +
                        "pesquisar: %d \n",linkProdutos.size(),pesquisar.size());
                Thread.sleep(2000);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdownNow();
        while (!executor.isTerminated()) {
        }
        System.out.println(pesquisar.size());
    }

    public static void getLinksNew(String uri, Set<String> conecidos, List<String> pesquisar, List<String> produtos) {
        try {
            if (!uri.contains("|")) {
                Scraper scraper = new Scraper(uri);
                List<String> newlinks = scraper.getLinkJsoup();
                for (String newLink : newlinks) {
                    if (!newLink.contains("|"))
                        if (!conecidos.add(newLink)) {
                            System.out.println("thread: " + Thread.currentThread().getName() + " " + "link novo:" + newLink);
                            pesquisar.add(newLink);
                            if (new Scraper(newLink).isProdutoJsoup()) {
                                produtos.add(newLink);
                                System.out.println("linkprodutos :" + produtos.size());
                            }
                        }
                }
            }

        } catch (IOException | InterruptedException e) {
            System.err.printf("Error: %s com a uri: %s\n", e, uri);
        }
    }

    public static void getLinks(String s, Set<String> linkconheceidos, List<String> pesquisar, List<String> linkProdutos) throws IOException, InterruptedException {

        Scraper scraper = new Scraper(s);
        System.out.println("pesquisando link:" + s);


        for (String s1 : scraper.getLinkJsoup()) {
//            System.out.println("thread: " + Thread.currentThread().getName() + " " + "link novo:" + s1);
            if (!s1.contains("|")) {
                if (linkconheceidos.add(s1)) {
                    pesquisar.add(s1);
                    try {
                        if (new Scraper(s1).isProduto()) {
                            linkProdutos.add(s1);
                            System.out.println("linkprodutos :" + linkProdutos.size());
                        }
                    } catch (IOException | InterruptedException e) {
                        System.err.printf("Error getlink: %s com o link %s\n", e, s1);
                    }
                }
            }
        }
    }
}
