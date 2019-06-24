package com.githup.yafeiwang1240.guardian.factory;

import com.githup.yafeiwang1240.guardian.dto.CommandDto;
import com.githup.yafeiwang1240.guardian.dto.ConsoleDto;
import com.githup.yafeiwang1240.guardian.handler.CallBack;

public class CommandDtoFactory {
    public static CommandDto newCommandDto(String id, Object command, CallBack<ConsoleDto> callBack) {
        return new CommandDto(id, command, callBack);
    }
}
