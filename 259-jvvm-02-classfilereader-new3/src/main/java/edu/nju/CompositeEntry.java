package edu.nju;

import java.io.IOException;

/**
 * format : dir/subdir;dir/subdir/*;dir/target.jar*
 */
public class CompositeEntry extends Entry{
    public CompositeEntry(String classpath) {
        super(classpath);
    }
    public Entry the_correct_entry;

    @Override
    public byte[] readClassFile(String className) throws IOException {
        String[] diff_path=classpath.split(PATH_SEPARATOR);
        for(String ele:diff_path){
            the_correct_entry=ClassFileReader.chooseEntryType(ele);
            if(the_correct_entry.readClassFile(className)!=null){
                return the_correct_entry.readClassFile(className);
            }
        }
        return null;
    }
}
