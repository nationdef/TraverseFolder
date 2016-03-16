package com.lgf.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.lgf.file.model.FloderNode;

/**
 * 比较两个文件目录下的文件是否大小一致
 * @author Administrator
 *
 */
public class ComparisonFileNote {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd/HH:mm:ss");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String class1path = "D:/gazx20160316105655.txt";
		String class2Path = "D:/gazx20160316115633.txt";
		
		

		Gson gson = new Gson();
		FloderNode node1 = gson.fromJson(readClassStrFormFile(class1path), FloderNode.class);
		FloderNode node2 = gson.fromJson(readClassStrFormFile(class2Path), FloderNode.class);
		changeState("kaishi", node1, node2);
	}

	private static void changeState(String path, FloderNode node1, FloderNode node2) {
		String getPath = path;
		// System.out.println(node1.getFileName());
		// System.out.println(node2.getFileName());
		if (node1.getFileName().equals(node2.getFileName())) {
			getPath += "/" + node1.getFileName();
		}
		 System.out.println("getPath:"+getPath);
		if (node1.isFolder() && node2.isFolder()) {
			 //文件夹不比较时间
			if (!(node1.getModifeTime() == node2.getModifeTime())) {
				System.out.println(getPath +"  "+node1.getFileName() +"  "+node2.getFileName()+ "文件修改时间不一致   原时间:" + sdf.format(new Date(node1.getModifeTime()))
						+ "   现时间:" + sdf.format(new Date(node2.getModifeTime())));
			}
			
			
			

		}
		if (!(node1.getSize() == node2.getSize())) {
			System.out.println(getPath +"  "+node1.getFileName() +"  "+node2.getFileName()+ "文件大小不一致   原大小:" + node1.getSize() + "   现大小:" + node2.getSize());
		}
		
		List<FloderNode> son1 = null;
		son1 = node1.getNode();
		List<FloderNode> son2 = null;
		son2 = node2.getNode();
		if (son1 != null && son2 != null) {
			if (!(son1.size() == son2.size())) {
				System.out.println(getPath + "子文件夹个数不一致   原个数:" + son1.size() + "   现个数:" + son2.size());
			}
			for (FloderNode sonnode1 : son1) {
				boolean hasValue = false;

				for (FloderNode sonnode2 : son2) {
					if (sonnode2.getFileName().equals(sonnode1.getFileName())) {
						// 存在则进行比较
						changeState(getPath, sonnode1, sonnode2);
						hasValue = true;
						break;
					}

				}
				if (!hasValue) {
					// 原来文件中有，后来的没有，则标识删除
					System.out.println(getPath + " 目录下删除了 " + sonnode1.getFileName());
				}
			}

			for (FloderNode sonnode4 : son2) {
				boolean hasValue = false;
				for (FloderNode sonnode3 : son1) {
					if (sonnode3.getFileName().equals(sonnode4.getFileName())) {
						// 存在上面的已经比较了，就不比较了
						hasValue = true;
						break;
					}

				}
				if (!hasValue) {
					// 新文件中有，原来的没有，则表示新增了
					System.out.println(getPath + " 目录下新增了 ：" + sonnode4.getFileName() + "  修改时间："
							+ sonnode4.getModifeTime() + " 文件大小" + sonnode4.getSize());
				}
			}
		}
	}

	private static String readClassStrFormFile(String filePath) {
		String contentStr = "";
		StringBuffer buffer = null;
		File file = new File(filePath);
		try {
			InputStream is = new FileInputStream(file);
			String line = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));

			line = reader.readLine(); // 读取第一行

			// while ((line = reader.readLine()) != null) {// 使用readLine方法，一次读一行
			// contentStr = contentStr + "\n" + line;
			// }
			reader.close();
			contentStr = line;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentStr;
	}

}
