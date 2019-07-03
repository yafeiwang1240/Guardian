package com.github.yafeiwang1240.guardian.dto;

import com.github.yafeiwang1240.guardian.enums.ResultEnum;
import com.github.yafeiwang1240.guardian.enums.RecordEnum;

public class ConsoleDto {
    private String print;
    private RecordEnum record;
    private ResultEnum result;

    public ConsoleDto(String print, RecordEnum record, ResultEnum result) {
        this.print = print;
        this.record = record;
        this.result = result;
    }

    public String getPrint() {
        return print;
    }

    public RecordEnum getRecord() {
        return record;
    }

    public ResultEnum getResult() {
        return result;
    }
}
