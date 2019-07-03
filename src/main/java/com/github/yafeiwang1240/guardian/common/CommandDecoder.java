package com.github.yafeiwang1240.guardian.common;

import com.github.yafeiwang1240.guardian.annotation.Command;
import com.github.yafeiwang1240.guardian.annotation.Directory;
import com.github.yafeiwang1240.guardian.annotation.Environment;

import java.lang.reflect.Field;

public class CommandDecoder {

    public static Object[] decode(Object o) {
        Class clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field command = null;
        Field env = null;
        Field dir = null;
        for (Field field : fields) {
            if(field.getAnnotation(Command.class) != null) {
                field.setAccessible(true);
                try {
                    Object val = field.get(o);
                    if(val != null && val.toString().trim().length() > 0) {
                        command = field;
                    }
                } catch (IllegalAccessException e) {
                    // 忽略异常
                }
            } else if(field.getAnnotation(Environment.class) != null) {
                field.setAccessible(true);
                try {
                    Object val = field.get(o);
                    if(val != null && val.toString().trim().length() > 0) {
                        env = field;
                    }
                } catch (IllegalAccessException e) {
                    // 忽略异常
                }
            } else if(field.getAnnotation(Directory.class) != null) {
                field.setAccessible(true);
                try {
                    Object val = field.get(o);
                    if(val != null && val.toString().trim().length() > 0) {
                        dir = field;
                    }
                } catch (IllegalAccessException e) {
                    // 忽略异常
                }
            }
        }
        try {
            if(env != null) {
                if(dir != null) {
                    return new Object[]{command.get(o), env.get(o), dir.get(o)};
                }
                return new Object[]{command.get(o), env.get(o)};
            }
            return command == null ? null : new Object[]{command.get(o)};
        } catch (Exception e) {
            // 忽略异常
        }
        return null;
    }
}
