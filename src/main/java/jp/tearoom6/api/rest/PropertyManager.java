package jp.tearoom6.api.rest;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * プロパティファイルのメッセージ管理用のクラス
 */
public class PropertyManager {

    private Properties props;

    public PropertyManager(InputStream is) throws IOException {
        props = new Properties();
        props.load(is);
    }

    public String getProperty(String key, String defaultValue, Object... replaceArgs) {
        String property = props.getProperty(key);
        if (property == null || property.isEmpty())
            return defaultValue;
        return MessageFormat.format(property, replaceArgs);
    }

}
