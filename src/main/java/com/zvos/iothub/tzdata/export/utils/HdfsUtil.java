package com.zvos.iothub.tzdata.export.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * HDFS操作，需要导入common和hdsf文件夹下的jar
 */
@Slf4j
public class HdfsUtil {

    public FileSystem fs = null;

    public HdfsUtil() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        fs = FileSystem.get(new URI("hdfs://dev01:8020"), conf, "root");

    }

    /**
     * 下载文件
     *
     * @throws IOException
     */
    public void download() throws IOException {
        InputStream in = fs.open(new Path("/java"));
        OutputStream out = new FileOutputStream("/Users/Desktop");
        IOUtils.copyBytes(in, out, 4096, true);
    }

    /**
     * 上传文件
     *
     * @throws IOException
     */
    public void upload() throws IOException {
        System.out.println("开始上传...");
        InputStream in = new FileInputStream("/Users/Desktop/compare.py");
        OutputStream out = this.fs.create(new Path("/compare.py"));
        IOUtils.copyBytes(in, out, 4096, true);
    }

    public void write(String content, String hdfs_path) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        conf.setBoolean("dfs.support.append", true);
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(hdfs_path), conf);
            Path path = new Path(hdfs_path);
            FSDataOutputStream outputStream = null;
            if (!fs.exists(path)) {
                outputStream = fs.create(path);
            } else {
                fs.delete(path);
                outputStream = fs.create(path);
            }
            //fs.append(new Path(hdfs_path));//.create(new Path(hdfs_path));
            outputStream.write(content.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除文件
     *
     * @throws IOException
     */
    public void delete() throws IOException {
        boolean ret = this.fs.delete(new Path("/java"), true);
        System.out.println("文件删除结果：" + ret);
    }

    /**
     * 创建文件夹
     *
     * @throws IOException
     */
    public void mkdir() throws IOException {
        boolean ret = this.fs.mkdirs(new Path("/temp"));
        System.out.println("文件创建成功：" + ret);
    }

}
