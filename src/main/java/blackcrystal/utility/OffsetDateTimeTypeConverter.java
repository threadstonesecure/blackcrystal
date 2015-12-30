package blackcrystal.utility;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;


public class OffsetDateTimeTypeConverter implements JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {

    @Override
    public JsonElement serialize(OffsetDateTime src, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public OffsetDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        return OffsetDateTime.parse(json.getAsString());
    }
}
