package org.battleship;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public abstract class AbstractSettings implements Path {
    protected String filePath = "";

    public static final String DEFAULT_FILE_PATH = System.getProperty("user.home") + "/battleship_server_settings.json";

    public void read(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.readerForUpdating(this).readValue(Paths.get(filePath).toFile());
        this.filePath = filePath;
    }

    public void write(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Path file = Paths.get(filePath);
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
        mapper.writeValue(file.toFile(), this);
        this.filePath = filePath;
    }

    public void reload(String filePath) {
        try {
            this.read(filePath);
        } catch (IOException e1) {
            try {
                this.write(filePath);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public void save() {
        try {
            write(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}