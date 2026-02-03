package es.caib.archium.services;

import es.caib.archium.objects.ColumnModel;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

import static es.caib.archium.services.ReflectionDynaFormModelBuilder.DEFAULT_PROPERTY_COMPARATOR;

public class ReflectionColumnModelBuilder {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private Class modelClass;

    private Comparator<PropertyDescriptor> propertySortComparator;
    private Predicate propertyFilterPredicate;
    private Set<String> excludedProperties;

    private ResourceBundle messageBundle;

    private static Set<String> defaultExcludedProperties = new HashSet<>(0);

    static {
        defaultExcludedProperties.add("class");
    }

    public ReflectionColumnModelBuilder(Class modelClass, ResourceBundle messageBundle) {

        this.modelClass = modelClass;
        this.messageBundle = messageBundle;

        this.propertyFilterPredicate = PredicateUtils.truePredicate();
        this.propertySortComparator = DEFAULT_PROPERTY_COMPARATOR;
        this.excludedProperties = new HashSet<>(0);
    }

    public ReflectionColumnModelBuilder setPropertyFilterPredicate(Predicate p){

        this.propertyFilterPredicate = p;
        return this;

    }

    public ReflectionColumnModelBuilder setPropertySortComparator(Comparator<PropertyDescriptor> c){

        this.propertySortComparator = c;
        return this;

    }

    public ReflectionColumnModelBuilder setExcludedProperties(Set<String> p){
        this.excludedProperties = p;
        return this;
    }

    public ReflectionColumnModelBuilder setExcludedProperties(String...p){
        this.excludedProperties = new HashSet<String>(0);
        Collections.addAll(this.excludedProperties, p);
        return this;
    }

    public List<ColumnModel> build() {

        List<ColumnModel> columns = new ArrayList<>(0);

        List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>(Arrays.asList(PropertyUtils.getPropertyDescriptors(modelClass)));

        CollectionUtils.filter(propertyDescriptors, PredicateUtils.andPredicate(propertyFilterPredicate, new Predicate() {

            public boolean evaluate(Object object) {

                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) object;

                return

                        propertyDescriptor.getReadMethod() != null &&

                                !defaultExcludedProperties.contains(propertyDescriptor.getName()) &&

                                !excludedProperties.contains(propertyDescriptor.getName());

            }

        }));


        //Collections.sort(propertyDescriptors, propertySortComparator);
        propertyDescriptors.sort(propertySortComparator);

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            ColumnModel columnDescriptor = new ColumnModel();

            columnDescriptor.setProperty(propertyDescriptor.getName());

            boolean required = false;
            for (Field field : propertyDescriptor.getPropertyType().getDeclaredFields()) {
                if (propertyDescriptor.getName().equals(field.getName()) && field.isAnnotationPresent(NotNull.class)) {
                    required = true;
                }
            }
            columnDescriptor.setRequired (required);

            // S'intenta posar message i18n. Del contrari, el nom de la propietat capitalitzat...
            String header;
            if (messageBundle.containsKey(propertyDescriptor.getName())) {
                header = messageBundle.getString(propertyDescriptor.getName());
            } else {
                header = StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(propertyDescriptor.getName())," "));
            }
            columnDescriptor.setHeader(header);

            log.debug("Columna " + propertyDescriptor.getName() + ". tipus = " + propertyDescriptor.getPropertyType().getName() + ". SimpleTipus = " + propertyDescriptor.getPropertyType().getSimpleName());
            columnDescriptor.setType(propertyDescriptor.getPropertyType().getName());

            columns.add(columnDescriptor);

        }

        return columns;

    }

}