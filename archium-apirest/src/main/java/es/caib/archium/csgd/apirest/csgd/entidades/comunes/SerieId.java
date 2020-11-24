package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

public class SerieId {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "SerieId{" +
                "id='" + id + '\'' +
                '}';
    }
}
