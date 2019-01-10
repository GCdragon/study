/*
 * Copyright (c) 2019 郭彩龙 All Rights Reserved.
 */
package com.gcl.common.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

public class HandleImageUtil
{
    public static final String IMAGE_FORMATE_PNG = "png";

    /**
     * 生成文字水印图片
     * 
     * @param str
     *            文字的内容
     * @param fontType
     *            字体，例如宋体
     * @param fontSize
     *            字体大小
     * @param color
     *            字体颜色，不带#号，例如"990033"
     * @param inputImagePath
     *            输入图片的路径
     * @param outputImagePath
     *            输出图片的路径
     * @throws Exception
     */

    public static String addTextToImage(String inputImagePath, String outputImagePath, String fontType, int fontSize, String str, String color) throws IOException
    {
        File file = new File(inputImagePath);
        Image image = ImageIO.read(file);
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        //保持透明背景色图片背景仍透明
        bi = g2.getDeviceConfiguration().createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
        g2 = bi.createGraphics();
        g2.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
        g2.setColor(new Color(Integer.parseInt(color, 16)));
        g2.setFont(new Font(fontType, Font.BOLD, fontSize));
        //设置字体抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fm = g2.getFontMetrics();
        int stringWidth = fm.stringWidth(str);
        int stringAscent = fm.getAscent();
        int stringDescent = fm.getDescent();
        g2.drawString(str, image.getWidth(null) / 2 - stringWidth / 2, image.getHeight(null) / 2 + (stringAscent - stringDescent) / 2);
        g2.dispose();
        ImageIO.write(bi, IMAGE_FORMATE_PNG, new FileOutputStream(outputImagePath));
        return outputImagePath;
    }

    public static byte[] readInputStream(FileImageInputStream inStream) throws Exception
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1)
        {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

}
