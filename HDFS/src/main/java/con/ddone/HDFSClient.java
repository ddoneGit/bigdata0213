package con.ddone;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.io.IOException;
import java.net.URI;

/**
 * @author ddone
 * @date 2020/7/30-20:41
 */
public class HDFSClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileSystem hdfsClient = getHDFSClient();
//        System.out.println("获取到的"+hdfsClient);
        uploadFile(hdfsClient);
//        downLoadFile(hdfsClient);
//        deleteFile(hdfsClient);
//        renameFile(hdfsClient);
//        getFileDetail(hdfsClient);
//        listFiles(hdfsClient,new Path("/hdfstest"));
        hdfsClient.close();
    }
    /*
    显示目录下文件
     */
    private static void listFiles(FileSystem hdfsClient,Path path) throws IOException {
        FileStatus[] fileStatuses = hdfsClient.listStatus(path);

        for (FileStatus fileStatus : fileStatuses) {
            if(fileStatus.isFile()){
                System.out.println("File:"+fileStatus.getPath().getName());
            }else {
                System.out.println("Direct:" + fileStatus.getPath());
                System.out.println();
                listFiles(hdfsClient,fileStatus.getPath());
            }

        }
    }
    /*
    查看文件详情
     */
    private static void getFileDetail(FileSystem hdfsClient) throws IOException {
        RemoteIterator<LocatedFileStatus> listFiles = hdfsClient.listFiles(new Path("/hdfstest"), true);
        while (listFiles.hasNext()){
            LocatedFileStatus statu = listFiles.next();
            //获取路径
            System.out.println(statu.getPath());
            System.out.println(statu.getLen());
            System.out.println(statu.getPermission());
            System.out.println(statu.getGroup());
            BlockLocation[] blockLocations = statu.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                System.out.println(blockLocation.getHosts());

            }
        }
    }
    /*
    修改文件名字
     */
    private static void renameFile(FileSystem hdfsClient) throws IOException {
        hdfsClient.rename(new Path("/hdfstest/sensor.txt"), new Path("/hdfstest/ceshi.txt"));
    }
    /*
    删除文件
     */
    private static void deleteFile(FileSystem hdfsClient) throws IOException {
        hdfsClient.delete(new Path("/hdfstest/output"), true);
    }
    /*
    下载文件
     */
    private static void downLoadFile(FileSystem hdfsClient) throws IOException {
        hdfsClient.copyToLocalFile(new Path("/hdfstest/sensor.txt"),new Path("input/sensor2.txt"));
    }
    /*
    上传文件
     */
    private static void uploadFile(FileSystem hdfsClient) throws IOException {
        hdfsClient.copyFromLocalFile(new Path("input/sensor777.txt"),new Path("/hdfstest"));
        hdfsClient.setReplication(new Path("/hdfstest/sensor.txt"), (short) 1);
    }

    /**
     *
     * @return 返回一个HDFS的客户端
     * @throws IOException
     * @throws InterruptedException
     */
    private static FileSystem getHDFSClient() throws IOException, InterruptedException {
//        DistributedFileSystem fileSystem = new DistributedFileSystem();
//        fileSystem.initialize(URI.create("hdfs://hadoop102:9820"),new Configuration());
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://hadoop102:9820/"), new Configuration(), "atguigu");
        return fileSystem;
    }


}
