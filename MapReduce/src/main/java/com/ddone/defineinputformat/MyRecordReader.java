package com.ddone.defineinputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author ddone
 * @date 2020/8/7-01:30
 * <p>
 * key是文件名,value是文件内容
 */
public class MyRecordReader extends RecordReader<Text, BytesWritable> {
    private Configuration conf;
    private FileSplit split;
    Text outK = new Text();
    BytesWritable outValue = new BytesWritable();
    private boolean isProgress = true;

    /**
     * 获取配置和切片信息
     *
     * @param split
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        conf = context.getConfiguration();
        this.split = (FileSplit) split;
    }

    /**
     * 读取数据和写出数据
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (isProgress) {

            // 1 定义缓存区
            byte[] contents = new byte[(int) split.getLength()];

            FileSystem fs = null;
            FSDataInputStream fis = null;

            try {
                // 2 获取文件系统
                Path path = split.getPath();
                fs = path.getFileSystem(conf);

                // 3 读取数据
                fis = fs.open(path);

                // 4 读取文件内容
                IOUtils.readFully(fis, contents, 0, contents.length);

                // 5 输出文件内容
                outValue.set(contents, 0, contents.length);

                // 6 获取文件路径及名称
                String name = split.getPath().toString();

// 7 设置输出的key值
                outK.set(name);

            } catch (Exception e) {

            } finally {
                IOUtils.closeStream(fis);
            }

            isProgress = false;

            return true;
        }

        return false;
    }

    public Text getCurrentKey() throws IOException, InterruptedException {
        return outK;
    }

    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return outValue;
    }

    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    public void close() throws IOException {
    }
}
