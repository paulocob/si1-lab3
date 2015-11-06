package models;

import play.data.validation.Constraints;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "ANUNCIANTE")
public class Anunciante {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "bairro")
    private String bairro;

    @ElementCollection
    @Column(name = "instrumentos")
    private List<String> instrumentos;

    @ElementCollection
    @Column(name = "gosta")
    private List<String> gosta;

    @ElementCollection
    @Column(name = "desgosta")
    private List<String> desgosta;

    @ElementCollection
    @Column(name = "contatos")
    private Map<String, String> contatos;
    
    @Column(name = "ocasional")
    private Boolean ocasional;

    public Anunciante() {}

    
    public Anunciante(String cidade, String bairro, String[] instrumentos, String[] gosta, String[] desgosta, String email, String facebook, Boolean ocasional) {
        

        this.ocasional = ocasional;
        this.cidade = cidade;
        this.bairro = bairro;
        
        this.setContatos(email, facebook);
        this.setInstrumentos(instrumentos);
        this.setGosta(gosta);
        this.setDesgosta(desgosta);
        
    }

    
    public Anunciante(String cidade, String bairro, List<String> instrumentos, List<String> gosta, List<String> desgosta, Map<String, String> contatos, Boolean ocasional) {
        
        this.ocasional = ocasional;
        this.cidade = cidade;
        this.bairro = bairro;
        
        this.setContatos(contatos.get("email"), contatos.get("fb"));
        this.setInstrumentos(instrumentos);
        this.setGosta(gosta);
        this.setDesgosta(desgosta);

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    private void setDesgosta(String[] desgosta) {
        if(desgosta != null)
            this.setDesgosta(Arrays.asList(desgosta));
    }

    private void setGosta(String[] gosta) {
        if(gosta != null)
            this.setGosta(Arrays.asList(gosta));
    }

    private void setInstrumentos(String[] instrumentos) {
        if(instrumentos != null)
            this.setInstrumentos(Arrays.asList(instrumentos));
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Map<String, String> getContatos() {
        return contatos;
    }

    public void setContatos(Map<String, String> contatos) {
        this.contatos = contatos;
    }

  
    public void setContatos(String email, String facebook) {
        if((facebook == null) && ((email == null) || !(new Constraints.EmailValidator().isValid(email))))
            throw new IllegalArgumentException("Pelo menos um contato deve ser fornecido");
        HashMap<String, String> tmp = new HashMap<String, String>();
        tmp.put("email", email);
        tmp.put("fb", facebook);
        this.setContatos(tmp);
    }

    public List<String> getDesgosta() {
        return desgosta;
    }

    
    public void setDesgosta(List<String> desgosta) {
        if((desgosta != null))
            this.desgosta = desgosta;
        else
            this.desgosta = new ArrayList<String>();
    }

    public List<String> getGosta() {
        return gosta;
    }

    public void setGosta(List<String> gosta) {
        if((gosta != null))
            this.gosta = gosta;
        else
            this.gosta = new ArrayList<String>();
    }

    public List<String> getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(List<String> instrumentos) {
        if((instrumentos == null) || (instrumentos.size() < 1))
            throw new IllegalArgumentException("O anunciante deve indicar ao menos um instrumento");
        this.instrumentos = instrumentos;
    }

    public Boolean getOcasional() {
        return ocasional;
    }

    public void setOcasional(Boolean ocasional) {
        this.ocasional = ocasional;
    }

    

}

