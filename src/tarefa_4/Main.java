package tarefa_4;


import tarefa_2.Scraper;

import java.io.IOException;
import java.util.*;
public class Main {

    public static void main(String[] args) {

        List<String> linkProdutos = new ArrayList<>();
        Set<String> linkconheceidos = new HashSet<>();
        Queue<String> pesquisar = new LinkedList<>();
        Queue<String> pesquisar2 = new LinkedList<>();

        try {
            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/");
            List<String> linknovos = scraper.getLinkJsoup();
            for (String s :linknovos) {
                pesquisar.add(s);
            }

            for (String s : pesquisar) {
                System.out.println("pesquisando link:" +s);
                scraper.setUri(s);
                scraper.scan();
                linknovos = scraper.getLinkJsoup();
                for (String s1 : linknovos) {
                    System.out.println("link novo:"+s1);
                    if(linkconheceidos.add(s1)){
                        pesquisar2.add(s1);
                        try {
                            if(!s1.contains("|"))
                                if(new Scraper(s1).isProduto()){
                                    linkProdutos.add(s1);
                                    System.out.println("linkprodutos :"+linkProdutos.size());
                                }
                        } catch (Exception e) {
                            System.err.println(e);
                            break;
                        }
                    }
//https://www.magazineluiza.com.br/luizacred/?id=1&s_cid=prc|ncc|pagprod|home-servicos|ccr|1|0|-|-|it&pco=CDL9996|AV-55|p1-lui
                }

            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //List<String> linknovos = scraper.getLink();






//https://www.magazineluiza.com.br/
    }
    public static List<String> getLinksProdutos(String uri){
        List<String> linkProdutos = new ArrayList<>();
        try {
            Scraper scraper = new Scraper("https://"+uri);
            Set<String> links = scraper.getLink();
            for (String s : links) {
                scraper.setUri("https://"+s);
                scraper.scan();
                linkProdutos.add(s);
            }
            for (String s : linkProdutos) {
                System.out.println(s);
            }
            return linkProdutos;

        }
        catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }
}









//
//        HashMap<Integer , String> hashMap = new HashMap<>();
//
//        hashMap.put(1,"aa");
//        System.out.println(hashMap.size());
//        hashMap.put(2,"bb");
//        System.out.println(hashMap.size());
//        hashMap.put(1,"bb");
//        System.out.println(hashMap.size());
//        hashMap.put(2,"bb");
//        System.out.println(hashMap.size());
//
//        for (Map.Entry<Integer,String> s : hashMap.entrySet() ) {
//            System.out.println(s.getKey()+"\t"+s.getValue());
//        }



/*

        Pattern pattern = Pattern.compile("(www[.]magazineluiza[.]com[.]br.*?)\"");
        Matcher matcher = pattern.matcher(html);

        int count =0;
        while(matcher.find()) {
            count++;
            System.out.println(matcher.group(1));
        }

 */

// www[.]magazineluiza[.]com[.]br.*?)"\[\]
//(?:"header-product__title")>(.*?)<
// (?:"price-template__text")>(.*?)<
//(?:showcase-product__big-img.*?src=)"(.*?)"
//(?:"header-product__title.*?")>(.*?)<|(?:"price-template__text")>(.*?)<|(?:showcase-product__big-img.*?src=)"(.*?)"





