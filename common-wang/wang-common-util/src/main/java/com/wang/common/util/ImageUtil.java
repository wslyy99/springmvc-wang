/**  
 * @Title:  ImageUtils.java
 * @Package com.dowin.utils.common
 * @Description: TODO(用一句话描述该文件做什么)
 * @author administrator
 * @date  2016年7月29日 上午12:41:15
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
    // 缩放类型
    public final static int SCALETYPE_WIDTH = 1;
    public final static int SCALETYPE_HEIGHT = 2;
    public final static int SCALETYPE_MAX = 3;
    public final static int SCALETYPE_MIN = 4;
    
    
    /**
     * 获取图片的宽度和高度尺寸
     * 
     * @param img
     * @return
     */
    public static int[] getSize(File img){
        BufferedImage image = null;
        try {
            image = ImageIO.read(img);
        } catch (IOException e) {
            e.printStackTrace();
        }  
        int width =  image.getWidth();
        int height = image.getHeight();
        int[] size = {width,height};
        return size;
    }
    
    /**
     * 功能：图片缩放
     * 
     * @param source
     *            原图像
     * @param target
     *            目标图像
     * @param width
     *            缩放后的图片宽度
     * @param height
     *            缩放后的图片高度
     * @param scaleType
     *            缩放类型，是按高度，还是按宽度，是以缩放最小为准还是以缩放最大为准
     * @param forceAble
     *            当图片大小小于规定的尺寸，是否强制将图片放大失真
     * @param quality
     */
    public static void scale(File source,File target,int width,int height,int scaleType,boolean forceAble,float quality){
        // 按比例缩放
        int size[] = getSize(source);
        int w = size[0];
        int h = size[1];
        double wScale = getScale(width, w);
        double hScale = getScale(height,h);
        double scale = 1.0;
        //System.out.println(width+"--"+height+"--"+w+"--"+h);
        /*
         *  width      height
         * -----   =  --------
         *   w           h
         */
        switch (scaleType) {
            case SCALETYPE_WIDTH:
                scale = wScale;
                break;
            case SCALETYPE_HEIGHT:
                scale = hScale;
                break;
            case SCALETYPE_MIN:
                scale = Math.min(wScale,hScale);
                break;
            case SCALETYPE_MAX:
                scale = Math.max(wScale, hScale);   
                break;
            default:
                scale = Math.min(wScale, hScale);
                break;
        }
        try{
            if(!target.getParentFile().exists()){
                target.getParentFile().mkdirs();
            }
            // 如果按照大于1的比例进行缩放（其实是放大），这里会根据参数决定forceAble决定
            if(scale<=1 || scale > 1 && forceAble){
                // 缩放原图
                Thumbnails.of(source).width(width).outputQuality(quality).toFile(target);
            }
            else{
                // 复制原图
                Thumbnails.of(source).scale(1).outputQuality(quality).toFile(target);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    /**
     * 功能：自定义位置图片剪裁,案例：头像剪裁
     * 
     * @param source
     *            原图像
     * @param target
     *            目标图像
     * @param width
     *            剪裁后的宽度
     * @param height
     *            剪裁后的高度
     * @param posX
     *            位置x
     * @param posY
     *            位置y
     * @param scaleWidth
     *            缩放后的图像宽度
     * @param quality
     *            剪裁后的图片质量
     */
    public static void clip(File source,File target,int width,int height,int posX,int posY,int scaleWidth,float quality){
        int size[] = getSize(source);
        int w = size[0];
        // scale为1.0表示原图剪裁，否则就缩放(扩大)
        double scale = (scaleWidth > 0) ? getScale(scaleWidth , w) : 1.0;
        try {
            if(!target.getParentFile().exists()){
                target.getParentFile().mkdirs();
            }
            // 先按比例缩放存到内存
            BufferedImage image = Thumbnails.of(source).scale(scale).asBufferedImage();
            // 后从中心剪裁
            Thumbnails.of(image).size(width,height).sourceRegion(posX,posY, width, height).outputQuality(quality).toFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }
    
    
    /**
     * 从中心剪裁图片
     * 
     * @param source
     * @param target
     * @param width
     * @param height
     * @param scaleAble
     *            是否先缩放后剪裁
     */
    public static void clipBySize(File source,File target,int width,int height,boolean scaleAble){
        int size[] = getSize(source);
        int w = size[0];
        int h = size[1];
        double wScale = getScale(width, w);
        double hScale = getScale(height, h);
        double maxScale = Math.max(wScale, hScale);
        double scale = scaleAble ? maxScale : 1.0;
        if(!target.getParentFile().exists()){
            target.getParentFile().mkdirs();
        }
        try {
            // 先按比例缩放存到内存
            BufferedImage image = Thumbnails.of(source).scale(scale).asBufferedImage();
            // 后从中心剪裁
            Thumbnails.of(image).size(width, height).sourceRegion(Positions.CENTER, width, height).outputQuality(1.0f).toFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 根据宽度缩放
     * 
     * @param source
     * @param target
     * @param width
     */
    public static void scaleByWidth(File source,File target,int width){
        scale(source, target, width, 0, ImageUtil.SCALETYPE_WIDTH, false, 1.0f);
    }
    
    /**
     * 根据高度缩放
     * 
     * @param source
     * @param target
     * @param height
     */
    public static void scaleByHeight(File source,File target,int height){
        scale(source, target, 0, height, ImageUtil.SCALETYPE_HEIGHT, false, 1.0f);
    }
    
    /**
     * 剪裁成正方形
     * 
     * @param source
     * @param target
     * @param width
     */
    public static void clip(File source,File target,int width){
        clipBySize(source, target, width,width,true);
    }

    private static double getScale(double num1,double num2){
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        double result = b1.divide(b2, 3,BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
   
    public static byte[] image2Bytes(String imgSrc) throws Exception
    {
        FileInputStream fin = new FileInputStream(new File(imgSrc));
        // 可能溢出,简单起见就不考虑太多,如果太大就要另外想办法，比如一次传入固定长度byte[]
        byte[] bytes = new byte[fin.available()];
        // 将文件内容写入字节数组，提供测试的case
        fin.read(bytes);
        fin.close();
        return bytes;
    }


    public static void main(String[] args) throws Exception
    {
        // File file = new File("c:\\image\\00.gif");
        // File target = new File("c:\\image\\target.gif");
        // // 将图片按照300的宽度比进行缩放，设置高度不会影响结果
        // // ImageUtil.scale(file, target, 300, 400, ImageUtil.SCALETYPE_WIDTH,
        // // false, 1.0f);
        // // 将图片按照400的宽度比进行缩放，设置宽度不会影响结果
        // ImageUtil.scale(file, target, 80, 80, ImageUtil.SCALETYPE_MIN,
        // false, 1.0f);

        // System.out.println("压缩后："
        // + Encodes.encodeBase64(image2Bytes("c:\\image\\target.gif")));
        File file = new File("c:\\image\\00.bmp");
        if (file.exists() && file.isFile())
        {
            file.delete();
        }

    }
}
