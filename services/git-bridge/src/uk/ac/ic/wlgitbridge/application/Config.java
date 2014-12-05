package uk.ac.ic.wlgitbridge.application;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import uk.ac.ic.wlgitbridge.application.exception.InvalidConfigFileException;
import uk.ac.ic.wlgitbridge.writelatex.api.request.base.JSONSource;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Winston on 05/12/14.
 */
public class Config implements JSONSource {

    private int port;
    private String rootGitDirectory;
    private String apiKey;

    public Config(String configFilePath) throws InvalidConfigFileException, IOException {
        try {
            fromJSON(new Gson().fromJson(new FileReader(configFilePath), JsonElement.class));
        } catch (JsonParseException e) {
            throw new IOException();
        }
    }

    @Override
    public void fromJSON(JsonElement json) {
        JsonObject configObject = json.getAsJsonObject();
        port = getElement(configObject, "port").getAsInt();
        rootGitDirectory = getElement(configObject, "rootGitDirectory").getAsString();
        apiKey = getElement(configObject, "apiKey").getAsString();
    }

    public int getPort() {
        return port;
    }

    public String getRootGitDirectory() {
        return rootGitDirectory;
    }

    public String getAPIKey() {
        return apiKey;
    }

    private JsonElement getElement(JsonObject configObject, String name) {
        JsonElement element = configObject.get(name);
        if (element == null) {
            throw new RuntimeException(new InvalidConfigFileException(name));
        }
        return element;
    }

}
