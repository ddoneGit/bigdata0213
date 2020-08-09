package con.ddone;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

/**
 * @author ddone
 * @date 2020/8/4-13:30
 */
public class HDFSSrcTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        //获取Client
        Configuration conf = new Configuration();
        conf.set(CommonConfigurationKeys.FS_DEFAULT_NAME_KEY,"hdfs://hadoop102:9820");
        FileSystem fs = FileSystem.get(conf);
        FileSystem.get(URI.create("hdfs://hadoop102:9820"), conf, "atguigu");

        FSDataOutputStream fsos = fs.create(new Path(""));
        /*
         *   public FSDataOutputStream create(Path f,
         *       boolean overwrite,
         *       int bufferSize,
         *       short replication,
         *       long blockSize) throws IOException {
         *     return create(f, overwrite, bufferSize, replication, blockSize, null);
         *   }
         */
    }
}
