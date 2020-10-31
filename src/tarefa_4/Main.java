package tarefa_4;


import tarefa_2.Scraper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) {
// MAX
        String[] names = {"maria","joao","eduardo"};
        List<String> linkProdutos = new ArrayList<>();
        Set<String> linkconheceidos = new HashSet<>();
        Queue<String> pesquisar = new LinkedList<>();
        Queue<String> pesquisar2 = new LinkedList<>();
        List<ThreadProduct> threadProductList = new ArrayList<>();




        /*** Eduardo esteve aqui ***/


        /** vou fazer comida */


        try {
            Scraper scraper = new Scraper("https://www.magazineluiza.com.br/");
            List<String> linknovos = scraper.getLinkJsoup();
            for (String s : linknovos) {
                pesquisar.add(s);
            }

            while (ThreadProduct.disponivel()){
                threadProductList.add( new ThreadProduct("edu") {
                    @Override
                    public void run() {
                        try {
                            getLinks(pesquisar.remove(), linkconheceidos, pesquisar2, linkProdutos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
            threadProductList.parallelStream()
                    .map(thread -> {
                        thread.setName(String.valueOf(new Random().nextInt()));
                        thread.run();
                        return thread;
                    }).collect(Collectors.toList());

        pesquisar.addAll(pesquisar2);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //List<String> linknovos = scraper.getLink();


//https://www.magazineluiza.com.br/
    }

    public static void getLinks(String s, Set<String> linkconheceidos, Queue<String> pesquisar2, List<String> linkProdutos) throws IOException, InterruptedException {

        Scraper scraper = new Scraper(s);
        System.out.println("pesquisando link:" + s);

        final var newLinks = scraper.getLinkJsoup();
        for (String s1 : newLinks) {
            System.out.println("thread: " +  Thread.activeCount() +" "+"link novo:" + s1);
            if (linkconheceidos.add(s1)) {
                pesquisar2.add(s1);
                try {
                    if (!s1.contains("|"))
                        if (new Scraper(s1).isProduto()) {
                            linkProdutos.add(s1);
                            System.out.println("linkprodutos :" + linkProdutos.size());
                        }
                } catch (Exception e) {
                    System.err.println(e);
                    break;
                }
            }
        }
    }


//    public static List<String> getLinksProdutos(String uri){
//        List<String> linkProdutos = new ArrayList<>();
//        try {
//            Scraper scraper = new Scraper("https://"+uri);
//            Set<String> links = scraper.getLink();
//            for (String s : links) {
//                scraper.setUri("https://"+s);
//                scraper.scan();
//                linkProdutos.add(s);
//            }
//            for (String s : linkProdutos) {
//                System.out.println(s);
//            }
//            return linkProdutos;
//
//        }
//        catch (Exception e){
//            e.getStackTrace();
//            return null;
//        }
//    }
//
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





