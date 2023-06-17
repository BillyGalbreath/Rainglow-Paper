package net.pl3x.rainglow.configuration;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import net.pl3x.rainglow.Rainglow;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractConfig {
    private File file;
    private YamlConfiguration config;

    public AbstractConfig() {
    }

    public @NotNull YamlConfiguration getConfig() {
        return this.config;
    }

    protected void reload(@NotNull Path path, @NotNull Class<? extends @NotNull AbstractConfig> clazz) {
        Logger logger = Rainglow.getPlugin(Rainglow.class).getLogger();

        this.file = path.toFile();
        this.config = new YamlConfiguration();

        try {
            getConfig().load(this.file);
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException e) {
            logger.severe("Could not load " + path.getFileName() + ", please correct your syntax errors");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // load data from yaml
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            Key key = field.getDeclaredAnnotation(Key.class);
            Comment comment = field.getDeclaredAnnotation(Comment.class);
            if (key == null) {
                return;
            }
            try {
                Object obj = getClassObject();
                Object value = getValue(key.value(), field.get(obj));
                field.set(obj, value instanceof String str ? StringEscapeUtils.unescapeJava(str) : value);
                if (comment != null) {
                    setComment(key.value(), comment.value());
                }
            } catch (Throwable e) {
                logger.warning("Failed to load " + key.value() + " from " + path.getFileName().toString());
                e.printStackTrace();
            }
        });

        save();
    }

    protected void save() {
        // save yaml to disk
        try {
            getConfig().save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected @Nullable Object getClassObject() {
        return null;
    }

    protected @Nullable Object getValue(@NotNull String path, @Nullable Object def) {
        if (getConfig().get(path) == null) {
            set(path, def);
        }
        return get(path, def);
    }

    protected void setComment(@NotNull String path, @NotNull String comment) {
        getConfig().setComments(path, Arrays.stream(comment.split("\n")).toList());
    }

    protected @Nullable Object get(@NotNull String path, @Nullable Object def) {
        Object val = get(path);
        return val == null ? def : val;
    }

    protected @Nullable Object get(@NotNull String path) {
        Object value = getConfig().get(path);
        if (!(value instanceof ConfigurationSection section)) {
            return value;
        }
        Map<String, Object> map = new LinkedHashMap<>();
        for (String key : section.getKeys(false)) {
            String rawValue = section.getString(key);
            if (rawValue == null) {
                continue;
            }
            map.put(key, addToMap(rawValue));
        }
        return map;
    }

    protected @NotNull Object addToMap(@NotNull String rawValue) {
        return rawValue;
    }

    protected void set(@NotNull String path, @Nullable Object value) {
        getConfig().set(path, value);
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Key {
        @NotNull String value();
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Comment {
        @NotNull String value();
    }
}
