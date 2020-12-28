package com.github.yafeiwang1240.guardian.git;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

/**
 * @author wangyafei
 */
public class GitUtils {

    private static String username;
    private static String password;

    /**
     * clone master
     * @param remoteUrl
     * @param local
     * @throws GitAPIException
     */
    public static void cloneRepository(String remoteUrl, File local) throws GitAPIException {
        cloneRepository(remoteUrl, local, "master");
    }

    /**
     * clone branch
     * @param remoteUrl
     * @param local
     * @param branch
     * @throws GitAPIException
     */
    public static void cloneRepository(String remoteUrl, File local, String branch) throws GitAPIException {
        deleteDir(local);
        local.mkdirs();
        CloneCommand clone = Git.cloneRepository().setURI(remoteUrl).setBranch(branch).setDirectory(local);
        UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(username, password);
        clone.setCredentialsProvider(user);
        Git git = clone.call();
        git.close();
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void main(String[] args) {
        username = "wangyafei@liepin.com";
        password = "*******";
        try {
            cloneRepository("http://gitcode.tongdao.cn/dev4/bi-firewall-web.git",
                    new File("D:\\Work\\git"), "clone");
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
