package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

import es.caib.archium.csgd.apirest.constantes.Aspects;
import es.caib.archium.csgd.apirest.constantes.TiposObjetoSGD;

import java.util.List;
import java.util.Map;

public class FunctionNode extends Node{

    /**
     * Tipo de entidad documental
     */
    private static final TiposObjetoSGD type = TiposObjetoSGD.FUNCION;


    @Override
    public String toString() {
        return "FunctionNode{" +
                "metadataCollection=" + metadataCollection +
                ", aspects=" + aspects +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
