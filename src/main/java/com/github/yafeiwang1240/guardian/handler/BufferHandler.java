package com.github.yafeiwang1240.guardian.handler;

public interface BufferHandler<R, S, M> {
    void invoke(R result, S send, M message);
}
