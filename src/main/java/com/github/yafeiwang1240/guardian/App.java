package com.github.yafeiwang1240.guardian;

import com.github.yafeiwang1240.guardian.process.ProcessFactory;
import com.github.yafeiwang1240.guardian.process.ProcessHandler;

/**
 * Hello World!
 */
public class App {

    public static void main(String[] args) {
        ProcessHandler handler = ProcessFactory.newProcessHandler("dir");
        handler.run();
    }

}
