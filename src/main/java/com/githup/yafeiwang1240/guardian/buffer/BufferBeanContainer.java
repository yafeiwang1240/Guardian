package com.githup.yafeiwang1240.guardian.buffer;

import com.githup.yafeiwang1240.guardian.dto.ProcessDto;
import com.githup.yafeiwang1240.guardian.enums.RecordEnum;
import com.githup.yafeiwang1240.guardian.factory.BufferThread;
import com.githup.yafeiwang1240.guardian.factory.BufferThreadFactory;
import com.githup.yafeiwang1240.guardian.handler.BufferHandler;

public class BufferBeanContainer implements BufferContainer {
    private BufferHandler<String, RecordEnum, BufferThread> handler;

    public BufferBeanContainer(BufferHandler<String, RecordEnum, BufferThread> handler) {
        this.handler = handler;
    }

    @Override
    public void take(ProcessDto dto) {
        BufferThread threadInput = BufferThreadFactory.newBufferThread();
        threadInput.setId(dto.getCommandDto().getId());
        threadInput.setIn(dto.getProcess().getInputStream());
        threadInput.setRecordEnum(RecordEnum.INFO);
        handler.invoke(dto.getCommandDto().getId(), RecordEnum.INFO, threadInput);
        BufferThread threadError = BufferThreadFactory.newBufferThread();
        threadError.setId(dto.getCommandDto().getId());
        threadError.setIn(dto.getProcess().getErrorStream());
        threadError.setRecordEnum(RecordEnum.ERROR);
        handler.invoke(dto.getCommandDto().getId(), RecordEnum.ERROR, threadError);
    }

}
