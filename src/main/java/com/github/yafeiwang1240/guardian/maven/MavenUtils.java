package com.github.yafeiwang1240.guardian.maven;

import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MavenUtils {

    private static volatile List<String> goals;

    private static volatile File mvm;

    static {
        goals = new ArrayList<>(3);
        goals.add("clean");
        goals.add("package");
        goals.add("-DskipTests=true");

        mvm = new File("D:\\Maven\\apache-maven-3.6.1");
    }

    public static void cleanPackage(File pom) throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(pom);
        request.setGoals(goals);
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(mvm);
        invoker.setLogger(new PrintStreamLogger(System.err, InvokerLogger.WARN));
        invoker.setOutputHandler(s -> System.out.println(s));
        invoker.execute(request);
    }

    public static void setGoals(List<String> goals) {
        MavenUtils.goals = goals;
    }

    public static void setMvm(File mvm) {
        MavenUtils.mvm = mvm;
    }

    public static void main(String[] args) {
        try {
            cleanPackage(new File("D:/work/git/pom.xml"));
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
    }
}
