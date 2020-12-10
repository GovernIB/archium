package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

import es.caib.archium.csgd.apirest.constantes.Aspects;
import es.caib.archium.csgd.apirest.constantes.TiposObjetoSGD;

import java.util.List;
import java.util.Set;

public class RootNode extends Node {

    /**
     * Tipo de entidad documental
     */
    private static final TiposObjetoSGD type = TiposObjetoSGD.CUADRO_CLASIFICACION;


    @Override
    public String toString() {
        return "RootNode{" +
                "metadataCollection=" + metadataCollection +
                ", aspects=" + aspects +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
