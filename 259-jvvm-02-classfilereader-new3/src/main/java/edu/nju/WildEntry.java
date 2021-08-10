package edu.nju;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * format : dir/.../*
 */
public class WildEntry extends Entry{
    public WildEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {
        File re_file=new File(classpath.substring(0,classpath.length()-2));
        String absPath=re_file.getAbsolutePath();
        File absFile=new File(absPath);
        File[] allFiles=absFile.listFiles();
        int zipcount=0;
        for(File ele:allFiles){
            if(ele.getAbsolutePath().endsWith("jar")||ele.getAbsolutePath().endsWith("JAR")||ele.getAbsolutePath().endsWith("zip")||ele.getAbsolutePath().endsWith("ZIP")){
                zipcount++;
            }
        }
        if(zipcount==0){
            return null;
        }else {
            ZipFile zipFile;
            for(int i=0;i<allFiles.length;i++){
                File ele=allFiles[i];
                zipFile=new ZipFile(ele.getAbsolutePath());
                if(zipFile.size()==1){
                    continue;
                }else {
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

        }
        return null;
    }
}
