package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

import es.caib.archium.csgd.apirest.constantes.Aspects;
import es.caib.archium.csgd.apirest.constantes.TiposObjetoSGD;

import java.util.List;
import java.util.Set;

public class Node {

    /**
     * Nombre del Nodo
     */
    protected String name;
    /**
     * Tipo de nodo
     */
    protected TiposObjetoSGD type;
    /**
     * Identificador, tipo UID, del nodo de alfresco
     */
    protected String id;

    /**
     * Lista de metadatos o propiedades del nodo
     */
    protected Set<Metadata> metadataCollection;

    /**
     * Lista de aspectos del nodo
     */
    protected List<Aspects> aspects;

    public Set<Metadata> getMetadataCollection() {
        return metadataCollection;
    }

    public void setMetadataCollection(Set<Metadata> metadataCollection) {
        this.metadataCollection = metadataCollection;
    }

    public List<Aspects> getAspects() {
        return aspects;
    }

    public void setAspects(List<Aspects> aspects) {
        this.aspects = aspects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TiposObjetoSGD getType() {
        return type;
    }

    public void setType(TiposObjetoSGD type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", id='" + id + '\'' +
                ", metadataCollection=" + metadataCollection +
                ", aspects=" + aspects +
                '}';
    }
}
