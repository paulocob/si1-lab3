package models;

import java.sql.Timestamp;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "ANUNCIO")
public class Anuncio {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "titulo")
    private String titulo;

    @OneToOne
    @JoinColumn(name = "criador")
    private Anunciante criador;

    @Column(name = "data")
    private Timestamp data;

    @Column(name = "codigo")
    private String codigo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "conversas")
    private List<Conversa> conversas;

    public Anuncio() {}

    public Anuncio(Anunciante criador, String codigo, Timestamp data, String descricao, String titulo) {
        this.criador = criador;
        this.codigo = codigo;
        this.titulo = titulo;
        this.conversas = new ArrayList<Conversa>();
        this.data = data;
        this.descricao = descricao;
       
    }


    public Map<String, String> getContatos() {
        return this.getAnunciante().getContatos();
    }
    public Anunciante getAnunciante() {
        return criador;
    }

    public void setAnunciante(Anunciante anunciante) {
        this.criador = anunciante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }
    public int getId() {
        return this.id;
    }

    public List<Conversa> getConversas() {
        return conversas;
    }

    public void setConversas(List<Conversa> conversas) {
        this.conversas = conversas;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    
    public void adicionarPergunta(String novaPergunta) {
        Conversa novaConversa = new Conversa(novaPergunta);
        this.getConversas().add(novaConversa);

    }

    public void adicionarResposta(int id, String resposta, String codigo) {
        if(!this.acertouCodigo(codigo))
            throw new IllegalArgumentException("Código inválido.");
        this.getConversaByIndex(id).adicionarResposta(resposta);
    }

    public void apagarPergunta(int index, String codigo) {
        if(this.acertouCodigo(codigo))
            this.getConversas().remove(index);
    }

    private Conversa getConversaByIndex(int indexPergunta) {
        return this.getConversas().get(indexPergunta);
    }

    private boolean acertouCodigo(String codigo) {
        return codigo.equals(this.codigo);
    }
   

    

   

}
