package com.github.yafeiwang1240.guardian.buffer;

import com.github.yafeiwang1240.guardian.dto.ProcessDto;

public interface BufferContainer {
    void take(ProcessDto dto);
}
