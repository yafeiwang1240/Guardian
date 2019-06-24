package com.githup.yafeiwang1240.guardian.factory;

import com.githup.yafeiwang1240.guardian.dto.CommandDto;
import com.githup.yafeiwang1240.guardian.dto.ProcessDto;

public class ProcessDtoFactory {
    public static ProcessDto newProcessDto(Process process, CommandDto commandDto) {
        return new ProcessDto(process, commandDto);
    }
}
