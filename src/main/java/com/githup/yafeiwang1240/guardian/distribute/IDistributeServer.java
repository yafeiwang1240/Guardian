package com.githup.yafeiwang1240.guardian.distribute;

import com.githup.yafeiwang1240.guardian.dto.ConsoleDto;
import com.githup.yafeiwang1240.guardian.dto.DistributeDto;

public interface IDistributeServer {
    void distribute(DistributeDto<ConsoleDto> dtoDistributeDto);
}