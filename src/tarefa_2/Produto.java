package tarefa_2;

public class Produto {
    private String nome;
    private float preco;
    private boolean disponivel;
    private String img;
    private String link;

    public Produto() {
    }

    public Produto(String nome, boolean disponivel, String img) {
        this.nome = nome;
        this.disponivel = disponivel;
        this.img = img;
    }

    public Produto(String nome, float preco, String img, boolean disponivel) {
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
        this.img = img;
    }

    public Produto(String nome, float preco, String img, boolean disponivel,String link) {
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
        this.img = img;
        this.link = link;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }


    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", disponivel=" + disponivel +
                ", img='" + img + '\'' +
                '}';
    }
}
