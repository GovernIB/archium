package es.caib.archium.objects;

import javax.faces.convert.Converter;
import java.io.Serializable;
import java.util.List;

public class ColumnModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String property;

    private boolean required;

    private String header;
    private String type;
    private Converter<?> converter;
    private List<Class<?>> values;

    public ColumnModel() {
    }

    public ColumnModel(String property, boolean required) {
        this.property = property;
        this.required = required;
    }

    public ColumnModel(String property, Converter<?> converter, List<Class<?>> values, boolean required) {
        this.property = property;
        this.converter = converter;
        this.values = values;
        this.required = required;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Class<?>> getValues() {
        return values;
    }

    public Converter<?> getConverter() {
        return converter;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}