package com.githup.yafeiwang1240.guardian.system;

import com.githup.yafeiwang1240.obrien.uitls.IOUtils;
import com.sun.jna.Library;
import com.sun.jna.Native;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

/**
 * 封装获取系统信息
 */
public class SystemEnvironment {

    private static Properties properties;
    private static InetAddress address;
    private static Map<String, String> envMap;
    private static OS os;
    private static String userhome;
    private static String userdir;
    private static String username;

    public enum OS {
        LINUX,
        WINDOWS,
    }

    static {
        properties = System.getProperties();
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        envMap = System.getenv();
        os = osname().toLowerCase().contains("windows") ? OS.WINDOWS : OS.LINUX;
        userhome = properties.getProperty("user.home");
        userdir = properties.getProperty("user.dir");
        username = properties.getProperty("user.name");
    }

    /**
     * 获取系统ip
     * @return
     */
    public static String ip() {
        return address.getHostAddress();
    }

    /**
     * 获取当前用户名
     * @return
     */
    public static String username() {
//        return envMap.get("USERNAME");
        return username;
    }

    /**
     * 系统名称
     * @return
     */
    public static String osname() {
        return properties.getProperty("os.name");
    }

    /**
     * 系统架构
     * @return
     */
    public static String osarch() {
        return properties.getProperty("os.arch");
    }

    /**
     * 系统版本
     * @return
     */
    public static String osversion() {
        return properties.getProperty("os.version");
    }

    /**
     * 当前目录
     * @return
     */
    public static String userdir() {
        return userdir;
    }

    public static String userhome() {
        return userhome;
    }

    /**
     *  系统类型
     * @return
     */
    public static OS os() {
        return os;
    }

    /**
     * 指定进程pid
     * @param process
     * @return
     */
    public static long pid(Object process) {
        if(os == OS.LINUX && process instanceof String) {
            String command = (String) process;
            return getLinuxPid(command);
        }
        if(os == OS.WINDOWS && process instanceof Process) {
            Process _process = (Process) process;
            return getWindowsPid(_process);
        }
        throw new IllegalArgumentException("无效的输入");
    }

    private static long getWindowsPid(Process process) {
        long pid = -1;
        Field field;
        try {
            field = process.getClass().getDeclaredField("handle");
            field.setAccessible(true);
            pid = Kernel32.INSTANCE.GetProcessId((long) field.get(process));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            // ignore
        }
        return pid;
    }

    private interface Kernel32 extends Library {

        static Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

        long GetProcessId(long hProcess);
    }

    private static long getLinuxPid(String command) {
        BufferedReader reader = null;
        InputStreamReader inputStreamReader = null;
        try {
            Process process = Runtime.getRuntime().exec("ps -ef");
            inputStreamReader = new InputStreamReader(process.getInputStream());
            reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(command)) {
                    System.out.println("相关信息 -----> " + command);
                    String[] chars = line.split("\\s+");
                    return Long.parseLong(chars[1]);
                }
            }
        } catch (Exception e) {
            // ignore
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStreamReader);
        }
        return -1;
    }
}
