package es.caib.archium.api.externa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

    final ObjectMapper defaultObjectMapper;

    public JacksonObjectMapperProvider() {
        defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        defaultObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // https://stackoverflow.com/questions/7766791/serializing-enums-with-jackson
        defaultObjectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        defaultObjectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        //defaultObjectMapper.setDateFormat(new RFC3339DateFormat());
        //defaultObjectMapper.registerModule(new JodaModule());
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }
}
