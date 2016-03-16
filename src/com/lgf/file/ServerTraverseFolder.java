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

import com.google.gson.Gson;
import com.lgf.file.model.FloderNode;
import com.sshtools.net.SocketTransport;
import com.sshtools.sftp.SftpClient;
import com.sshtools.sftp.SftpFile;
import com.sshtools.sftp.SftpStatusException;
import com.sshtools.ssh.ChannelOpenException;
import com.sshtools.ssh.PasswordAuthentication;
import com.sshtools.ssh.SshAuthentication;
import com.sshtools.ssh.SshClient;
import com.sshtools.ssh.SshConnector;
import com.sshtools.ssh.SshException;
import com.sshtools.ssh2.Ssh2Client;
import com.sshtools.util.UnsignedInteger64;

/**
 * 读取服务器端下目录
 * @author Administrator
 *
 */
public class ServerTraverseFolder {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd/HH:mm:ss");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");

	public static void main(String[] args) {

		String hostname = "192.168.1.1";
		int port = 22;
		String username = "root";
		String passwd = "";
		try {
			SocketTransport transport = new SocketTransport(hostname, port);
			SshConnector con = SshConnector.createInstance();
			final SshClient ssh = con.connect(transport, username);
			Ssh2Client ssh2 = (Ssh2Client) ssh;
			PasswordAuthentication pwd = new PasswordAuthentication();
			 String path = "/home/xyz";
//			String path = "/home/zyzx";
			pwd.setPassword(passwd);
			if (ssh2.authenticate(pwd) == SshAuthentication.COMPLETE && ssh.isConnected()) {
				if (ssh.isAuthenticated()) {
					SftpClient sftp = new SftpClient(ssh2);
					SftpFile[] list = sftp.ls(path);
					FloderNode node = new FloderNode();
					node.setFileName("zyz");
//					node.setFileName("ztd");
					node.setLevel(0);
					node.setFolder(true);
					showServerAllFiles(path, sftp, list, node, 1);
					 String filePath = "D:/Servergazx目录"+sdf1.format(new Date())+".txt";
					saveClassAsSon(node, "gazx");
//					String filePath = "D:/Serverjudcorrect目录.txt";
					File file = new File(filePath);
					if (!file.exists()) {
						System.out.println("文件不存在，创建");
						System.out.println("文件创建：" + file.createNewFile());
					}

					FileOutputStream fos = new FileOutputStream(file, true);
					showFloder(node, fos);
					fos.flush();
					fos.close();
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查看服务器文件夹下所有目录
	 * 
	 * @param sftp
	 * @param list
	 * @param node
	 * @param level
	 */
	final static void showServerAllFiles(String fatherPath, SftpClient sftp, SftpFile[] list, FloderNode node,
			int level) {
		List<FloderNode> sonNodeList = new ArrayList<FloderNode>();
		try {
			for (int i = 0; i < list.length; i++) {
				FloderNode sonNode = new FloderNode();
				SftpFile file = list[i];
				sonNode.setFileName(file.getFilename());
				System.out.print(file.getFilename());
				sonNode.setFolder(file.isDirectory());
				System.out.print("-" + file.isDirectory());
				sonNode.setLevel(level);
				sonNode.setModifeTime(file.getAttributes().getModifiedDateTime().getTime());
				System.out.print("-" + sdf.format(file.getAttributes().getModifiedDateTime().getTime()));
				UnsignedInteger64 fileSize = file.getAttributes().getSize();
				sonNode.setSize(fileSize.longValue());
				System.out.println("-" + fileSize.longValue());
				

				if (file.isDirectory() && !file.getFilename().equals("..") && !file.getFilename().equals(".")) {
					String sonPath = fatherPath + "/" + file.getFilename();
					System.out.println(sonPath);
					SftpFile[] sonList = sftp.ls(sonPath);
					showServerAllFiles(sonPath, sftp, sonList, sonNode, level + 1);
				}
				sonNodeList.add(sonNode);
			}
			node.setNode(sonNodeList);
		} catch (SftpStatusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 将node保存为json类型方便以后做对比
	 * 
	 * @param node
	 */
	private static void saveClassAsSon(FloderNode node, String name) {
		try {
			String filePath = "D:/" + name + sdf1.format(new Date()) + ".txt";
			File file = new File(filePath);
			if (!file.exists()) {
				System.out.println("文件不存在，创建");
				System.out.println("文件创建：" + file.createNewFile());
			}
			Gson gson = new Gson();
			FileOutputStream fos = new FileOutputStream(file, true);
			String content = gson.toJson(node);
			byte[] ba;
			ba = content.getBytes("utf-8");
			content = new String(ba);
			fos.write(content.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 将路径写入目录。txt
	 * 
	 * @param node
	 * @param fos
	 */
	private static void showFloder(FloderNode node, FileOutputStream fos) {
		String content = "";
		for (int i = 0; i < node.getLevel(); i++) {
			System.out.print("  ");
			content += "  ";

		}
		content += "|";
		content += "--";
		content += node.getFileName();
		content += "\t" + sdf.format(new Date(node.getModifeTime()));
		content += "\t" + node.getSize();
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
		if (node.isFolder() && node.getNode() != null) {
			for (FloderNode sonNode : sonNodeList) {
				showFloder(sonNode, fos);
			}
		}

	}

}
