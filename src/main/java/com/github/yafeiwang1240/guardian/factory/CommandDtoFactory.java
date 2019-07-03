package com.github.yafeiwang1240.guardian.factory;

import com.github.yafeiwang1240.guardian.dto.CommandDto;
import com.github.yafeiwang1240.guardian.dto.ConsoleDto;
import com.github.yafeiwang1240.guardian.handler.CallBack;

public class CommandDtoFactory {
    public static CommandDto newCommandDto(String id, Object command, CallBack<ConsoleDto> callBack) {
        return new CommandDto(id, command, callBack);
    }
}
