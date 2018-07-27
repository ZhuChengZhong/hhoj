package com.zhu.lzip;

import java.util.HashMap;
import java.util.Map;

import com.zhu.lzip.BitList.BitQueue;
import com.zhu.lzip.HuffmanTree.TreeNode;


/**
 * HUffman算法实现压缩工具
 * 
 * @author zhu
 *
 */
public class HFMHelper{

	

	/**
	 * 编码
	 * 
	 * @param sources
	 * @param tree
	 * @return
	 */
	private  byte[] encode(byte[] sources, HuffmanTree tree) {
		BitList list = new BitList(sources.length * 8);
		Map<Integer, String> table = createEncodeTable(tree);
		for (int i = 0; i < sources.length; i++) {
			byte bt = sources[i];
			String s = table.get(bt);
			for (int j = 0; j < s.length(); j++) {
				if (s.charAt(j) == '1') {
					list.add(true);
				} else {
					list.add(false);
				}
			}
		}
		String s = table.get(HuffmanTree.END);
		for (int j = 0; j < s.length(); j++) {
			if (s.charAt(j) == '1') {
				list.add(true);
			} else {
				list.add(false);
			}
		}
		return list.toBytes();
	}

	/**
	 * 解码   返回解码后的数
	 * 
	 */
	public int encode(BitQueue queue,HuffmanTree tree){
		int res;
		TreeNode node=tree.root;
		while(true){
			if(tree.isLeaf(node)){
				res=node.val;
				return res;
			}else{
				if(queue.pollBit()){
					node=node.right;
				}else{
					node=node.left;
				}
			}
		}
		
	}
	
	/**
	 * 创建编码表
	 * 
	 * @param tree
	 * @return
	 */
	public Map<Integer, String> createEncodeTable(HuffmanTree tree) {
		Map<Integer, String> table = new HashMap<>();
		if(tree.root==null){
			return table;
		}
		StringBuilder sb = new StringBuilder();
		createEncodeTable(tree.root, table, sb);
		return table;
	}

	private void createEncodeTable(TreeNode root, Map<Integer, String> table, StringBuilder sb) {
		if (HuffmanTree.isLeaf(root)) {
			table.put(root.val, sb.toString());
			return;
		}
		sb.append('0');
		createEncodeTable(root.left, table, sb);
		sb.deleteCharAt(sb.length() - 1);
		sb.append('1');
		createEncodeTable(root.right, table, sb);
		sb.deleteCharAt(sb.length() - 1);
	}


}
