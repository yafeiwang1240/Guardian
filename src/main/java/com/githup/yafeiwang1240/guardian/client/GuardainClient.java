package com.githup.yafeiwang1240.guardian.client;

import com.githup.yafeiwang1240.guardian.dto.ConsoleDto;
import com.githup.yafeiwang1240.guardian.handler.CallBack;
import com.githup.yafeiwang1240.guardian.server.IProcessServer;
import com.githup.yafeiwang1240.guardian.server.ProcessServerImpl;

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

}
