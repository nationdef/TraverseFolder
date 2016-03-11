package com.lgf.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.lgf.file.model.FloderNode;

public class TraverseFolder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 递归显示C盘下所有文件夹及其中文件
		String path = "C:/Users/Administrator/Desktop/2.0文档整理1.1";
		String name = "2.0文档整理1.1";
		File root = new File("C:/Users/Administrator/Desktop/2.0文档整理1.1");
		FloderNode node = new FloderNode();
		try {
			node.setFileName(name);
			node.setLevel(0);
			node.setFolder(true);
			showAllFiles(root, node,1);
			File file = new File(path+"/目录.txt");
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileOutputStream fos = new FileOutputStream(file,true);
			showFloder(node,fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	final static void showAllFiles(File dir, FloderNode node,int level) throws Exception {
		File[] fs = dir.listFiles();
		List<FloderNode> sonNodeList = new ArrayList<FloderNode>();
		
		for (int i = 0; i < fs.length; i++) {
			FloderNode sonNode = new FloderNode();
			sonNode.setFileName(fs[i].getName());
			sonNode.setLevel(level);
			// System.out.println(fs[i].getName());
			// System.out.println(fs[i].getAbsolutePath());
			if (fs[i].isDirectory()) {
				try {
					showAllFiles(fs[i], sonNode,level+1);
				} catch (Exception e) {
				}
				sonNode.setFolder(true);
			} else {
				sonNode.setFolder(false);
			}

			sonNodeList.add(sonNode);
		}
		node.setNode(sonNodeList);
	}

	private static void showFloder(FloderNode node,FileOutputStream fos) {
		
		String content ="";
		for (int i = 0; i < node.getLevel(); i++) {
			System.out.print("  ");
			content +="  ";
 			
		}
			//System.out.print("|");
			content +="|";
//		System.out.print("--");
		content +="--";
//		System.out.println(node.getFileName());
		content +=node.getFileName();
		content +="\r\n";
		byte[] ba;
		try {
			System.out.println(content);
			ba = content.getBytes("utf-8");
			content = new String(ba);
			fos.write(content.getBytes());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<FloderNode> sonNodeList = node.getNode();
		if (node.isFolder()) {
			for (FloderNode sonNode : sonNodeList) {
				showFloder(sonNode,fos);
			}
		}
		
		
	}
	
	
}
