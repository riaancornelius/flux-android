package com.riaancornelius.flux.jira;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: riaan.cornelius
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MMM/yy h:mm a");

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return DATE_FORMAT.parse(jp.getText());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
