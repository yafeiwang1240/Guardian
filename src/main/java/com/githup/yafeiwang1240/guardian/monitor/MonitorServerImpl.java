package com.githup.yafeiwang1240.guardian.monitor;

import com.githup.yafeiwang1240.guardian.distribute.DistributeServerImpl;
import com.githup.yafeiwang1240.guardian.distribute.IDistributeServer;
import com.githup.yafeiwang1240.guardian.dto.DistributeDto;
import com.githup.yafeiwang1240.guardian.handler.CallBack;

/**
 * 监控服务
 * @param <D>
 */
public class MonitorServerImpl<D> implements IMonitorServer<D> {

    private IDistributeServer server = new DistributeServerImpl();

    @Override
    public void monitor(D data, CallBack<D> callBack) {
        server.distribute(new DistributeDto(callBack, data));
    }
}
