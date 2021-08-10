package edu.nju;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * format : dir/subdir/target.jar
 */
public class ArchivedEntry extends Entry{
    public ArchivedEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {
        File the_dir=new File(this.classpath);
        String absDirPath=the_dir.getAbsolutePath();
        //使用绝对路径得到zipFile
        ZipFile zipFile=new ZipFile(absDirPath);
        //枚举类存放zipFile中的条目
        Enumeration entries=zipFile.entries();
        String pathname;
        ZipEntry zipEntry;
        do{
            if(!entries.hasMoreElements()){
                return null;
            }
            zipEntry=(ZipEntry) entries.nextElement();
            pathname=IOUtil.transform(zipEntry.getName());
        }while (!pathname.equals(className));
        return IOUtil.readFileByBytes(zipFile.getInputStream(zipEntry));



    }



}
