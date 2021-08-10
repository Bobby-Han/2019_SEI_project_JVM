package edu.nju;

import java.io.*;

/**
 * format : dir/subdir/.../
 */
public class DirEntry extends Entry{
    public DirEntry(String classpath) {
        super(classpath);
    }

    @Override
    public byte[] readClassFile(String className) throws IOException {
        byte[] res=new byte[0];
        File re_file=new File(classpath);
        String absPath=re_file.getAbsolutePath();
        try {
            File file=new File(absPath+File.separator+className);
            InputStream in=new FileInputStream(file);
            res=IOUtil.readFileByBytes(in);
        }catch (FileNotFoundException exp){
            return null;
        }
        return res;
    }
}
