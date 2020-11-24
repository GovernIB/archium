package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

public class Termino {

    private String valorPrimario;
    private Long termino;

    public String getValorPrimario() {
        return valorPrimario;
    }

    public void setValorPrimario(String valorPrimario) {
        this.valorPrimario = valorPrimario;
    }

    public Long getTermino() {
        return termino;
    }

    public void setTermino(Long termino) {
        this.termino = termino;
    }


    @Override
    public String toString() {
        return "Termino{" +
                "valorPrimario='" + valorPrimario + '\'' +
                ", termino=" + termino +
                '}';
    }
}
