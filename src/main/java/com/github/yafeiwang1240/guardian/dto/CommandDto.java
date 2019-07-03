package com.github.yafeiwang1240.guardian.dto;

import com.github.yafeiwang1240.guardian.handler.CallBack;

public class CommandDto {
    private String id;
    private Object command;
    private CallBack<ConsoleDto> callBack;

    public CommandDto(String id, Object command, CallBack<ConsoleDto> callBack) {
        this.id = id;
        this.callBack = callBack;
        this.command = command;
    }

    public String getId() {
        return id;
    }

    public Object getCommand() {
        return command;
    }

    public CallBack<ConsoleDto> getCallBack() {
        return callBack;
    }
}
