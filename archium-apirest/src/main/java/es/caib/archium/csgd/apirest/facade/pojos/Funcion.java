package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.constantes.Aspects;
import es.caib.archium.csgd.apirest.constantes.Estado;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.FunctionNode;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.Node;
import es.caib.archium.csgd.apirest.utils.Constantes;

public class Funcion extends Nodo{

    private Estado estado;
    private Boolean isFuncionPadre;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Boolean getFuncionPadre() {
        return isFuncionPadre;
    }

    public void setFuncionPadre(Boolean funcionPadre) {
        isFuncionPadre = funcionPadre;
    }

    @Override
    public void converter(Node nodo) {
        if(nodo!=null) {
            FunctionNode dto = (FunctionNode) nodo;
            this.codigo = dto.getName();
            this.nodeId = dto.getId();
            this.estado = dto.getAspects().stream().anyMatch(x -> x.equals(Aspects.OBSOLETO)) == true ? Estado.OBSOLET : null;
            Object aux = getProperty(dto.getMetadataCollection(), Constantes.FUNCION_PADRE_QNAME);
            this.isFuncionPadre = aux == null ? null : (Boolean) aux;
        }
    }
}
