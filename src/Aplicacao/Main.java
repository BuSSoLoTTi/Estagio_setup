package Aplicacao;


import Coletor.Scraper;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) {
        List<String> linkProdutos = new CopyOnWriteArrayList<>();
        Set<String> linkconheceidos = ConcurrentHashMap.newKeySet();
        ExecutorService executor = Executors.newFixedThreadPool(8);
        Scraper scraper = null;
        try {
            scraper = new Scraper("https://www.magazineluiza.com.br/");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        List<String> linknovos = scraper.getLinkJsoup();
        List<String> pesquisar = new CopyOnWriteArrayList<>(linknovos);

        System.out.println(pesquisar.size());

        while (linkProdutos.size()<100) {
                executor.execute(() -> getLinksNew(pesquisar.remove(0), linkconheceidos, pesquisar, linkProdutos));


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
