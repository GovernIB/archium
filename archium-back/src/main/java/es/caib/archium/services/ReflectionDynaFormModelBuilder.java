package es.caib.archium.services;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.ColumnModel;
import es.caib.archium.objects.FormControlBuilder;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.OrdreVisual;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.spi.CDI;
import javax.faces.convert.Converter;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class ReflectionDynaFormModelBuilder {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private Class<?> modelClass;
    private Comparator<PropertyDescriptor> propertySortComparator;
    private Predicate propertyFilterPredicate;
    private Set<String> excludedProperties;
    private final ResourceBundle messageBundle;

    private static Set<String> defaultExcludedProperties = new HashSet<String>(0);
    private Map<String, FormControlBuilder> customBuilders = new HashMap<String, FormControlBuilder>();
    public static Comparator<PropertyDescriptor> DEFAULT_PROPERTY_COMPARATOR = new Comparator<PropertyDescriptor>() {
        public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
            // Si una de les propietats està anotada amb OrdreVisual, ordenem per aquí.
            Integer ordre1 = null;
            Integer ordre2 = null;
            if (o1.getReadMethod().isAnnotationPresent(OrdreVisual.class)) {
                ordre1 = o1.getReadMethod().getAnnotation(OrdreVisual.class).ordre();
            }
            if (o2.getReadMethod().isAnnotationPresent(OrdreVisual.class)) {
                ordre2 = o2.getReadMethod().getAnnotation(OrdreVisual.class).ordre();
            }

            if (ordre1 != null && ordre2 != null) {
                return ordre1.compareTo(ordre2);
            } else if (ordre1 != null) {
                return -1;
            } else if (ordre2 != null) {
                return 1;
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        }
    };

    static {
        defaultExcludedProperties.add("class");
    }

    public ReflectionDynaFormModelBuilder(Class<?> modelClass, ResourceBundle messageBundle) {
        this.modelClass = modelClass;
        this.propertyFilterPredicate = PredicateUtils.truePredicate();
        this.propertySortComparator = DEFAULT_PROPERTY_COMPARATOR;
        this.excludedProperties = new HashSet<String>(0);
        this.messageBundle = messageBundle;
    }

    public ReflectionDynaFormModelBuilder setPropertyFilterPredicate(Predicate p) {
        this.propertyFilterPredicate = p;
        return this;
    }

    public ReflectionDynaFormModelBuilder setPropertySortComparator(Comparator<PropertyDescriptor> c) {
        this.propertySortComparator = c;
        return this;
    }

    public ReflectionDynaFormModelBuilder setExcludedProperties(Set<String> p) {
        this.excludedProperties = p;
        return this;
    }

    public ReflectionDynaFormModelBuilder putCustomBuilder(String name, FormControlBuilder builder) {
        this.customBuilders.put(name, builder);
        return this;
    }

    public ReflectionDynaFormModelBuilder putCustomBuilders(Map<String, FormControlBuilder> builders) {
        this.customBuilders.putAll(builders);
        return this;
    }

    public ReflectionDynaFormModelBuilder setExcludedProperties(String... p) {
        this.excludedProperties = new HashSet<String>(0);
        Collections.addAll(this.excludedProperties, p);
        return this;
    }

    public DynaFormModel build() {
        DynaFormModel formModel = new DynaFormModel();

        List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>(Arrays.asList(PropertyUtils.getPropertyDescriptors(modelClass)));

        CollectionUtils.filter(propertyDescriptors, PredicateUtils.andPredicate(propertyFilterPredicate, object -> {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) object;
            return
                    propertyDescriptor.getReadMethod() != null &&
                            propertyDescriptor.getWriteMethod() != null &&
                            !defaultExcludedProperties.contains(propertyDescriptor.getName()) &&
                            !excludedProperties.contains(propertyDescriptor.getName());
        }));

        propertyDescriptors.sort(propertySortComparator);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            DynaFormRow row = formModel.createRegularRow();

            if (customBuilders.containsKey(propertyDescriptor.getName())) {
                customBuilders.get(propertyDescriptor.getName()).populateRow(row);
            } else {
                //Default Row

                String header;
                if (messageBundle.containsKey(propertyDescriptor.getName())) {
                    header = messageBundle.getString(propertyDescriptor.getName());
                } else {
                    header = StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(propertyDescriptor.getName())," "));
                }
                DynaFormLabel label = row.addLabel(header);

                boolean manyToOne = false;
                for (Field field : modelClass.getDeclaredFields()) {
                    if (propertyDescriptor.getName().equals(field.getName()) && field.isAnnotationPresent(ManyToOne.class)) {
                        manyToOne = true;
                    }
                }

                boolean required = false;
                for (Field field : modelClass.getDeclaredFields()) {
                    if (propertyDescriptor.getName().equals(field.getName()) && field.isAnnotationPresent(NotNull.class)) {
                        required = true;
                    }
                }

                int size = 0;
                for (Field field : modelClass.getDeclaredFields()) {
                    if (propertyDescriptor.getName().equals(field.getName()) && field.isAnnotationPresent(Size.class)) {
                       size = field.getAnnotation(Size.class).max();
                    }
                }

                if (manyToOne) {
                    // Rellenar lista de valores con el método findAll...

                    Class<?> service;
                    Class<?> converter;
                    try {
                        log.debug("Cercant la property manytoone: " + propertyDescriptor.getPropertyType().getSimpleName());
                        service = Class.forName("es.caib.archium.ejb.service." + propertyDescriptor.getPropertyType().getSimpleName() + "Service");
                        converter = Class.forName("es.caib.archium.converters." + propertyDescriptor.getPropertyType().getSimpleName() + "Converter");
                        log.debug("Converter: " + converter.getName());
                        log.debug("Converter: " + converter.getTypeName());

                        DynaFormControl input = row.addControl(new ColumnModel(propertyDescriptor.getName(), (Converter)CDI.current().select(converter).get(), ((DAO) CDI.current().select(service).get()).findAll(), required), "manytoone");

                        label.setForControl(input);

                    } catch (ClassNotFoundException | I18NException e) {
                        e.printStackTrace();
                    }
                } else {
                    DynaFormControl input = row.addControl(new ColumnModel(propertyDescriptor.getName(), required), size >= 1000 ? "textarea" : propertyDescriptor.getPropertyType().getSimpleName().toLowerCase());
                    label.setForControl(input);
                }
            }
        }

        return formModel;
    }
}

