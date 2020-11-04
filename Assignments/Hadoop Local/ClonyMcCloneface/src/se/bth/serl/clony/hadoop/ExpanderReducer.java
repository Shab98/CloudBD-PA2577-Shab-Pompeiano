package se.bth.serl.clony.hadoop;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import se.bth.serl.clony.chunks.Chunk;
import se.bth.serl.clony.chunks.Clone;

public class ExpanderReducer extends Reducer<Clone, NullWritable, Clone, NullWritable> {

	//TODO implement the reduce() method
	
	
	public void reduce(Clone clone, NullWritable n, Context context) throws IOException, InterruptedException {
		context.write(clone, n);
	}
}
