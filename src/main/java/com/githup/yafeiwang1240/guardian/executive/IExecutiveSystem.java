package com.githup.yafeiwang1240.guardian.executive;

import com.githup.yafeiwang1240.guardian.dto.CommandDto;

public interface IExecutiveSystem {
    void execute(CommandDto dto);
    int destroy(String id);
}
