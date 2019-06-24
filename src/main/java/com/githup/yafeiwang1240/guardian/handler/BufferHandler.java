package com.githup.yafeiwang1240.guardian.handler;

public interface BufferHandler<R, S, M> {
    void invoke(R result, S send, M message);
}
