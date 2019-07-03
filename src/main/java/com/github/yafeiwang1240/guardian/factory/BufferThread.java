package com.github.yafeiwang1240.guardian.factory;

import com.github.yafeiwang1240.guardian.enums.RecordEnum;
import com.github.yafeiwang1240.guardian.handler.BufferHandler;
import com.github.yafeiwang1240.obrien.uitls.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BufferThread implements Runnable{

    private InputStream in;
    private RecordEnum recordEnum;
    private String id;
    private BufferHandler<String, RecordEnum, String> handler;

    public void setIn(InputStream in) {
        this.in = in;
    }

    public void setRecordEnum(RecordEnum recordEnum) {
        this.recordEnum = recordEnum;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BufferHandler<String, RecordEnum, String> getHandler() {
        return handler;
    }

    public void setHandler(BufferHandler<String, RecordEnum, String> handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inputStreamReader);
            String chars;
            while (StringUtils.isNotEmpty((chars = bufferedReader.readLine()))) {
                handler.invoke(id, recordEnum, chars);
            }
        } catch (Exception e) {
            // ignore
        }  finally {
            if(inputStreamReader != null) {
                IOUtils.closeQuietly(inputStreamReader);
            }
            if(inputStreamReader != null) {
                IOUtils.closeQuietly(in);
            }
            if(bufferedReader != null) {
                IOUtils.closeQuietly(bufferedReader);
            }
            handler.invoke(id, RecordEnum.END, recordEnum == RecordEnum.ERROR ? "结束异常流缓冲" : "结束普通输出流缓冲");
        }
    }

    public void clear() {
        this.id = null;
        this.in = null;
        this.recordEnum = null;
    }
}
