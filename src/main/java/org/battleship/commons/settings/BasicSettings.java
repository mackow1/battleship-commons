package org.battleship.commons.settings;

import org.battleship.commons.settings.AbstractSettings;

import java.io.IOException;
import java.util.Properties;

public class BasicSettings extends AbstractSettings {
    protected final Properties properties = new Properties();

    public BasicSettings() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("program.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getVersion() {
        return this.properties.getProperty("version");
    }
}
