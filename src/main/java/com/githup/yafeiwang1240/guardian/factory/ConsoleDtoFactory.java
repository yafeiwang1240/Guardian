package com.githup.yafeiwang1240.guardian.factory;

import com.githup.yafeiwang1240.guardian.dto.ConsoleDto;
import com.githup.yafeiwang1240.guardian.enums.RecordEnum;
import com.githup.yafeiwang1240.guardian.enums.ResultEnum;

public class ConsoleDtoFactory {
    public static ConsoleDto newConsoleDto(String print, RecordEnum record, ResultEnum result) {
        return new ConsoleDto(print, record, result);
    }
}
