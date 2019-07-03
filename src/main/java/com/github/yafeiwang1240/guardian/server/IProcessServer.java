package com.github.yafeiwang1240.guardian.server;

import com.github.yafeiwang1240.guardian.dto.ConsoleDto;
import com.github.yafeiwang1240.guardian.handler.CallBack;

public interface IProcessServer {
    String execute(Object command, CallBack<ConsoleDto> callBack);
    int destroy(String id);
    boolean isAlive(String id);
}
