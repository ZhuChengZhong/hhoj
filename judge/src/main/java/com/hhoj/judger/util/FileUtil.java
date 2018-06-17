package com.hhoj.judger.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 文件处理类
 * 
 * @author zhu
 *
 */
public final class FileUtil {
	public static final String separator = "/";
	
	private static MessageDigest digest;
	
	static {
		try {
			digest=MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private FileUtil() {

	}

	/**
	 * 创建新文件并将指定字符串写入文件内
	 * 
	 * @param filePath
	 * @param data
	 */
	public static boolean createFile(String filePath, String data) {
		if (StringUtil.isEmpty(filePath)) {
			return false;
		}
		PrintWriter pw = null;
		try {
			File file = new File(filePath);
			File father=file.getParentFile();
			if(!father.exists()) {
				father.mkdirs();
			}
			pw = new PrintWriter(file);
			pw.println(data);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param dirPath
	 * @return
	 */
	public static boolean mkdir(String dirPath) {
		if (StringUtil.isEmpty(dirPath)) {
			return false;
		}
		File file = new File(dirPath);
		if (file.exists()) {
			return file.isDirectory();
		}
		return file.mkdirs();
	}

	/**
	 * 读取文件内容并以字符串的形式返回
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getCharsFromFile(String fileName) {
		if (StringUtil.isEmpty(fileName)) {
			return "";
		}
		BufferedReader reader = null;
		StringBuilder result = new StringBuilder();
		try {
			File file = new File(fileName);
			InputStreamReader ir = new InputStreamReader(new FileInputStream(file));
			reader = new BufferedReader(ir);
			String s = null;

			while ((s = reader.readLine()) != null) {
				result.append(s+"\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		result.deleteCharAt(result.length()-1);
		return result.toString();
	}

	/**
	 * 
	 * 比较两个文件内容是否相同
	 */
	public static boolean compare(String standardOutPath, String userOutPath) {
		if (StringUtil.isEmpty(standardOutPath) || StringUtil.isEmpty(userOutPath)) {
			return false;
		}
		File standardOutFile=new File(standardOutPath);
		File userOutFile=new File(userOutPath);
		if(standardOutFile.length()!=userOutFile.length()) {
			return false;
		}
		return compareFileUseDigest(standardOutPath, userOutPath);
	}

	public static boolean rmdir(String dir) {
		if (StringUtil.isEmpty(dir)) {
			return false;
		}
		File file = new File(dir);
		if (file.exists()) {
			file.delete();
		}
		return true;
	}
	public static String getFileNameFromPath(String filePath) {
		String res=filePath.substring(filePath.lastIndexOf("/")+1);
		int r=res.indexOf(".");
		if(r<0) {
			return null;
		}
		return res.substring(0,r);
	}
	public static String getFileDir(String filePath) {
		if(filePath==null) {
			return null;
		}
		int index=filePath.lastIndexOf("/");
		if(index>0) {
			return filePath.substring(0, filePath.lastIndexOf("/"));
		}
		return null;
	}
	public static String getFileMD5(String fileName) throws Exception {
		File file=new File(fileName);
		FileInputStream in=new FileInputStream(file);
		byte[]buffer=new byte[1024];
		int len=0;
		while((len=in.read(buffer))!=-1) {
			digest.update(buffer, 0, len);
		}
		in.close();
		byte[]bs=digest.digest();
		return convertBytesToString(bs);
	}
	public static boolean compareFileUseDigest(String standardOutPath, String userOutPath) {
		try {
			String digest1=getFileMD5(standardOutPath);
			String digest2=getFileMD5(userOutPath);
			if(digest1.equals(digest2)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	public static String convertBytesToString(byte[]bs) {
		char[]arr={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		StringBuilder sb=new StringBuilder();
		byte mask=0X0F;
		for(int i=0;i<bs.length;i++) {
			byte b=(byte) ((bs[i]>>>4)&mask);
			sb.append(arr[b]);
			b=(byte) (bs[i]&mask);
			sb.append(arr[b]);
		}
		return sb.toString();
	}
	
	public static void testCompare() {
		long start,cost;
		
		start=System.currentTimeMillis();
		for(int i=0;i<1000;i++) {
			compare("/home/zhu/judger/output/1/7.out", "/home/zhu/judger/output/1/7.out");
		}
		cost=System.currentTimeMillis()-start;
		System.out.println(cost);
	}
	public static void main(String[] args) throws NoSuchAlgorithmException  {
		testCompare();
	}
}
