package com.github.yafeiwang1240.guardian.executive;

import com.github.yafeiwang1240.guardian.buffer.BufferManagerImpl;
import com.github.yafeiwang1240.guardian.buffer.IBufferManager;
import com.github.yafeiwang1240.guardian.dto.CommandDto;
import com.github.yafeiwang1240.guardian.enums.RecordEnum;
import com.github.yafeiwang1240.guardian.enums.ResultEnum;
import com.github.yafeiwang1240.guardian.factory.ConsoleDtoFactory;
import com.github.yafeiwang1240.guardian.factory.ProcessDtoFactory;
import com.github.yafeiwang1240.guardian.factory.ThreadPoolFactory;
import com.github.yafeiwang1240.guardian.common.CommandDecoder;
import com.github.yafeiwang1240.obrien.lang.Lists;

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

    @Override
    public boolean isAlive(String id) {
        return bufferManager.isAlive(id);
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
                    dtos.forEach(this::commandProcess);
                    dtos.clear();
                    try {
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 修改为最简单的command执行, 并合并数据流
        public void commandProcess(CommandDto dto){
            Object[] val = CommandDecoder.decode(dto.getCommand());
            if(val == null) {
                return;
            }
            Process process = null;
            boolean redirectErrorStream = false;
            try {
                if(val[0] instanceof String[]) {
                    String[] command = (String[]) val[0];
                    process = new ProcessBuilder(command).redirectErrorStream(true).start();
                } else if(val[0] instanceof String) {
                    String command = (String) val[0];
                    process = new ProcessBuilder(command).redirectErrorStream(true).start();
                } else if(val[0] instanceof List){
                    List<String> command = ((List<String>) val[0]);
                    process = new ProcessBuilder(command).redirectErrorStream(true).start();
                }else {
                    dto.getCallBack().invoke(ConsoleDtoFactory.newConsoleDto("错误的命令行模式", RecordEnum.END, ResultEnum.FAILED));
                    return;
                }
                redirectErrorStream = true;
            } catch (Exception e) {
                dto.getCallBack().invoke(ConsoleDtoFactory.newConsoleDto(e.getMessage(), RecordEnum.END, ResultEnum.FAILED));
                return;
            }
            if(process != null)
                bufferManager.read(ProcessDtoFactory.newProcessDto(process, dto, redirectErrorStream));
        }

        // 不合并流
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
                    }else {
                        return;
                    }
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
                    }else {
                        return;
                    }
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
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                // ignore
            }
            if(process != null)
                bufferManager.read(ProcessDtoFactory.newProcessDto(process, dto, false));
        }

        protected void stop() {
            exit = true;
        }
    }
}
