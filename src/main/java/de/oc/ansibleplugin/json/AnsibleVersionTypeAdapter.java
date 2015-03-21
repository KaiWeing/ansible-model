package de.oc.ansibleplugin.json;

import com.google.gson.*;
import de.oc.ansibleplugin.model.AnsibleVersion;

import java.lang.reflect.Type;

/**
 * Created by Weingärtner on 21.03.2015.
 */
public class AnsibleVersionTypeAdapter implements JsonSerializer<AnsibleVersion>, JsonDeserializer<AnsibleVersion> {
    @Override
    public JsonElement serialize(AnsibleVersion ansibleVersion, Type type, JsonSerializationContext jsonSerializationContext) {
        if (ansibleVersion == AnsibleVersion.DEFAULT_VERSION) {
            return null;
        }
        return new JsonPrimitive(ansibleVersion.toString());
    }

    @Override
    public AnsibleVersion deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return AnsibleVersion.valueOf(jsonElement.getAsJsonPrimitive().getAsString());
    }
}
