package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.constantes.Aspects;
import es.caib.archium.csgd.apirest.constantes.Estado;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.Node;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootNode;

public class CuadroClasificacion extends Nodo{

    private Estado estado;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public void converter(Node nodo) {
        if(nodo!=null) {
            RootNode dto = (RootNode) nodo;
            this.codigo = dto.getName();
            this.nodeId = dto.getId();
            this.estado = dto.getAspects().stream().anyMatch(x -> x.equals(Aspects.OBSOLETO)) == true ? Estado.OBSOLET : null;
        }
    }
}
