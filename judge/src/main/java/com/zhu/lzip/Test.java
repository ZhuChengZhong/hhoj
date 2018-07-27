package com.zhu.lzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Test {
	public static void test(String sourceFile,String target,Compressor compressor) throws Exception{
		File file=new File(sourceFile);
		FileInputStream in=new FileInputStream(file);
		byte[]bs=new byte[(int) file.length()];
		int lenA=bs.length;
		in.read(bs);
		byte[]b=compressor.compress(bs);
		int lenB=b.length;
		byte[]sources=compressor.decompress(b);
		File fileb=new File(target);
		FileOutputStream out=new FileOutputStream(fileb);
		out.write(sources);
		in.close();
		out.close();
		int rate=lenB*10000/lenA;
		System.out.println(rate*1.0/100);
	}
	public static void main(String[] args) throws Exception {
	//	Compressor cp=new HFMHelper();
		//Compressor cp=new LZWCompressor();
		Compressor cp=new LzipCompressor();
	//	String sourceFile="e://test.java";
		//String target="e://test-copy.java";
		String sourceFile="e://12345.txt";
		String target="e://12345-copy.txt";
		test(sourceFile, target, cp);
	}
	//27, 100, 95, -118, -31, 36, 68, 115, 75, 72, -78, -90, -104, 13, 107, -35, -102, -44, -12, 0
}
