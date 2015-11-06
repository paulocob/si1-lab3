package models;

import javax.persistence.*;

@Entity
@Table(name = "CONVERSA")
public class Conversa {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "pergunta")
    private String pergunta;

    @Column(name = "resposta")
    private String resposta;



    public Conversa(String pergunta) {
        this.pergunta = pergunta;
        this.resposta = new String();
    }
    
    public Conversa() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public void adicionarResposta(String resposta) {
        this.setResposta(resposta);
    }


    

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    
}
