package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

import es.caib.archium.csgd.apirest.constantes.Aspects;
import es.caib.archium.csgd.apirest.constantes.TiposObjetoSGD;

import java.util.List;

public class SerieNode extends Node{

    /**
     * Tipo de entidad documental
     */
    private static final TiposObjetoSGD type = TiposObjetoSGD.SERIE_DOCUMENTAL;

    /**
     * Documento XML con los datos de la serie
     */
    private Content binaryContent;


    public Content getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(Content binaryContent) {
        this.binaryContent = binaryContent;
    }

    @Override
    public String toString() {
        return "SerieNode{" +
                "metadataCollection=" + metadataCollection +
                ", aspects=" + aspects +
                ", binaryContent=" + binaryContent +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
