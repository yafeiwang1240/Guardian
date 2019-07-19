package com.github.yafeiwang1240.guardian.dto;

public class ProcessDto {
    private Process process;
    private CommandDto commandDto;
    private boolean redirectErrorStream = false;

    public ProcessDto(Process process, CommandDto commandDto, boolean redirectErrorStream) {
        this.process = process;
        this.commandDto = commandDto;
        this.redirectErrorStream = redirectErrorStream;
    }

    public Process getProcess() {
        return process;
    }

    public CommandDto getCommandDto() {
        return commandDto;
    }

    public boolean isRedirectErrorStream() {
        return redirectErrorStream;
    }
}
