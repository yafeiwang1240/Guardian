package com.github.yafeiwang1240.guardian.monitor;

import com.github.yafeiwang1240.guardian.handler.CallBack;

public interface IMonitorServer<D> {
    void monitor(D data, CallBack<D> callBack);
}
