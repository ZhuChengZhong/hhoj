package com.zhu.lzip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class HuffmanTree  {
	//结束标记
	public static final byte END=(byte)0XFF;
	//无用的空节点标记
	public static final byte EMPTY_NODE_VAL=(byte)0XFE;
	//NULL
	public static final byte NULL_NODE_VAL=(byte)0XFD;
	//分隔标记
	public static final byte SPLIT=(byte)0XFC;
	
	private int nodeValLen;
	
	private int index;
	
	static class TreeNode implements Comparable<TreeNode>{
		TreeNode left;
		TreeNode right;
		int val;
		int count;
		public TreeNode(TreeNode left, TreeNode right, int val, int count) {
			super();
			this.left = left;
			this.right = right;
			this.val = val;
			this.count = count;
		}
		@Override
		public int compareTo(TreeNode o) {
			if(this.count<o.count) {
				return -1;
			}else if(this.count>o.count) {
				return 1;
			}
			return 0;
		}
	}
	TreeNode root;
	
	
	public HuffmanTree(int nodeValLen) {
		this.nodeValLen=nodeValLen;
	}

	/**
	 * 通过优先级队列构建霍夫曼树
	 * @param queue
	 */
	public void buildTree(PriorityQueue<TreeNode> queue) {
		while(queue.size()>1) {
			TreeNode first=queue.poll();
			TreeNode second=queue.poll();
			TreeNode newNode=new TreeNode(first,second,EMPTY_NODE_VAL,first.count+second.count);
			queue.add(newNode);
		}
		if(queue.size()==1) {
			root=queue.poll();
		}
	}
	
	public HuffmanTree(Map<Integer,Integer>map,int nodeValLen) {
		this(nodeValLen);
		PriorityQueue<TreeNode>queue=new PriorityQueue<>();
		Set<Entry<Integer, Integer>>set=map.entrySet();
		for(Entry<Integer, Integer>entry:set) {
			TreeNode node=new TreeNode(null, null, entry.getKey(), entry.getValue());
			queue.add(node);
		}
		buildTree(queue);
	}


	/**
	 * 通过先序遍历序列化哈夫曼树
	 * @return
	 */
	public byte[] serialize() {
		BitList bitList=new BitList(100);
		Stack<TreeNode>stack=new Stack<>();
		if(root==null) {
			return new byte[]{};
		}
		stack.add(root);
		while(!stack.isEmpty()) {
			TreeNode node=stack.pop();
			if(!isLeaf(node)) {
				bitList.add(false);
				stack.push(node.right);
				stack.push(node.left);
			}else {
				bitList.add(true);
				bitList.add(node.val, nodeValLen);
			}
		}
		return bitList.toBytes();
	}
	
	/**
	 * 通过先序遍历重建哈夫曼树
	 * @param bs
	 */
	public void deserialize(byte[] bs) {
		if(bs==null||bs.length<1) {
			return ;
		}
		index=0;
		root=build(new BitList(bs));
	}
	private TreeNode build(BitList list) {
		boolean b=list.get(index++);
		if(b) {
			int val=list.get(index,nodeValLen);
			index+=nodeValLen;
			TreeNode node=new TreeNode(null,null, val,0);
			return node;
		}
		TreeNode left=build(list);
		TreeNode right=build(list);
		TreeNode root=new TreeNode(left, right,0,0);
		return root;
	}
	
	/**
	 * 判断树节点是否为叶子节点
	 * @param node
	 * @return
	 */
	public static boolean isLeaf(TreeNode node) {
		if(node!=null&&node.left==null&&node.right==null) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		TreeNode node1=new TreeNode(null,null,1,1);
		TreeNode node2=new TreeNode(null,null,2,2);
		TreeNode node3=new TreeNode(null,null,3,3);
		TreeNode node4=new TreeNode(null,null,4,4);
		PriorityQueue<TreeNode>queue=new PriorityQueue<>();
		queue.add(node4);
		queue.add(node3);
		queue.add(node2);
		queue.add(node1);
		HuffmanTree tree=new HuffmanTree(4);
		tree.buildTree(queue);
		byte[]bs=tree.serialize();
		System.out.println(Arrays.toString(bs));
		HuffmanTree newTree=new HuffmanTree(4);
		newTree.deserialize(bs);
		System.out.println(Arrays.toString(newTree.serialize()));
	}
}
