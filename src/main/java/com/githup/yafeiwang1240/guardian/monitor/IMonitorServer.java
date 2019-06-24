package com.githup.yafeiwang1240.guardian.monitor;

import com.githup.yafeiwang1240.guardian.handler.CallBack;

public interface IMonitorServer<D> {
    void monitor(D data, CallBack<D> callBack);
}
