package com.github.yafeiwang1240.guardian.buffer;

import com.github.yafeiwang1240.guardian.dto.ConsoleDto;
import com.github.yafeiwang1240.guardian.dto.ProcessDto;
import com.github.yafeiwang1240.guardian.enums.RecordEnum;
import com.github.yafeiwang1240.guardian.enums.ResultEnum;
import com.github.yafeiwang1240.guardian.factory.ConsoleDtoFactory;
import com.github.yafeiwang1240.guardian.factory.ThreadPoolFactory;
import com.github.yafeiwang1240.guardian.monitor.IMonitorServer;
import com.github.yafeiwang1240.guardian.monitor.MonitorServerImpl;
import com.github.yafeiwang1240.obrien.lang.Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 缓冲管理
 */
public class BufferManagerImpl implements IBufferManager {

    private ThreadPoolExecutor executor;
    private Map<String, ProcessDto> processDtoMap;
    private BufferContainer container;
    private IMonitorServer<ConsoleDto> monitorServer;

    public BufferManagerImpl() {
        init();
    }

    private void init() {
        executor = ThreadPoolFactory.newThreadPoolExecutor();
        processDtoMap = Maps.create(ConcurrentHashMap::new);
        monitorServer = new MonitorServerImpl<>();
        container = new BufferBeanContainer((_r, _s, _d) -> {
            if(_d.getHandler() == null) {
                _d.setHandler((__r, __s, __d) -> machining(__r, __s, __d) );
            }
            executor.execute(_d);
        });
    }

    @Override
    public void read(ProcessDto dto) {
        processDtoMap.put(dto.getCommandDto().getId(), dto);
        container.take(dto);
        // 第一条日志
        ConsoleDto consoleDto = ConsoleDtoFactory.newConsoleDto("开始监控控制台", RecordEnum.NEW, ResultEnum.NONE);
        monitorServer.monitor(consoleDto, dto.getCommandDto().getCallBack());
    }

    @Override
    public int destroy(String id) {
        ProcessDto processDto = processDtoMap.remove(id);
        if(processDto == null) {
            return Integer.MIN_VALUE;
        }
        processDto.getProcess().destroy();
        return processDto.getProcess().exitValue();
    }

    @Override
    public boolean isAlive(String id) {
        ProcessDto processDto = processDtoMap.get(id);
        if(processDto == null) {
            return false;
        }
        return processDto.getProcess().isAlive();
    }

    private void machining(String id, RecordEnum recordEnum, String print) {
        ProcessDto processDto = processDtoMap.get(id);
        // 缓冲结束
        if(processDto == null) {
            return;
        }
        if(recordEnum == RecordEnum.END) {
            // 判断的当前进程是否依然存活
            if(processDto.getProcess().isAlive()) {
                return;
            } else {
                int exit = processDto.getProcess().exitValue();
                ConsoleDto consoleDto = ConsoleDtoFactory.newConsoleDto(print, RecordEnum.END, exit == 0 ? ResultEnum.SUCCEED : ResultEnum.FAILED);
                monitorServer.monitor(consoleDto, processDto.getCommandDto().getCallBack());

                // 缓冲完毕，不在管理
                processDtoMap.remove(id);
            }
        }else {
            ConsoleDto consoleDto = ConsoleDtoFactory.newConsoleDto(print, recordEnum, ResultEnum.NONE);
            monitorServer.monitor(consoleDto, processDto.getCommandDto().getCallBack());
        }
    }
}
