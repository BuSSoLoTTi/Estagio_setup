package Coletor;

import Connect.Http;
import Connect.ProxyAuth;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class Scraper {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/601.6.17 (KHTML, like Gecko) Version/9.1.1 Safari/601.6.17";
    Document document;
    private String uri;
    private ProxyAuth proxyAuth;
    private String html;

    public Scraper() {
    }

    public Scraper(String uri) throws IOException, InterruptedException {
        this.uri = uri;
        scan();
    }

    public Scraper(String uri, ProxyAuth proxyAuth) throws IOException, InterruptedException {
        this.uri = uri;
        this.proxyAuth = proxyAuth;
        scan();
    }

    public void scan() throws IOException, InterruptedException {
        if (proxyAuth != null)
           this.html = Http.connect(this.uri, this.proxyAuth).body();
        else
            this.html = Http.connect(this.uri).body();

        this.document = Jsoup.parse(html);
//        this.proxyAuth!=null ? Http.connect(this.uri,this.proxyAuth):Http.connect(this.uri);
    }

    public boolean isProduto() {
        Matcher matcher = Pattern.compile("(?:\"header-product__title.*?\")>(.*?)<").matcher(html);
        return matcher.find();
    }

    public Produto getProduto() {
        Produto produto = new Produto();

        List<Pattern> patterns = new ArrayList<>();
        List<String> dados = new ArrayList<>();

        patterns.add(Pattern.compile("(?:\"header-product__title.*?\")>(.*?)<"));
        patterns.add(Pattern.compile("(?:\"price-template__text\")>(.*?)<"));
        patterns.add(Pattern.compile("(?:showcase-product__big-img.*?src=)\"(.*?)\""));

        Matcher matcher = patterns.get(0).matcher(this.html);
        matcher.find();
        if (matcher.group().contains("unavailable")) {
            Matcher img = Pattern.compile("(?:\"wrapper-product-unavailable.*?src=)\"(.*?)\"").matcher(this.html);
            img.find();
            return new Produto(matcher.group(1), false, img.group(1));
        } else {
            for (Pattern pattern : patterns) {
                matcher = pattern.matcher(this.html);
                matcher.find();
                dados.add(matcher.group(1));
            }

            return new Produto(dados.get(0), Float.parseFloat(dados.get(1).replaceAll(",", ".")), dados.get(2), true);

        }

    }

    public Set<String> getLink() {
        Set<String> strings = new HashSet<>();
        Pattern pattern = Pattern.compile("(www\\.magazineluiza\\.com\\.br\\/[\\/a-zA-Z-0-9]+)");
        Matcher matcher = pattern.matcher(this.html);

        int count = 0;
        while (matcher.find()) {
            count++;
            String aux = matcher.group(1);
            strings.add(aux);
        }
        return strings;
    }

    public boolean isProdutoJsoup() {
        return document.select("[class^=header-product__title]").size() > 0;
    }

    public Produto getProdutoJsoup() {
        if (document.select("[class^=wrapper-product-unavailable]").size() != 0) {
            return new Produto(document.select("[class^=header-product__title]").text(), false,
                    document.select("[class^=wrapper-product-unavailable]").select("img").attr("src"));
        } else {
            return new Produto(document.select("[class^=header-product__title]").text(),
                    Float.parseFloat(document.select("[class^=price-template__text]").text().replaceAll(",", ".")),
                    document.select("[class^=showcase-product__big]").attr("src"), true);
        }

    }

    public List<String> getLinkJsoup() {

        List<String> strings = new ArrayList<>();
        Elements elements = document.select("[href^=https://www.magazineluiza.com.br]");
        for (Element element : elements) {
            strings.add(element.attr("href"));
        }
        return strings;
    }

    public String getHtml() {
        return html;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}