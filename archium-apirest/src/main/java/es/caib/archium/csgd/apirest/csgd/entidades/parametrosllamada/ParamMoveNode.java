package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;

public class ParamMoveNode {
    private String nodeId;
    private String targetParent;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getTargetParent() {
        return targetParent;
    }

    public void setTargetParent(String targetParent) {
        this.targetParent = targetParent;
    }

    @Override
    public String toString() {
        return "ParamMoveNode{" +
                "nodeId='" + nodeId + '\'' +
                ", targetParent='" + targetParent + '\'' +
                '}';
    }
}
