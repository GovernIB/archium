package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

public class FunctionId extends Id{


    public String getId() {
        return super.id;
    }

    public void setId(String id) {
        super.id = id;
    }

    public FunctionId() {
    }

    public FunctionId(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return "FunctionId{" +
                "id='" + id + '\'' +
                '}';
    }
}
