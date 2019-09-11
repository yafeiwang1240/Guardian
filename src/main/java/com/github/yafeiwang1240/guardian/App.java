package com.github.yafeiwang1240.guardian;

import com.github.yafeiwang1240.guardian.system.SystemEnvironment;
import com.github.yafeiwang1240.obrien.lang.Lists;

/**
 * Hello World!
 */
public class App {

    public static void main(String[] args) {
        System.out.println(SystemEnvironment.os().toString());
        System.out.println(SystemEnvironment.username());
        System.out.println(SystemEnvironment.getClassLoadPath());
        System.out.println(SystemEnvironment.ip());
        Lists.asList(1).forEach(System.out::println);
    }

}
