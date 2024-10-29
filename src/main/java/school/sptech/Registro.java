package school.sptech;

import java.time.LocalDateTime;

public class Registro {
    private String nomeCurso;
    private String nomeArea;
    private Integer anoTurma;
    private Integer qtdIngressantes;
    private Integer qtdAlunosPermanencia;

    public Registro() {

    }

    public Registro(String nomeCurso, String nomeArea, Integer anoTurma,
                    Integer qtdIngressantes, Integer qtdAlunosPermanencia) {
        this.nomeCurso = nomeCurso;
        this.nomeArea = nomeArea;
        this.anoTurma = anoTurma;
        this.qtdIngressantes = qtdIngressantes;
        this.qtdAlunosPermanencia = qtdAlunosPermanencia;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNomeArea() {
        return nomeArea;
    }

    public void setNomeArea(String nomeArea) {
        this.nomeArea = nomeArea;
    }

    public Integer getAnoTurma() {
        return anoTurma;
    }

    public void setAnoTurma(Integer anoTurma) {
        this.anoTurma = anoTurma;
    }

    public Integer getQtdIngressantes() {
        return qtdIngressantes;
    }

    public void setQtdIngressantes(Integer qtdIngressantes) {
        this.qtdIngressantes = qtdIngressantes;
    }

    public Integer getQtdAlunosPermanencia() {
        return qtdAlunosPermanencia;
    }

    public void setQtdAlunosPermanencia(Integer qtdAlunosPermanencia) {
        this.qtdAlunosPermanencia = qtdAlunosPermanencia;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "nomeCurso='" + nomeCurso + '\'' +
                ", nomeArea='" + nomeArea + '\'' +
                ", anoTurma=" + anoTurma +
                ", qtdIngressantes=" + qtdIngressantes +
                ", qtdAlunosPermanencia=" + qtdAlunosPermanencia +
                '}';
    }
}
