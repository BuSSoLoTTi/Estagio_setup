package tarefa_2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String LINK = "http://httpbin.org/anything";
    private static final String PROXY_HOST = "104.227.146.81";
    private static final int PROXY_PORT = 12345;
    private static final String PROXY_USER = "proxylett";
    private static final String PROXY_PASS = "lettpublic321";

    public static void main(String[] args) {
        String html = "";
        try {
            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/aveia-nestle-flocos-integral-170g-nestle/p/fjej5hc4e7/me/avea/"); //produto disponivel
//            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/nutren-active-baunilha-nestle-400g/p/ajhh98kgea/me/cmaa/"); //produto indisponivel
//            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/");

            System.out.println(scraper.isProduto());
            Produto produto = scraper.getProduto();
            System.out.println(produto.toString());
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
