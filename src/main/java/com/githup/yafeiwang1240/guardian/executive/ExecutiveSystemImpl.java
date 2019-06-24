package com.githup.yafeiwang1240.guardian.executive;

import com.githup.yafeiwang1240.guardian.buffer.BufferManagerImpl;
import com.githup.yafeiwang1240.guardian.buffer.IBufferManager;
import com.githup.yafeiwang1240.guardian.common.CommandDecoder;
import com.githup.yafeiwang1240.guardian.dto.CommandDto;
import com.githup.yafeiwang1240.guardian.factory.ProcessDtoFactory;
import com.githup.yafeiwang1240.guardian.factory.ThreadPoolFactory;
import com.githup.yafeiwang1240.obrien.lang.Lists;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 进程调度
 */
public class ExecutiveSystemImpl implements IExecutiveSystem {

    private ArrayDeque<CommandDto> deque;
    private Object[] lock;
    private ExecutorService service;
    private SystemThread thread;
    private IBufferManager bufferManager;

    public ExecutiveSystemImpl() {
        deque = new ArrayDeque<>();
        lock = new Object[0];
        service = ThreadPoolFactory.newSingleThreadExecutor();
        bufferManager = new BufferManagerImpl();
        thread = new SystemThread();
        service.execute(thread);
    }

    @Override
    public void execute(CommandDto dto) {
        synchronized (lock) {
            deque.add(dto);
            lock.notify();
        }
    }

    @Override
    public int destroy(String id) {
        return bufferManager.destroy(id);
    }

    private class SystemThread implements Runnable {

        protected boolean exit = false;

        @Override
        public void run() {
            exit = false;
            List<CommandDto> dtos = Lists.create(ArrayList::new);
            while (!exit) {
                synchronized (lock) {
                    while (!deque.isEmpty()) {
                        dtos.add(deque.poll());
                    }
                    dtos.forEach(this::command);
                    try {
                        lock.wait();
                    } catch (Exception e) {

                    }
                }
            }
        }

        public void command(CommandDto dto) {
            Object[] val = CommandDecoder.decode(dto.getCommand());
            if(val == null) {
                return;
            }
            Process process = null;
            try {
                if(val.length == 1) {
                    if(val[0] instanceof String[]) {
                        String[] command = (String[]) val[0];
                        process = Runtime.getRuntime().exec(command);
                    } else if(val[0] instanceof String) {
                        String command = (String) val[0];
                        process = Runtime.getRuntime().exec(command);
                    } else if(val[0] instanceof List){
                        List<String> command = ((List<String>) val[0]);
                        process = new ProcessBuilder(command).redirectErrorStream(false).start();
                    }
                    return;
                } else if(val.length == 2) {
                    if(!(val[1] instanceof String[])) {
                        return;
                    }
                    String[] env = (String[]) val[1];
                    if(val[0] instanceof String[]) {
                        String[] command = (String[]) val[0];
                        process = Runtime.getRuntime().exec(command, env);
                    } else if(val[0] instanceof String) {
                        String command = (String) val[0];
                        process = Runtime.getRuntime().exec(command, env);
                    }
                    return;
                } else if(val.length == 3) {
                    if(!(val[1] instanceof String[] || !(val[2] instanceof String))) {
                        return;
                    }
                    String[] env = (String[]) val[1];
                    String dir = (String) val[2];
                    File file = new File(dir);
                    if(val[0] instanceof String[]) {
                        String[] command = (String[]) val[0];
                        process = Runtime.getRuntime().exec(command, env, file);
                    } else if(val[0] instanceof String) {
                        String command = (String) val[0];
                        process = Runtime.getRuntime().exec(command, env, file);
                    }
                    return;
                }
                return;
            } catch (Exception e) {
                // ignore
            }
            if(process != null)
                bufferManager.read(ProcessDtoFactory.newProcessDto(process, dto));
        }

        protected void stop() {
            exit = true;
        }
    }
}
