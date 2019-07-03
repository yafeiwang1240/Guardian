package com.github.yafeiwang1240.guardian.dto;

public class ProcessDto {
    private Process process;
    private CommandDto commandDto;

    public ProcessDto(Process process, CommandDto commandDto) {
        this.process = process;
        this.commandDto = commandDto;
    }

    public Process getProcess() {
        return process;
    }

    public CommandDto getCommandDto() {
        return commandDto;
    }
}
