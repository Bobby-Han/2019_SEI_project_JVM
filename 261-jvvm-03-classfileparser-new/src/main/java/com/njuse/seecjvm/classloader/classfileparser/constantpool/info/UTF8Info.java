package com.njuse.seecjvm.classloader.classfileparser.constantpool.info;

import com.njuse.seecjvm.classloader.classfileparser.BuildUtil;
import com.njuse.seecjvm.classloader.classfileparser.constantpool.ConstantPool;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import javax.naming.NamingEnumeration;
import java.nio.ByteBuffer;


@Getter
public class UTF8Info extends ConstantPoolInfo {

    /**
     * Add some codes here.
     *
     * tips:
     * step1
     *      UTF8Info need some fields, what are they?
     * step2
     *      You need to add some args in constructor
     *      and don't forget to set tag
     *
     *      super method and super key word will help you
     *
     * step3
     *      The length of String is unknown for getConstantPoolInfo method
     *      How to return the instance with its length?
     *
     *      return a Pair<UTF8Info,Integer> or get the length of string in UTF8Info?
     *
     */
    //todo attributes of UTF8Info

    private int length;
    private byte[] bytes;
    private String myString;

    //todo constructor of UTF8Info
    public UTF8Info(ConstantPool constantPool, int length, byte[] bytes){
        super(constantPool);
        this.length=length;
        this.bytes=bytes;
        if(bytes.length!=length){
            throw new UnsupportedOperationException("UTF8 constantpool info expects "+length+" bytes, actual is " + bytes.length);
        }
        //将bytes转化为String
        this.myString=new String(bytes);
        super.tag=ConstantPoolInfo.UTF8;
    }

    /**
     * Add some codes here.
     * return the string of UTF8Info
     */
    //todo getInstance
    //这个方法其实可以不要！！
    static Pair<UTF8Info, Integer> getInstance(ConstantPool constantPool, byte[] in, int offset) {
        ByteBuffer buffer=ByteBuffer.wrap(in,offset,in.length-offset);

        BuildUtil buildUtil=new BuildUtil(buffer);
        int string_length=0xFFFF & (int) buffer.getShort();
        byte the_string_bytes[]=new byte[string_length];
        for(int i=0;i<string_length;i++){
            the_string_bytes[i]=buffer.get();
        }
        UTF8Info utf8Info=new UTF8Info(constantPool,string_length,the_string_bytes);
        return Pair.of(utf8Info,2+string_length);
    }

    //todo return string
    public String getString() {
        return myString;
    }
}
