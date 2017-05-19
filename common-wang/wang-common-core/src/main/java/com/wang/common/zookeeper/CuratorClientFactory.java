package com.wang.common.zookeeper;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.wang.common.resource.ResourcesUtils;

/**
 * @ClassName: CuratorClientFactory
 * @Description: zookeeper客户端实例化工厂
 * @author administrator
 * @date 2016年5月19日 上午10:58:59
 *
 */
public class CuratorClientFactory
{

    private static CuratorFramework client;

    /**
     * @Title: getInstance
     * @Description: 获取zookeeper客户端对象
     * @author administrator 2016年5月19日 上午10:53:38
     * @return
     * @throws
     */
    public static CuratorFramework getInstance()
    {
        if (client != null)
        {
            if (client.getState().compareTo(CuratorFrameworkState.STARTED) != 0)
            {
                client.start();
            }
            return client;
        }
        else
        {
            client = newClient();
            client.start();
            return client;
        }
    }

    /**
     * @Title: newClient
     * @Description: 实例化zookeeper客户端对象
     * @author administrator 2016年5月19日 上午10:53:33
     * @return
     * @throws
     */
    private static CuratorFramework newClient()
    {
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(ResourcesUtils.bundle_service.getString("CURATOR_CONN"))
                .sessionTimeoutMs(10000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(
                        new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace(ResourcesUtils.bundle_service.getString("CURATOR_NAMESPACE"))
                .defaultData(null)
                .authorization("digest", "admin:admin123321".getBytes())
                .build();
        return client;
        /* client.start(); */

    }

    /**
     * @Title: newClientAcl
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author administrator 2016年5月19日 上午10:58:43
     * @return
     * @throws
     */
    public static CuratorFramework newClientAcl()
    {
        String up = "";
        try
        {
            up = DigestAuthenticationProvider
                    .generateDigest("admin:admin123321");
            System.out.println(up);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        AuthInfo authinfo = new AuthInfo("digest",
                "admin:admin123321".getBytes());
        List<AuthInfo> aList = new ArrayList<AuthInfo>();
        aList.add(authinfo);

        // -Dzookeeper.DigestAuthenticationProvider.superDigest=admin:XNayzH0KHS9YkzwRXa0HNAvPPMc=
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString("192.168.1.5:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(
                        new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace("two").defaultData(null)
                .authorization("digest", "admin:admin123321".getBytes())
                // .authorization(aList)
                .build();
        return client;
        /* client.start(); */

    }

    public static void main(String[] args)
    {
        try
        {
            client = newClient();
            String digest = DigestAuthenticationProvider
                    .generateDigest("admin:admin123321");
            client.start();
            System.out.println(client.isStarted() + digest);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }
}
