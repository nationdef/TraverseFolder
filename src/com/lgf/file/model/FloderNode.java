package com.lgf.file.model;

import java.util.List;

public class FloderNode {
	private String fileName;//文件名
	private int noteId;//父节点id
	private int level;//层级
	private List<FloderNode> node;//子节点
	private boolean isFolder;
	private long createTime;//创建时间
	private long modifeTime;//修改时间
	private long size;//大小byte
	private String hashValue;// hash值
	
	
	
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getModifeTime() {
		return modifeTime;
	}
	public void setModifeTime(long modifeTime) {
		this.modifeTime = modifeTime;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getHashValue() {
		return hashValue;
	}
	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}
	public boolean isFolder() {
		return isFolder;
	}
	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<FloderNode> getNode() {
		return node;
	}
	public void setNode(List<FloderNode> node) {
		this.node = node;
	}
	
	
	

}
