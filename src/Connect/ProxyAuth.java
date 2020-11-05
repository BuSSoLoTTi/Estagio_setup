package Connect;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class ProxyAuth extends Authenticator {
    private String hostname;
    private int port;
    private String user;
    private String password;

    public ProxyAuth(String hostname, int port, String user, String password) {
        this.hostname = hostname;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public ProxyAuth(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        System.err.println("tentando conctar");
        return new PasswordAuthentication(this.user, this.password.toCharArray());
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

}
