package com.zhu.lzip;

import java.util.Arrays;
import java.util.List;


public abstract class AbstractCompressor implements Compressor{
	/**
	 * 将字节数组分割成哈夫曼树部分与数据部分
	 * @param sources
	 * @return
	 */
	protected static byte[][] cut(byte[] sources){
		int splitIndex=0;
		for(int i=0;i<sources.length;i++) {
			if(sources[i]==HuffmanTree.SPLIT) {
				splitIndex=i;
				break;
			}
		}
		byte[][]res=new byte[2][];
		res[0]=Arrays.copyOfRange(sources, 2, splitIndex);
		res[1]=Arrays.copyOfRange(sources,splitIndex+1,sources.length);
		return res;
	}
	
	/**
	 * 列表转数组
	 * @param list
	 * @return
	 */
	protected static byte[] listToArray(List<Byte>list) {
		if(list==null) {
			return null;
		}
		byte[] bts=new byte[list.size()];
		for(int i=0;i<list.size();i++) {
			bts[i]=list.get(i);
		}
		return bts;
	}
	
	/**
	 * 连接两个byte数组
	 * @param btsA
	 * @param btsB
	 * @return
	 */
	protected static byte[] concat(byte[] btsA,byte[] btsB) {
		byte[]newBytes=new byte[btsA.length+btsB.length];
		System.arraycopy(btsA, 0, newBytes, 0, btsA.length);
		System.arraycopy(btsB, 0, newBytes, btsA.length, btsB.length);
		return newBytes;
	}
}
