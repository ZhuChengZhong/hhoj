package com.zhu.lzip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.synth.Region;

import com.zhu.lzip.BitList.BitQueue;

/**
 * LZ77压缩算法实现
 * 
 * @author zhu
 *
 */
public class LzipCompressor extends AbstractCompressor {
	class Tuple {
		int offset;
		int len;

		public Tuple(int offset, int len) {
			this.offset = offset;
			this.len = len;
		}
	}

	/**
	 * 表示偏移量需要的位数
	 */
	private static final int MAX_OFFSET_LEN = 16;

	/**
	 * 滑动窗口大小
	 */
	private static final int MAX_WIN_SIZE = (1 << MAX_OFFSET_LEN) - 1;

	/**
	 * 最大匹配值(使用255不超过一字节，则长度都可用一字节表示)
	 */
	private static final int MAX_MATCH_LENGTH = 255;

	/**
	 * 最少匹配MIN_MATCH_LENGTH个字符才进行编码
	 */
	private static final int MIN_MATCH_LENGTH = 3;
	
	
	private static final int OFFSET_REGION_LEN=5;
	
	private static final int LITERAL_AND_LEN_REGION_LEN=9;
	
	private static final int LITERAL_COUNT=257;
	
	private static final int END=256;
	
	@Override
	public byte[] compress(byte[] sources) {

		if (sources == null || sources.length == 0) {
			throw new RuntimeException("the datas can't be compress");
		}
		
		BitList bitList = firstLz77Compress(sources);
		
		System.out.println("fisrt:"+bitList.size());
		
		BitList res = secondHfmCompress(bitList);

		System.out.println(res.size());
		return res.toBytes();
	}

	/**
	 * 使用LZ77算法进行第一次编码
	 * 
	 * @param sources
	 * @return
	 */
	private BitList firstLz77Compress(byte[] sources) {
		int winLeft = 0, winRight = -1; // 滑动窗口左右大小
		int len = sources.length;
		// 用来保存压缩后的数据
		BitList bitList = new BitList(sources.length * 8);
		HashMap<Integer, Integer> offsetMap = new HashMap<>();
		while (winRight < len - 1) {
			Tuple t = match(sources, winLeft, winRight);
			if (t.len >= MIN_MATCH_LENGTH) {
				writeTuple(bitList, t);
			} else {
				writeByte(bitList, sources[winRight + 1]);
			}
			winRight += t.len;
			winLeft = Math.max(winRight - MAX_WIN_SIZE + 1, winLeft);
		}
		return bitList;
	}

	/**
	 * 使用HFM算法进行二次编码
	 * 
	 * @param bitList
	 * @return
	 */
	private BitList secondHfmCompress(BitList bitList) {
		HuffmanTree[] trees = new HuffmanTree[2];
		createHfmTree(bitList, trees);
		BitList res = new BitList(bitList.size());
		byte[] offsetArr = trees[0].serialize();
		byte[] litAndLenArr=trees[1].serialize();
		HFMHelper h=new HFMHelper();
		int offsetArrLen = offsetArr.length;
		int litAndLenArrLen=litAndLenArr.length;
		// 记录offset对应的huffman编码树和长度
		res.addInt(offsetArrLen);
		res.addBytes(offsetArr);
		// 记录literal length 对应的huffman编码树和长度
		res.addInt(litAndLenArrLen);
		res.addBytes(litAndLenArr);
		System.out.println(Arrays.toString(litAndLenArr));  //
		HFMHelper helper = new HFMHelper();
		Map<Integer, String> offsetRegionCodeTable = helper.createEncodeTable(trees[0]);
		Map<Integer, String> litAndLenRegionCodeTable = helper.createEncodeTable(trees[1]);
		BitList.BitQueue queue=bitList.new BitQueue();
		while (queue.hasNext()) {
			if (!queue.pollBit()) {
				int b = queue.pollInt(8);
				String litEncodeStr=litAndLenRegionCodeTable.get(b);
				res.addStr(litEncodeStr);
			} else {
				int offset = queue.pollInt(MAX_OFFSET_LEN);
				int region=RegionTable.getOffsetRegion(offset);
				String offsetEncodeStr =offsetRegionCodeTable.get(region)+RegionTable.getOffsetBitCode(offset);
				int len = queue.pollInt(8);
				int lenRegion=RegionTable.getLenRegion(len);
				String lenEncodeStr=litAndLenRegionCodeTable.get(LITERAL_COUNT+lenRegion)+RegionTable.getLenBitCode(len);
				res.addStr(lenEncodeStr);
				res.addStr(offsetEncodeStr);
			}
		}
		//结束标志
		String endStr=litAndLenRegionCodeTable.get(END);
		res.addStr(endStr);
		return res;
	}

	/**
	 * 创建offset和len的哈夫曼树
	 * 
	 * @param bitList
	 * @param trees
	 */
	private void createHfmTree(BitList bitList, HuffmanTree[] trees) {
		HashMap<Integer, Integer> offsetRegionMap = new HashMap<>();
		HashMap<Integer,Integer> litAndLenRegionMap=new HashMap<>();
		BitList.BitQueue queue=bitList.new BitQueue();
		while (queue.hasNext()) {
			if (!queue.pollBit()) {
				int lit =queue.pollInt(8);
				addRecored(litAndLenRegionMap,lit);
			} else {
				int offset = queue.pollInt(MAX_OFFSET_LEN);
				int region=RegionTable.getOffsetRegion(offset);
				addRecored(offsetRegionMap, region);
				int len = queue.pollInt(8);
				addRecored(litAndLenRegionMap, RegionTable.getLenRegion(len)+LITERAL_COUNT);
			}
		}
		//结束标志编码
		addRecored(litAndLenRegionMap, END);
		trees[0] = new HuffmanTree(offsetRegionMap, OFFSET_REGION_LEN);
		trees[1]=new HuffmanTree(litAndLenRegionMap,LITERAL_AND_LEN_REGION_LEN);
	}
	
	/**
	 * 将map中key对应的value加1
	 * @param map
	 * @param key
	 */
	private void addRecored(Map<Integer,Integer>map,int key){
		if (map.containsKey(key)) {
			map.put(key, map.get(key) + 1);
		} else {
			map.put(key, 1);
		}
	}
	
	/**
	 * 已编码byte前用bit位1标识
	 * 
	 * @param bitList
	 * @param b
	 */
	private void writeTuple(BitList bitList, Tuple tuple) {
		bitList.add(true);
		int cur = 1 << MAX_OFFSET_LEN - 1;
		int offset = tuple.offset;
		while (cur > 0) {
			if ((offset & cur) != 0) {
				bitList.add(true);
			} else {
				bitList.add(false);
			}
			cur = cur >> 1;
		}
		bitList.addByte((byte) tuple.len);
	}

	/**
	 * 未编码byte前用bit位0标识
	 * 
	 * @param bitList
	 * @param b
	 */
	private void writeByte(BitList bitList, byte b) {
		bitList.add(false);
		bitList.addByte(b);
	}

	/**
	 * 寻找未编码数据 与滑动窗口内数据的最长匹配
	 * 
	 * @param sources
	 * @param winLeft
	 * @param winRight
	 * @return
	 */
	private Tuple match(byte[] sources, int winLeft, int winRight) {
		int beginPos = winRight + 1;
		int endPos = Math.min(beginPos + MAX_MATCH_LENGTH - 1, sources.length - 1);
		if (winRight == -1 || endPos - beginPos < MIN_MATCH_LENGTH - 1) {
			return new Tuple(0, 1);
		}
		int maxMatchOffset = 0, maxMatchLen = 1;
		for (int i = winLeft; i <= winRight; i++) {
			int len = 0;
			for (int j = beginPos; j <= endPos; j++) {
				if (sources[i + j - beginPos] != sources[j] || (j == endPos && ++len > 0)) {
					if (len >= MIN_MATCH_LENGTH && len >= maxMatchLen) {
						maxMatchLen = len;
						maxMatchOffset = beginPos - i;
					}
					break;
				}
				len++;
			}
		}
		Tuple tuple = new Tuple(maxMatchOffset, maxMatchLen);
		return tuple;
	}

	@Override
	public byte[] decompress(byte[] sources) {

		BitList bitList = firstUnCompressWithHfm(sources);

		return secondUnCompressWithLz77(bitList);
	}

	
	/**
	 * 使用Huffman算法进行首次解码
	 */
	private BitList firstUnCompressWithHfm(byte[] sources) {
		BitList bitList = new BitList(sources);
		BitQueue queue=bitList.new BitQueue();
		int offsetTreeLen = queue.pollInt();
		byte[] offsetTreeArr = queue.pollBytes(offsetTreeLen);
		HuffmanTree offsetTree = new HuffmanTree(OFFSET_REGION_LEN);
		offsetTree.deserialize(offsetTreeArr);
		int litAndLenLen = queue.pollInt();
		byte[] litAndLenArr = queue.pollBytes(litAndLenLen);
		HuffmanTree litAndLenTree = new HuffmanTree(LITERAL_AND_LEN_REGION_LEN);
		litAndLenTree.deserialize(litAndLenArr);
		BitList res = new BitList(bitList.size());
		HFMHelper helper = new HFMHelper();
		boolean isOffset=false;
		while (true) {
			if (isOffset) {
				int region = helper.encode(queue, offsetTree);
				int bitLen=RegionTable.getOffsetBitLen(region);
				int rank=queue.pollInt(bitLen);
				int offset=RegionTable.getOffset(region, rank);
				res.add(offset, MAX_OFFSET_LEN);
			} else {
				int bitOrLen=helper.encode(queue, litAndLenTree);
				if(bitOrLen>=LITERAL_COUNT){
					res.add(true);
					int lenRegion=bitOrLen-LITERAL_COUNT;
					int bitLen=RegionTable.getLenBitLen(lenRegion);
					int rank=queue.pollInt(bitLen);
					int len=RegionTable.getLen(lenRegion, rank);
					res.add(len, 8);
					isOffset=true;
					continue;
				}else if(bitOrLen==END){
					break;
				}else{
					res.add(false);
					res.add(bitOrLen,8);
				}
			}
			isOffset=false;
		}
		return res;
	}

	/**
	 * 使用Lz77算法进行二次解码
	 * 
	 * @param bitList
	 * @return
	 */
	private byte[] secondUnCompressWithLz77(BitList bitList) {
		List<Byte> res = new ArrayList<>();
		BitQueue queue=bitList.new BitQueue();
		while (queue.hasNext()) {
			// 以编码数据
			if (queue.pollBit()) {
				// 获取匹配长度和偏移量
				int l = queue.pollByte();
				int offset = queue.pollInt(MAX_OFFSET_LEN);
				decode(res, offset, l);
			} else {// 原数据
				byte b = queue.pollByte();
				res.add(b);
			}
		}
		return listToArray(res);
	}

	/**
	 * 从已解码数据中解码未解码数据
	 * 
	 * @param res
	 * @param offset
	 * @param len
	 */
	private void decode(List<Byte> res, int offset, int len) {
		int size = res.size();
		int start = size - offset;
		for (int i = start; i < start + len; i++) {
			res.add(res.get(i));
		}
	}

	/**
	 * 获取偏移量
	 * 
	 * @param bitList
	 * @param curPos
	 * @return
	 */
	private int getOffset(BitList bitList, int curPos) {
		int offset = 0;
		for (int i = 0; i < MAX_OFFSET_LEN; i++) {
			if (bitList.get(curPos++)) {
				offset = (offset << 1) + 1;
			} else {
				offset = offset << 1;
			}
		}
		return offset;
	}

	public static void main(String[] args) {
		byte[]bts={1,2,3,1};
		LzipCompressor compress=new LzipCompressor();
		byte[] bb=compress.compress(bts);
		System.out.println(Arrays.toString(bb));
		byte[] ss=compress.decompress(bb);
		System.out.println(Arrays.toString(ss));
		 
	}
}
