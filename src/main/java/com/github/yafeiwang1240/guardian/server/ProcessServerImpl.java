package com.github.yafeiwang1240.guardian.server;

import com.github.yafeiwang1240.guardian.dto.CommandDto;
import com.github.yafeiwang1240.guardian.dto.ConsoleDto;
import com.github.yafeiwang1240.guardian.executive.ExecutiveSystemImpl;
import com.github.yafeiwang1240.guardian.factory.CommandDtoFactory;
import com.github.yafeiwang1240.guardian.handler.CallBack;
import com.github.yafeiwang1240.guardian.common.CommandValidateResult;
import com.github.yafeiwang1240.guardian.common.CommandValidateUtils;
import com.github.yafeiwang1240.guardian.executive.IExecutiveSystem;

import java.util.UUID;

/**
 * 进程管理服务
 */
public class ProcessServerImpl implements IProcessServer {

    private IExecutiveSystem system = new ExecutiveSystemImpl();

    @Override
    public String execute(Object command, CallBack<ConsoleDto> callBack) {
        CommandValidateResult result = CommandValidateUtils.validate(command);
        if(result.getStatus() == CommandValidateResult.Status.FAILED) {
            StringBuilder builder = new StringBuilder();
            result.getMessage().forEach(builder::append);
            throw new IllegalArgumentException(builder.toString());
        }
        String id = UUID.randomUUID().toString();
        CommandDto dto = CommandDtoFactory.newCommandDto(id, command, callBack);
        system.execute(dto);
        return id;
    }

    @Override
    public int destroy(String id) {
        return system.destroy(id);
    }

    @Override
    public boolean isAlive(String id) {
        return system.isAlive(id);
    }
}
