package tarefa_1;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;


public class Main {

    private static final String LINK ="http://httpbin.org/anything";
    private static final String USER_AGENT ="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/601.6.17 (KHTML, like Gecko) Version/9.1.1 Safari/601.6.17";
    private static final String PROXY_USER ="proxylett";
    private static final String PROXY_PASS ="lettpublic321";

    public static void main(String[] args) throws Exception {

        Set<String> links = new HashSet<>();

        links.add("saas");
        System.out.println(links.iterator());

        try {
            GETBody(LINK);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    public static void GETBody(String uri) throws Exception {

        Authenticator a = new ProxyAuth(PROXY_USER,PROXY_PASS);

        HttpClient client = HttpClient.newBuilder()
                .proxy(ProxySelector.of(new InetSocketAddress("104.227.146.81",12345)))
                .authenticator(a)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("User-Agent",USER_AGENT)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}

class ProxyAuth extends Authenticator{
    private String user;
    private String password;

    public ProxyAuth(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.user, this.password.toCharArray());
    }
}
