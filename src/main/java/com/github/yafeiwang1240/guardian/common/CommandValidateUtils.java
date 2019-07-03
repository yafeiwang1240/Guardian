package com.github.yafeiwang1240.guardian.common;

import com.github.yafeiwang1240.guardian.annotation.Command;
import com.github.yafeiwang1240.guardian.annotation.ProcessElement;

import java.lang.reflect.Field;

public class CommandValidateUtils {

    /**
     * 命令行对象校验
     */
    public static CommandValidateResult validate(Object o) {
        CommandValidateResult result = new CommandValidateResult();
        result.setStatus(CommandValidateResult.Status.FAILED);
        Class clazz = o.getClass();
        if(clazz.getAnnotation(ProcessElement.class) == null) {
            result.add("command must annotation: " + ProcessElement.class.getName());
            return result;
        }
        Field[] fields = clazz.getDeclaredFields();
        Field command = null;
        for (Field field : fields) {
            if(field.getAnnotation(Command.class) != null) {
                field.setAccessible(true);
                try {
                    Object val = field.get(o);
                    if(val == null || val.toString().trim().length() <= 0) {
                        result.add("command not is empty");
                        return result;
                    }
                    command = field;
                } catch (IllegalAccessException e) {
                    result.add(e.getMessage());
                    return result;
                }
            }
        }

        if(command == null) {
            result.add("command must annotation: " + Command.class);
            return result;
        }
        result.setStatus(CommandValidateResult.Status.SUCCEED);
        return result;
    }
}
