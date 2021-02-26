package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.Metadata;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.Node;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Nodo {

    protected String codigo;
    protected String nodeId;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public abstract void converter(Node nodo);

    protected Object getProperty(Set<Metadata> metadataCollection, String property){
        if(metadataCollection!=null && !metadataCollection.isEmpty()){
            List<Object> result = metadataCollection.stream().filter(x -> x.getQname().equals(property)).collect(Collectors.toList());
            return (result == null || result.isEmpty()) ? null : ((Metadata)result.get(0)).getValue();
        }
        return null;
    }
}
