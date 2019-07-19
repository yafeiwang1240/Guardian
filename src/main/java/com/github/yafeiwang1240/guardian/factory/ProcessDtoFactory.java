package com.github.yafeiwang1240.guardian.factory;

import com.github.yafeiwang1240.guardian.dto.CommandDto;
import com.github.yafeiwang1240.guardian.dto.ProcessDto;

public class ProcessDtoFactory {
    public static ProcessDto newProcessDto(Process process, CommandDto commandDto, boolean redirectErrorStream) {
        return new ProcessDto(process, commandDto, redirectErrorStream);
    }
}
