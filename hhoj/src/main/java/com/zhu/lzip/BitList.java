package com.zhu.lzip;

import java.util.Arrays;
/**
 * 存放bit位的list
 * @author zhu
 *
 */
public class BitList {
	
	private int capcity;
	
	private int size;
	
	private byte[] bitkeep;
	
	private static final int MASK=7;
	
	private static final int DEFAULT_CAPCITY=8*2;
	
	public BitList(byte[] bs) {
		this.capcity=bs.length*8;
		size=capcity;
		bitkeep=bs;
	}
	
	public BitList(int count) {
		if(count<0) {
			throw new RuntimeException("capcity must big than 0");
		}
		capcity=(count&MASK)==0?count>>3:((count>>3)+1);
		capcity<<=3;
		bitkeep=new byte[capcity>>3];
	}
	
	public BitList(){
		this(DEFAULT_CAPCITY);
	}
	
	/**
	 * 添加bit�?
	 * @param b true 表示插入1 否则 0
	 */
	public void add(boolean b) {
		ensureCapacity(size+1);
		if(b) {
			int index=size>>3;
			int pos=7-(size&MASK);
			bitkeep[index]|=(1<<pos);
		}
		size++;
	}
	
	
	
	/**
	 * 添加�?个byte�?8bit
	 * @param b
	 */
	public void addByte(byte b) {
		for(int i=7;i>=0;i--) {
			if((b&(1<<i))!=0) {
				add(true);
			}else {
				add(false);
			}
		}
	}
	
	
	
	/**
	 * 添加指定位数的bit
	 * @param b
	 */
	public void add(int n,int len) {
		for(int i=len-1;i>=0;i--) {
			if((n&(1<<i))!=0) {
				add(true);
			}else {
				add(false);
			}
		}
	}
	
	
	
	
	/**
	 * 将只�?01的字符串添加进列�?
	 * @param s
	 */
	public void addStr(String s){
		for(int i=0;i<s.length();i++){
			char c=s.charAt(i);
			if(c=='1'){
				add(true);
			}else if(c=='0'){
				add(false);
			}else{
				throw new RuntimeException("the s can only contains 0 or 1");
			}
		}
	}
	
	/**
	 * 添加int
	 * @param n
	 */
	public void addInt(int n){
		for(int i=31;i>=0;i--) {
			if((n&(1<<i))!=0) {
				add(true);
			}else {
				add(false);
			}
		}
	}
	
	/**
	 * 添加byte数组
	 * @param arr
	 */
	public void addBytes(byte[] arr){
		for(int i=0;i<arr.length;i++){
			addByte(arr[i]);
		}
	}
	
	/**
	 * 从指定位置获取一个byte
	 * @param begin
	 * @return
	 */
	public byte getByte(int begin) {
		if(begin+7>=size) {
			throw new IndexOutOfBoundsException();
		}
		byte res=0;
		for(int i=begin;i<=begin+7;i++) {
			if(get(i)) {
				res|=1<<(7-i+begin);
			}
		}
		return res;
	}
	
	/**
	 * 获取指定位置的bit位值
	 */
	public boolean get(int i) {
		if(i<0||i>=size) {
			throw new IndexOutOfBoundsException();
		}
		int index=i>>3;
		int pos=7-(i&MASK);
		byte bt=(byte)(1<<pos);
		return (bitkeep[index]&bt)!=0;
	}
	
	/**
	 * 从指定位置获取长度为len的int
	 */
	public int get(int pos,int len) {
		if(pos<0||pos+len>size) {
			throw new IndexOutOfBoundsException(pos+"");
		}
		int res=0;
		for(int i=0;i<len;i++) {
			if(get(pos+i)) {
				res=(res<<1)+1;
			}else {
				res=res<<1;
			}
		}
		return res;
	}
	
	
	/**
	 * 从指定位置获取int
	 * @param pos
	 * @return
	 */
	public int getInt(int pos){
		return get(pos, 32);
	}
	
	
	/**
	 * 获取byte数组
	 * @param pos
	 * @param size
	 * @return
	 */
	public byte[] getBytes(int pos,int size){
		byte[] res=new byte[size];
		for(int i=0;i<size;i++){
			res[i]=getByte(pos);
			pos+=8;
		}
		return res;
	}
	
	/**
	 * 获取底层byte数组
	 * @return
	 */
	public byte[] toBytes() {
		int len=(size&MASK)==0?(size>>3):(size>>3)+1;
		return Arrays.copyOf(bitkeep, len);
	}
	
	/**
	 * �?查容�?
	 * @param size
	 */
	private void ensureCapacity(int size) {
		if(size>=capcity) {
			grow(capcity);
		}
	}

	/**
	 * 扩容
	 * @param capcity
	 */
	private void grow(int capcity) {
		this.capcity=capcity<<1;
		this.bitkeep=Arrays.copyOf(this.bitkeep, this.capcity/8);
	}

	/**
	 * 获取元素个数
	 * @return
	 */
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		return "BitList [capcity=" + capcity + ", size=" + size + ", bitkeep=" + Arrays.toString(bitkeep) + "]";
	}
	
	class BitQueue{
		int pos;
		
		public BitQueue(){
			pos=0;
		}
		
		public boolean pollBit(){
			return get(pos++);
		}
		
		public byte pollByte(){
			byte res=getByte(pos);
			pos+=8;
			return res;
		}
		
		public int pollInt(){
			int res=getInt(pos);
			pos+=32;
			return res;
		}
		
		public int pollInt(int len){
			int res=get(pos, len);
			pos+=len;
			return res;
		}
		
		public boolean hasNext(){
			return pos<size;
		}
		
		public byte[] pollBytes(int len){
			byte[] res=getBytes(pos, len);
			pos+=8*len;
			return res;
		}
	}
}
