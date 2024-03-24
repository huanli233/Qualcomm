package com.huanli233.qualcomm.emmcdl.bean;

public class Partition {
	String name;
	long start;
	long size;
	long end;
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return start
	 */
	public long getStart() {
		return start;
	}
	/**
	 * @param start 要设置的 start
	 */
	public void setStart(long start) {
		this.start = start;
	}
	/**
	 * @return size
	 */
	public long getSize() {
		return size;
	}
	/**
	 * @param size 要设置的 size
	 */
	public void setSize(long size) {
		this.size = size;
	}
	/**
	 * @return end
	 */
	public long getEnd() {
		return end;
	}
	/**
	 * @param end 要设置的 end
	 */
	public void setEnd(long end) {
		this.end = end;
	}
	
	public Partition(String name, long start, long size, long end) {
		this.name = name;
		this.start = start;
		this.size = size;
		this.end = end;
	}
	
	public Partition(String name, long start, long size) {
		this(name, start, size, start + size);
	}
}
