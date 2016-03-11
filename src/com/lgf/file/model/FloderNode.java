package com.lgf.file.model;

import java.util.List;

public class FloderNode {
	private String fileName;//文件名
	private int noteId;//父节点id
	private int level;//层级
	private List<FloderNode> node;//子节点
	private boolean isFolder;
	
	
	
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
