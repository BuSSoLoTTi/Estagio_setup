package Aplicacao;


import Coletor.Scraper;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Main {


    public static void main(String[] args) {

        List<String> linkProdutos = new CopyOnWriteArrayList<>();
        Set<String> linkconheceidos = ConcurrentHashMap.newKeySet();
        List<String> pesquisar = new CopyOnWriteArrayList<>();


        try {
            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/");
            List<String> linknovos = scraper.getLinkJsoup();
            pesquisar.addAll(linknovos);

            getLinks(pesquisar.remove(0), linkconheceidos, pesquisar, linkProdutos);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void getLinks(String s, Set<String> linkconheceidos, List<String> pesquisar, List<String> linkProdutos) throws IOException, InterruptedException {

        Scraper scraper = new Scraper(s);
        System.out.println("pesquisando link:" + s);


        for (String s1 : scraper.getLinkJsoup()) {
            System.out.println("thread: " + Thread.currentThread().getName() + " " + "link novo:" + s1);
            if (!s1.contains("|")) {
                if (linkconheceidos.add(s1)) {
                    pesquisar.add(s1);
                    try {
                        if (new Scraper(s1).isProduto()) {
                            linkProdutos.add(s1);
                            System.out.println("linkprodutos :" + linkProdutos.size());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }
    }
}
