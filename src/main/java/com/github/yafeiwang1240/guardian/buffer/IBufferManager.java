package com.github.yafeiwang1240.guardian.buffer;

import com.github.yafeiwang1240.guardian.dto.ProcessDto;

public interface IBufferManager {
    void read(ProcessDto dto);
    int destroy(String id);
    boolean isAlive(String id);
}
