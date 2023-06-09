package org.battleship.commons.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public abstract class AbstractSettings {
    protected String filePath = "";

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