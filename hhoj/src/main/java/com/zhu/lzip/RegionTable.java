package com.zhu.lzip;

public class RegionTable {
	
	private static final int MAX_OFF_SET=32768;
	
	private static final int MAX_LEN=255;
	

	
	private static int[] offsetTable={
			1,		2,		3,		4,		5,		7,
			9,		13,		17,		25,		33,		49,
			65,		97,		129,	193, 	257,	385,
			513,	769,	1025,	1537,	2049,	3073,
			4097,	6145,	8193,	12289,	16385,	24577
	};
	
	private static int[] lenTable = {
			3,		 4,		 5,		 6,		 7,		 8,		 9,
			10,		 11,	 13,	 15,	 17,	 19,	 23,
			27,		 31,	 35,	 43,	 51,	 59,	 67,
			83,		 99,	 115,	 131,	 163,	 195,	 227, 	258
			};

	/**
	 * 获取偏移量对应的区间值
	 * @param offset
	 * @return
	 */
	public static int getOffsetRegion(int offset){
		if(offset<=0||offset>MAX_OFF_SET){
			throw new RuntimeException("offset must in (0~32768)");
		}
		return binarySearch(offsetTable, offset);
	}
	
	/**
	 * 获取指定offset的追加编码
	 * @param offset
	 * @return
	 */
	public static String getOffsetBitCode(int offset){
		int region=getOffsetRegion(offset);
		int len=getOffsetBitLen(region);
		if(len==0){
			return "";
		}
		int leftOffset=offsetTable[region];
		int rank=offset-leftOffset;
		return toStr(rank, len);
	}
	
	/**
	 * 获取指定len的追加编码
	 * @param len
	 * @return
	 */
	public static String getLenBitCode(int len){
		int region=getLenRegion(len);
		int l=getLenBitLen(region);
		if(l==0){
			return "";
		}
		int left=lenTable[region];
		int rank=len-left;
		return toStr(rank,l);
	}
	
	
	/**
	 * 获取指定区间需要附加的编码长度
	 * @param region
	 * @return
	 */
	public static int getOffsetBitLen(int region){
		if(region>=0&&region<=3){
			return 0;
		}
		return (region-2)/2;
	}
	
	/**
	 * 获取指定区间需要附加的编码长度
	 * @param region
	 * @return
	 */
	public static int getLenBitLen(int region){
		int len=lenTable[region+1]-lenTable[region];
		int res=0;
		while(len>1){
			res++;
			len=len>>1;
		}
		return res;
	}
	
	/**
	 * 将rank转换为长度为len的01字符串
	 * @param rank
	 * @param len
	 * @return
	 */
	private static String toStr(int rank,int len){
		StringBuilder sb=new StringBuilder();
		for(int i=len-1;i>=0;i--){
			int temp=rank&(1<<i);
			if(temp>0){
				sb.append('1');
			}else{
				sb.append('0');
			}
		}
		return sb.toString();
	}
	
	/**
	 * 二分查找返回第一个小于等于num的
	 * 数的下标
	 * @param arr
	 * @param num
	 * @return
	 */
	private static int binarySearch(int[] arr,int num){
		int start=0,end=arr.length-1;
		while(start<end){
			int mid=(start+end+1)>>1;
			if(arr[mid]>num){
				end=mid-1;
			}else{
				start=mid;
			}
		}
		return start;
	}

	/**
	 * 通过区域和区域内排名获取offset
	 * @param region
	 * @param rank
	 * @return
	 */
	public static int getOffset(int region,int rank){
		return offsetTable[region]+rank;
	}
	
	/**
	 * 通过区域和区域内排名获取len
	 * @param region
	 * @param rank
	 * @return
	 */
	public static int getLen(int region,int rank){
		return lenTable[region]+rank;
	}
	
	/**
	 * 获取编码长度对应的区间值
	 * @param offset
	 * @return
	 */
	public static int getLenRegion(int len){
		if(len<=0||len>MAX_OFF_SET){
			throw new RuntimeException("offset must in (0~255)");
		}
		return binarySearch(lenTable, len);
	}
}
