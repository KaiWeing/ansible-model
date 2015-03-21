package de.oc.ansibleplugin.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.oc.ansibleplugin.AnsibleVersion;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Created by Weingärtner on 21.03.2015.
 */
public class JsonConverter {

    private final Gson gson;

    public JsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AnsibleVersion.class, new AnsibleVersionTypeAdapter());
        gsonBuilder.registerTypeAdapter(List.class, new OmitEmptyCollectionTypeAdapter());
        this.gson = gsonBuilder.create();
    }

    public void write(Object object, Writer writer) throws IOException {
        gson.toJson(object, writer);
        writer.flush();
    }

    @SuppressWarnings("unchecked")
    public <T> T read(Reader reader, Class<T> type) {
        return gson.fromJson(reader, type);
    }

}
