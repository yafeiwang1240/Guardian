package com.githup.yafeiwang1240.guardian.buffer;

import com.githup.yafeiwang1240.obrien.uitls.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BufferThread implements Runnable{

    private InputStream in;

    @Override
    public void run() {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inputStreamReader);
            String chars = null;
            while (StringUtils.isNotEmpty((chars = bufferedReader.readLine()))) {

            }
        } catch (Exception e) {

        }  finally {
            if(inputStreamReader != null) {
                IOUtils.closeQuietly(inputStreamReader);
            }
            if(bufferedReader != null) {
                IOUtils.closeQuietly(bufferedReader);
            }
        }
    }
}
