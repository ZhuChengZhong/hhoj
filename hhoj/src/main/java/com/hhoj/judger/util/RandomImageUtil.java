package com.hhoj.judger.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public final class RandomImageUtil {

	private static String randomcode;
	
	private RandomImageUtil() { };
	public static BufferedImage createRandImage(int width,int height){  //创建验证码图片
		Graphics2D g2d=(Graphics2D)new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB).getGraphics();
		//创建背景透明的图片
		BufferedImage image=g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d=image.createGraphics();
		Random random=new Random();
		Color color;
		for(int i=0;i<200;i++){      //画200个小圆点用来干扰读取验证码
			int x=random.nextInt(width);
			int y=random.nextInt(height);
			color=new Color(random.nextInt(120)+130,random.nextInt(120)+130,random.nextInt(120)+130);
			g2d.setColor(color);
			g2d.fillOval(x, y, 2, 2);
		}
		g2d.setFont(new Font("宋体",1, 30));
		for(int i=0;i<4;i++){
			int x=random.nextInt(10);   //随机产生验证码
			randomcode+=x;
			color=new Color(random.nextInt(130),random.nextInt(130),random.nextInt(130));
			g2d.setColor(color);
			g2d.drawString(x+"",8+i*21,30);
		}
		g2d.dispose();
		return image;
	}
	
    public static String getRandomcode() {
		return randomcode;
	}
	
//先用一个main函数测试能后生成我们需要的图片
   public static void main(String[] args) {
		BufferedImage image=(BufferedImage)createRandImage(100, 40);
		File file=new File("/home/zhu/Desktop/my.jpg");
		
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
