package com.njuse.seecjvm.memory.jclass.runtimeConstantPool.constant.ref;

import com.njuse.seecjvm.classloader.classfileparser.constantpool.info.MethodrefInfo;
import com.njuse.seecjvm.memory.jclass.JClass;
import com.njuse.seecjvm.memory.jclass.Method;
import com.njuse.seecjvm.memory.jclass.runtimeConstantPool.RuntimeConstantPool;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Getter
@Setter
public class MethodRef extends MemberRef {
    private Method method;

    public MethodRef(RuntimeConstantPool runtimeConstantPool, MethodrefInfo methodrefInfo) {
        super(runtimeConstantPool, methodrefInfo);
    }

    /**
     * TODO：实现这个方法
     * 这个方法用来实现对象方法的动态查找
     * @param clazz 对象的引用
     */
    public Method resolveMethodRef(JClass clazz) {
        resolve(clazz);
        return method;
    }

    /**
     * TODO: 实现这个方法
     * 这个方法用来解析methodRef对应的方法
     * 与上面的动态查找相比，这里的查找始终是从这个Ref对应的class开始查找的
     */
    public Method resolveMethodRef() {
        try {
            resolveClassRef();
            resolve(clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return method;
    }

    private void resolve(JClass clazz){
        assert clazz!=null;
        //从当前类开始不断查找父类，看是否能够在父类中查找到这个方法，找到就返回
        Optional<Method> optionalMethod;
        for(JClass curclazz=clazz;curclazz!=null;curclazz=clazz.getSuperClass()){
            optionalMethod=curclazz.resolveMethod(name,descriptor);
            if(optionalMethod.isPresent()){
                method= optionalMethod.get();
                return;
            }
        }
        //在父类中找不到这个类，继续到这个类的所有接口中查找，找到再返回
        //使用栈数据结构
        JClass[] ifs=clazz.getInterfaces();
        Stack<JClass> interfaces=new Stack<>();
        interfaces.addAll(Arrays.asList(ifs));
        while (!interfaces.isEmpty()){
            JClass clz=interfaces.pop();
            optionalMethod=clz.resolveMethod(name,descriptor);
            if(optionalMethod.isPresent()){
                method=optionalMethod.get();
                return;
            }
            //这里还要继续加interfaces
            interfaces.addAll(Arrays.asList(clz.getInterfaces()));
        }
    }


    @Override
    public String toString() {
        return "MethodRef to " + className;
    }
}
