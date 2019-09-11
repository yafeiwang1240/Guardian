package com.github.yafeiwang1240.guardian.system;

import java.io.File;
import java.io.IOException;

/**
 * 系统操作
 *
 * @author wangyafei
 */
public class SystemPath {

    // 根据操作系统进行区分分割符
    private static final String split;

    static {
        split = SystemEnvironment.os() == SystemEnvironment.OS.WINDOWS ? "\\" : "/";
    }

    /**
     * 系统路径拼接
     * @param values
     * @return
     */
    public static String join(String... values) {
        if (values == null || values.length <= 0) {
            return split;
        }
        int length = values.length;
        StringBuilder builder = new StringBuilder(values[0]);
        for (int i = 1; i < length; i++) {
            String value = values[i];
            String _value = value;
            if (_value == null || _value.equals("")) {
                _value = split;
            } else if (!_value.startsWith(split)) {
                _value = split + _value;
            } else {
                builder.replace(0, builder.length(), "");
            }
            builder.append(_value);
        }
        return builder.toString();
    }

    /**
     * 文件是否存在
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * mkdir
     * @param path
     * @return
     */
    public static File mkdir(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }
        if (file.mkdir()) {
            return file;
        }
        return null;
    }

    /**
     * mkdirs
     * @param path
     * @return
     */
    public static File mkdirs(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }
        if (file.mkdirs()) {
            return file;
        }
        return null;
    }

    /**
     * create if not exist
     * @param path
     * @return
     * @throws IOException
     */
    public static File createNotExist(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
