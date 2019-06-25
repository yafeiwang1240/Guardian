package com.githup.yafeiwang1240.guardian.distribute;

import com.githup.yafeiwang1240.guardian.dto.ConsoleDto;
import com.githup.yafeiwang1240.guardian.dto.DistributeDto;
import com.githup.yafeiwang1240.guardian.factory.ThreadPoolFactory;
import com.githup.yafeiwang1240.obrien.lang.Lists;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据异步分发
 */
public class DistributeServerImpl implements IDistributeServer {

    private ArrayDeque<DistributeDto<ConsoleDto>> deque;
    private ReentrantLock lock;
    private ExecutorService service;
    private DistributeThread thread;

    public DistributeServerImpl() {
        deque = new ArrayDeque<>();
        lock = new ReentrantLock();
        service = ThreadPoolFactory.newSingleThreadExecutor();
        thread = new DistributeThread();
        service.execute(thread);
    }

    @Override
    public void distribute(DistributeDto<ConsoleDto> dtoDistributeDto) {
        lock.lock();
        deque.add(dtoDistributeDto);
        lock.unlock();
    }

    private class DistributeThread implements Runnable {
        protected boolean exit = false;

        @Override
        public void run() {
            exit = false;
            List<DistributeDto<ConsoleDto>> dtos = Lists.create(ArrayList::new);
            while (!exit) {
                lock.lock();
                while (!deque.isEmpty()) {
                    dtos.add(deque.poll());
                }
                lock.unlock();
                dtos.forEach(DistributeServerImpl::execute);
                dtos.clear();
            }
        }

        protected void stop() {
            exit = true;
        }
    }

    private static void execute(DistributeDto<ConsoleDto> dto) {
        dto.getCallBack().invoke(dto.getData());
    }
}
