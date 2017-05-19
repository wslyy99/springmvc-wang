package com.wang.common.proxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;

import com.wang.common.resource.ResourcesUtils;
import com.wang.common.util.Encodes;
import com.wang.common.util.GetImage;
import com.wang.common.util.ImageUtil;

/**
 * @ClassName: ProxyClient
 * @Description: 代理服务器操作客户端
 * @author administrator
 * @date 2016年7月15日 下午5:02:42
 *
 */
public class ProxyClient
{

    /**
     * @Title: httpProxy
     * @Description: 使用http代理服务器下载文件
     * @author administrator 2016年7月15日 下午5:02:45
     * @param ip
     *            代理服务器的ip地址
     * @param port
     *            代理服务器的端口
     * @param savePath
     *            文件保存路径
     * @param urlAddress
     *            使用代理服务器访问的http地址
     * @param fileNewName
     *            文件重命名
     * @return 返回修改名字后的路径
     * @throws Exception
     * @throws
     */
    public static String httpProxyDownLoad(String savePath, String urlAddress,
            String fileNewName) throws Exception
    {
        final String username = ResourcesUtils.bundle_service
                .getString("proxy_username");
        final String password = ResourcesUtils.bundle_service
                .getString("proxy_password");
        final String ip = ResourcesUtils.bundle_service.getString("proxy_ip_address");
        final int port = Integer.valueOf(ResourcesUtils.bundle_service
                .getString("proxy_ip_port"));
        Authenticator.setDefault(new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password
                        .toCharArray());
            }
        });
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(ip, port));
        URL url = new URL(urlAddress);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        conn.setConnectTimeout(10000);
        InputStream is = conn.getInputStream();
        // BufferedReader in = new BufferedReader(new InputStreamReader(is));
        // StringBuffer text = new StringBuffer();
        // String line = null;
        // while ((line = in.readLine()) != null)
        // {
        // text.append(line);
        // }
        String resultString = GetImage.writeImageToDisk(
                GetImage.readInputStream(is), savePath, fileNewName);
        if (is != null)
        {
            is.close();
        }
        if (conn != null)
        {
            conn.disconnect();
        }
        return resultString;
    }

    /**
     * @Title: httpProxy
     * @Description: 使用http代理服务器下载文件
     * @author administrator 2016年7月15日 下午5:02:45
     * @param ip
     *            代理服务器的ip地址
     * @param port
     *            代理服务器的端口
     * @param savePath
     *            文件保存路径
     * @param urlAddress
     *            使用代理服务器访问的http地址
     * @param fileNewName
     *            文件重命名
     * @return 返回修改名字后的路径
     * @throws Exception
     * @throws
     */
    public static byte[] httpProxyReadByte(String urlAddress) throws Exception
    {
        final String username = ResourcesUtils.bundle_service
                .getString("proxy_username");
        final String password = ResourcesUtils.bundle_service
                .getString("proxy_password");
        final String ip = ResourcesUtils.bundle_service.getString("proxy_ip_address");
        final int port = Integer.valueOf(ResourcesUtils.bundle_service
                .getString("proxy_ip_port"));
        Authenticator.setDefault(new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password
                        .toCharArray());
            }
        });
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress(ip, port));
        URL url = new URL(urlAddress);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000);
        InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
        
        // byte[] btImg = Thumbnails.of(inStream).scale(1).outputQuality(1)
        // .toString().getBytes();

        byte[] btImg = GetImage.readInputStream(inStream);// 得到图片的二进制数据

        if (inStream != null)
        {
            inStream.close();
        }
        if (conn != null)
        {
            conn.disconnect();
        }
        return btImg;
    }

    /**
     * @Title: socket5
     * @Description: 使用socket代理服务器（测试版本，需要根据需求改装）
     * @author administrator 2016年7月15日 下午5:12:05
     * @param ip
     * @param port
     * @throws Exception
     * @throws
     */
    static void socket5(String ip, int port) throws Exception
    {
        Authenticator.setDefault(new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("username", "password"
                        .toCharArray());
            }
        });
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ip,
                port));
        Socket socket = new Socket(proxy);
        socket.connect(new InetSocketAddress("www.baidu.com", 80));
        OutputStream output = socket.getOutputStream();
        InputStreamReader isr = new InputStreamReader(socket.getInputStream(),
                "GBK");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder request = new StringBuilder();
        request.append("GET /index.php HTTP/1.1\r\n");
        request.append("Accept-Language: zh-cn\r\n");
        request.append("Host: www.baidu.com\r\n");
        request.append("\r\n");
        output.write(request.toString().getBytes());
        output.flush();
        StringBuilder sb = new StringBuilder();
        String str = null;
        while ((str = br.readLine()) != null)
        {
            sb.append(str + "\n");
        }
        System.out.println(sb.toString());
        br.close();
        isr.close();
        output.close();
    }


    public static void main(String[] args)
    {
        try
        {
            String sourcePathString = "http://wx.qlogo.cn/mmopen/zOGvhicken5pgOocukdqv3ogiaGoD5lNO5QTDQPYSxOd0tWG8kALwCDo6Xz3vUGP2CNbBLRGEN0EeK6Q3pQuV9OfSBlm1FGYNQ/0";
            String savePathString = "c:\\image\\";
            String newNameString = "newName";
            String fullPath = httpProxyDownLoad(savePathString,
                    sourcePathString, newNameString);
            File file = new File(fullPath);
            File target = new File("c:\\image\\target.gif");
            ImageUtil.scale(file, target, 80, 80, ImageUtil.SCALETYPE_MIN,
                    false, 1.0f);
            System.out.println("压缩后："
                    + Encodes.encodeBase64(ImageUtil
                            .image2Bytes("c:\\image\\target.gif")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
