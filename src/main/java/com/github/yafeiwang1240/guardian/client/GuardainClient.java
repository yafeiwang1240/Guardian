package com.github.yafeiwang1240.guardian.client;

import com.github.yafeiwang1240.guardian.dto.ConsoleDto;
import com.github.yafeiwang1240.guardian.handler.CallBack;
import com.github.yafeiwang1240.guardian.server.IProcessServer;
import com.github.yafeiwang1240.guardian.server.ProcessServerImpl;

/**
 * 进程管理客户端
 */
public class GuardainClient {

    private static IProcessServer server;
    static {
        server = new ProcessServerImpl();
    }
    public static String execute(Object command, CallBack<ConsoleDto> callBack) {
        return server.execute(command, callBack);
    }

    public static int destroy(String id) {
        return server.destroy(id);
    }

    public static boolean isAlive(String id) {
        return server.isAlive(id);
    }
}
