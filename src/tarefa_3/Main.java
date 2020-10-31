package tarefa_3;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tarefa_2.Scraper;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        try {
//            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/aveia-nestle-flocos-integral-170g-nestle/p/fjej5hc4e7/me/avea/"); //produto disponivel
            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/nutren-active-baunilha-nestle-400g/p/ajhh98kgea/me/cmaa/"); //produto indisponivel
//            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/");

            System.out.println(scraper.getProdutoJsoup().toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }








    }
}
