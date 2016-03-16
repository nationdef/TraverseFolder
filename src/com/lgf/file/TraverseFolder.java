package com.lgf.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lgf.file.model.FloderNode;

public class TraverseFolder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 递归显示C盘下所有文件夹及其中文件
//		String path = "D:/JudCorrect";
		String path ="C:/Users/Administrator/Desktop/2.0文档整理1.2";
		String name = "2.0文档整理1.2";
		File root = new File(path);

		FloderNode node = new FloderNode();
		try {
			node.setFileName(name);
			node.setLevel(0);
			node.setFolder(true);
			showAllFiles(root, node, 1);
			File file = new File(path + "/目录.txt");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileOutputStream fos = new FileOutputStream(file, true);
			showFloder(node, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	final static void showAllFiles(File dir, FloderNode node, int level) throws Exception {
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
					showAllFiles(fs[i], sonNode, level + 1);
				} catch (Exception e) {
				}
				sonNode.setFolder(true);
			} else {
				sonNode.setFolder(false);
				fs[i].length();
				sonNode.setModifeTime(fs[i].lastModified());
				sonNode.setSize(fs[i].length());
				sonNode.setHashValue(getMD5Checksum(fs[i]));

			}

			sonNodeList.add(sonNode);
		}
		node.setNode(sonNodeList);
	}

	private static void showFloder(FloderNode node, FileOutputStream fos) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd/HH:mm:ss");
		String content = "";
		for (int i = 0; i < node.getLevel(); i++) {
			System.out.print("  ");
			content += "  ";

		}
		// System.out.print("|");
		content += "|";
		// System.out.print("--");
		content += "--";
		// System.out.println(node.getFileName());
		content += node.getFileName();
		if (!node.isFolder()) {
//			content += "\t" + sdf.format(new Date(node.getCreateTime()));
			content += "\t" + sdf.format(new Date(node.getModifeTime()));
			content += "\t" +node.getHashValue();
		}
		content += "\r\n";
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
				showFloder(sonNode, fos);
			}
		}

	}

	public static byte[] createChecksum(File file) {
		InputStream fis = null;
		MessageDigest complete = null;
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			complete = MessageDigest.getInstance("MD5");
			int numRead;

			do {
				numRead = fis.read(buffer);
				if (numRead > 0) {
					complete.update(buffer, 0, numRead);
				}
			} while (numRead != -1);

			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return complete.digest();
	}

	public static String getMD5Checksum(File file) {
		byte[] b = createChecksum(file);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
}
