package com.githup.yafeiwang1240.guardian.server;

import com.githup.yafeiwang1240.guardian.dto.ConsoleDto;
import com.githup.yafeiwang1240.guardian.handler.CallBack;

public interface IProcessServer {
    String execute(Object command, CallBack<ConsoleDto> callBack);
    int destroy(String id);
}
