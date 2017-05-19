/**  
 * @Title:  GetImage.java
 * @Package com.dowin.utils.common
 * @Description: TODO(用一句话描述该文件做什么)
 * @author administrator
 * @date  2016年7月5日 上午11:45:52
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @ClassName: GetImage
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author administrator
 * @date 2016年7月5日 上午11:45:52
 *
 */
public class GetImage
{

    public static void main(String[] args) throws IOException
    {
        // String url =
        // "http://wx.qlogo.cn/mmopen/Q3auHgzwzM5TeAQ9BMdRTAaQKicicodQ2GEal7dPL1TXwlAgQnNSJAHqcCyR5qchyKoTwpn654VgVGNiaCC5Eff3w/0";
        // byte[] btImg = getImageFromNetByUrl(url);
        // String fileName = "00.gif";
        // if (null != btImg && btImg.length > 0)
        // {
        // System.out.println("读取到：" + btImg.length + " 字节");
        // writeImageToDisk(btImg, "c:\\image\\", fileName);
        // }
        // else
        // {
        // System.out.println("没有从该连接获得内容");
        // }
        //
        // File file2 = new File("c:\\image\\", fileName);
        // ByteArrayOutputStream out = new ByteArrayOutputStream();
        // BufferedImage bufferedImage = Thumbnails.of(file2).scale(0.5)
        // .outputQuality(0.5).asBufferedImage();
        //
        // boolean flag = ImageIO.write(bufferedImage, "gif", out);
        // byte[] b = out.toByteArray();
        // Thumbnails.of(file2).size(300, 400).outputQuality(0.6)
        // .toFile("c:\\image\\name00.gif");
        //
        // System.out.println("压缩前的图片：" + Encodes.encodeBase64(btImg));
        // System.out.println("压缩后的图片：" + Encodes.encodeBase64(b));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage bi = Thumbnails.of(new File("c:\\image\\00.gif"))
                .scale(0.06).outputQuality(0.05)
                .asBufferedImage(); 
        ImageIO.write(bi, "bmp", out);
        System.out.println("压缩后的图片：" + Encodes.encodeBase64(out.toByteArray()));

    }

    /**
     * @Title: writeImageToDisk
     * @Description: 保存Image到本地磁盘
     * @author administrator 2016年7月5日 下午1:29:48
     * @param img
     *            图片数据流
     * @param savePath
     *            文件保存地址
     * @param fileName
     *            文件保存时的名称
     * @throws
     */
    public static String writeImageToDisk(byte[] img, String savePath,
            String fileName)
    {
        String imgPath = null;
        try
        {
            File file = new File(savePath);
            if (!file.exists())
            {
                file.mkdirs();
            }
            imgPath = String.format(file.getPath() + "%s" + fileName, "\\");
            FileOutputStream fops = new FileOutputStream(imgPath);
            fops.write(img);
            fops.flush();
            fops.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return imgPath;
    }

    /**
     * @Title: getImageFromNetByUrl
     * @Description: 根据URL地址获得数据的字节流
     * @author administrator 2016年7月5日 上午11:48:25
     * @param strUrl
     *            网络连接地址
     * @return
     * @throws
     */
    public static byte[] getImageFromNetByUrl(String strUrl)
    {
        try
        {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Title: readInputStream
     * @Description: 从输入流中获取数据
     * @author administrator 2016年7月5日 上午11:48:07
     * @param inStream
     *            输入流
     * @return
     * @throws Exception
     * @throws
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1)
        {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * @Title: download
     * @Description:从URL地址下载图片并重命名
     * @author administrator 2016年7月5日 下午1:32:15
     * @param urlString
     *            URL地址
     * @param filename
     *            文件名
     * @param savePath
     *            保存地址
     * @throws Exception
     * @throws
     */
    public static void download(String urlString, String filename,
            String savePath) throws Exception
    {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists())
        {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
        // 开始读取
        while ((len = is.read(bs)) != -1)
        {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }
}
