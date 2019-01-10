/*
 * Copyright (c) 2019 郭彩龙 All Rights Reserved.
 */
package com.gcl.common.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class CutImageUtil
{
    /**
     * 生成方形图片，如果要生成正方形图片，width和height需要相等
     * 
     * @param str
     *            传入的字符
     * @param width
     *            方形宽度
     * @param height
     *            方形长度
     * @param imagePath
     *            生成图片路径
     * @return
     * @throws Exception
     */
    public static String createRandomColorImage(String str, int width, int height, String imagePath) throws Exception
    {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        int[] colors =
        { 0xfffec831, 0xfff48d65, 0xffd6a7c9, 0xff92d1f4, 0xff99d88f, 0xffec89a5, 0xff7a7fb6, 0xffeb6d71, 0xffffb359, 0xff5fc2d2 };
        Color color = new Color(colors[Math.abs(str.hashCode() % colors.length)]);
        g2d.setBackground(color);
        g2d.clearRect(0, 0, width, height);
        ImageIO.write(bufferedImage, HandleImageUtil.IMAGE_FORMATE_PNG, new File(imagePath));
        g2d.dispose();
        return imagePath;
    }

    /**
     * 裁切成基础圆形，裁切的图像必须是正方形的 ，如果是长方形的，裁切则会变成椭圆形
     * 
     * @param length
     *            正方形边长
     * @param color
     *            颜色值，例如 ：0xff99d88f
     * @param imagePath
     *            生成图片路径
     * @return
     * @throws Exception
     */
    public static String createBasicCircularImage(int length, Paint color, String imagePath) throws Exception
    {
        File file = new File(imagePath);
        BufferedImage bufferedImage = new BufferedImage(length, length, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(color);
        g2d.fillOval(0, 0, length, length);
        g2d.dispose();
        ImageIO.write(bufferedImage, HandleImageUtil.IMAGE_FORMATE_PNG, file);
        return imagePath;
    }

}
