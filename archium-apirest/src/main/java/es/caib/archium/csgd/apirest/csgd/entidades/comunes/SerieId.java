package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

public class SerieId extends Id{
    public String getId() {
        return super.id;
    }

    public void setId(String id) {
        super.id = id;
    }

    public SerieId() {
    }

    public SerieId(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return "SerieId{" +
                "id='" + id + '\'' +
                '}';
    }
}
