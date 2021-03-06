package se.bth.serl.clony.chunks;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.util.StringUtils;

import se.bth.serl.clony.hadoop.ChunkArrayWritable;

public class CloneInstance implements WritableComparable<CloneInstance> {
	private String filename;
	private int firstRawLineNumber;
	private int lastRawLineNumber;
		
	public CloneInstance(LinkedList<Chunk> chunks) {
		Chunk first = chunks.getFirst();
		Chunk last = chunks.getLast();
		filename = first.getOriginId();
		firstRawLineNumber = first.getFirstRawLineNumber();
		lastRawLineNumber = last.getLastRawLineNumber();
	}
	
	public CloneInstance(CloneInstance c) {
		this.filename = c.filename;
		this.firstRawLineNumber = c.firstRawLineNumber;
		this.lastRawLineNumber = c.lastRawLineNumber;
	}
	
	public CloneInstance() {}
	
	
	public int getLength() {
		return lastRawLineNumber - firstRawLineNumber + 1;
	}
	
	public File getFile() {
		return new File(filename);
	}
	
	public String getFilename() {
		return filename;
	}
	
	public int getFirstLineNumber() {
		return firstRawLineNumber;
	}
	
	public int getLastLineNumber() {
		return lastRawLineNumber;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("File: ")
		.append(filename)
		.append(" | ")
		.append(firstRawLineNumber)
		.append("-")
		.append(lastRawLineNumber)
		.append("\n");
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(this == obj)
			return true;
		
		if(!(obj instanceof CloneInstance))
			return false;
		
		CloneInstance other = (CloneInstance) obj;
		return this.filename.equals(other.filename) &&
				this.firstRawLineNumber == other.firstRawLineNumber &&
				this.lastRawLineNumber == other.lastRawLineNumber;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + filename.hashCode();
		result = 37 * result + firstRawLineNumber;
		result = 37 * result + lastRawLineNumber;
		return result;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		filename = in.readUTF();
		firstRawLineNumber = in.readInt();
		lastRawLineNumber = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(filename);
		out.writeInt(firstRawLineNumber);
		out.writeInt(lastRawLineNumber);
	}

	@Override
	public int compareTo(CloneInstance o) {
		if(this.equals(o))
			return 0;
		
		int cmp = filename.compareTo(o.filename);
		if(cmp != 0)
			return cmp;
		
		 cmp = firstRawLineNumber - o.firstRawLineNumber;
		 if(cmp != 0)
			 return cmp;
                 
		 cmp = lastRawLineNumber - o.lastRawLineNumber;         
		 if(cmp != 0)
			 return cmp;

		return 0;
	}
}
