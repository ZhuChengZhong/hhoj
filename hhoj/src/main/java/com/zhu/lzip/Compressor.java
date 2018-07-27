package com.zhu.lzip;

public interface Compressor {
	/**
	 * 压缩
	 * @param sources
	 * @return
	 */
	public byte[] compress(byte[] sources);
	
	/**
	 * 解压缩
	 * @param sources
	 * @return
	 */
	public byte[] decompress(byte[] sources);
}
