package com.githup.yafeiwang1240.guardian.buffer;

import com.githup.yafeiwang1240.guardian.dto.ProcessDto;

public interface BufferContainer {
    void take(ProcessDto dto);
}
