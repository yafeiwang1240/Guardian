package com.github.yafeiwang1240.guardian.dto;

import com.github.yafeiwang1240.guardian.handler.CallBack;

public class DistributeDto<T> {
    private CallBack<T> callBack;
    private T data;

    public DistributeDto(CallBack<T> callBack, T data) {
        this.callBack = callBack;
        this.data = data;
    }

    public CallBack<T> getCallBack() {
        return callBack;
    }

    public T getData() {
        return data;
    }
}
