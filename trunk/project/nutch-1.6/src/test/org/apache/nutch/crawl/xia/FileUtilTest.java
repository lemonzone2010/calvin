package org.apache.nutch.crawl.xia;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.fs.FileUtil;

public class FileUtilTest {
	public static void main(String[] args) throws IOException {
		FileUtil.makeShellPath("c:/");
	    ProcessBuilder pb=new ProcessBuilder("cygpath","-jar","Test3.jar");
        //让这个进程的工作区空间改为F:\dist
        //这样的话,它就会去F:\dist目录下找Test.jar这个文件
      //  pb.directory(new File("D:\\tmp"));
        Process p=pb.start();
        p.getOutputStream();
	}
}
