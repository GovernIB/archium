package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

public class RootId extends Id{


    public String getId() {
        return super.id;
    }

    public void setId(String id) {
        super.id = id;
    }

    public RootId() {
    }

    public RootId(String id){
        super(id);
    }

    @Override
    public String toString() {
        return "RootId{" +
                "id='" + id + '\'' +
                '}';
    }
}
