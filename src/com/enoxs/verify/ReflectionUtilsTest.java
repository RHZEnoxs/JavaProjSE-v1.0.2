package com.enoxs.verify;


import com.enoxs.example.oop.Son;
import com.enoxs.utillity.ReflectionUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectionUtilsTest {

	/**
     * 測試獲取父類的各個方法對象 
     */  
      
    @Test  
    public void testGetDeclaredMethod() {  
          
        Object obj = new Son() ;
          
        //獲取公共方法名  
        Method publicMethod = ReflectionUtils.getDeclaredMethod(obj, "publicMethod") ;
        System.out.println(publicMethod.getName());  
          
        //獲取默認方法名  
        Method defaultMethod = ReflectionUtils.getDeclaredMethod(obj, "defaultMethod") ;  
        System.out.println(defaultMethod.getName());  
          
        //獲取被保護方法名  
        Method protectedMethod = ReflectionUtils.getDeclaredMethod(obj, "protectedMethod") ;  
        System.out.println(protectedMethod.getName());  
          
        //獲取私有方法名  
        Method privateMethod = ReflectionUtils.getDeclaredMethod(obj, "privateMethod") ;  
        System.out.println(privateMethod.getName());  
    }  
  
    /** 
     * 測試調用父類的方法 
     * @throws Exception 
     */  
      
    @Test  
    public void testInvokeMethod() throws Exception {  
        Object obj = new Son() ;  
          
        //調用父類的公共方法  
        ReflectionUtils.invokeMethod(obj, "publicMethod", null , null) ;  
          
        //調用父類的默認方法  
        ReflectionUtils.invokeMethod(obj, "defaultMethod", null , null) ;  
          
        //調用父類的被保護方法  
        ReflectionUtils.invokeMethod(obj, "protectedMethod", null , null) ;  
          
        //調用父類的私有方法  
        ReflectionUtils.invokeMethod(obj, "privateMethod", null , null) ;  
    }  
  
    /** 
     * 測試獲取父類的各個屬性名 
     */  
      
    @Test
    public void testGetDeclaredField() {  
          
        Object obj = new Son() ;  
          
        //獲取公共屬性名  
        Field publicField = ReflectionUtils.getDeclaredField(obj, "publicField") ;  
        System.out.println(publicField.getName());  
          
        //獲取公共屬性名  
        Field defaultField = ReflectionUtils.getDeclaredField(obj, "defaultField") ;  
        System.out.println(defaultField.getName());  
          
        //獲取公共屬性名  
        Field protectedField = ReflectionUtils.getDeclaredField(obj, "protectedField") ;  
        System.out.println(protectedField.getName());  
          
        //獲取公共屬性名  
        Field privateField = ReflectionUtils.getDeclaredField(obj, "privateField") ;  
        System.out.println(privateField.getName());  
          
    }  
  
    @Test  
    public void testSetFieldValue() {  
          
        Object obj = new Son() ;  
          
        System.out.println("原來的各個屬性的值: ");  
        System.out.println("publicField = " + ReflectionUtils.getFieldValue(obj, "publicField"));  
        System.out.println("defaultField = " + ReflectionUtils.getFieldValue(obj, "defaultField"));  
        System.out.println("protectedField = " + ReflectionUtils.getFieldValue(obj, "protectedField"));  
        System.out.println("privateField = " + ReflectionUtils.getFieldValue(obj, "privateField"));  
          
        ReflectionUtils.setFieldValue(obj, "publicField", "a") ;  
        ReflectionUtils.setFieldValue(obj, "defaultField", "b") ;  
        ReflectionUtils.setFieldValue(obj, "protectedField", "c") ;  
        ReflectionUtils.setFieldValue(obj, "privateField", "d") ;  
          
        System.out.println("***********************************************************");  
          
        System.out.println("將屬性值改變後的各個屬性值: ");  
        System.out.println("publicField = " + ReflectionUtils.getFieldValue(obj, "publicField"));  
        System.out.println("defaultField = " + ReflectionUtils.getFieldValue(obj, "defaultField"));  
        System.out.println("protectedField = " + ReflectionUtils.getFieldValue(obj, "protectedField"));  
        System.out.println("privateField = " + ReflectionUtils.getFieldValue(obj, "privateField"));  
          
    }  
  
    @Test  
    public void testGetFieldValue() {  
          
        Object obj = new Son() ;  
          
        System.out.println("publicField = " + ReflectionUtils.getFieldValue(obj, "publicField"));  
        System.out.println("defaultField = " + ReflectionUtils.getFieldValue(obj, "defaultField"));  
        System.out.println("protectedField = " + ReflectionUtils.getFieldValue(obj, "protectedField"));  
        System.out.println("privateField = " + ReflectionUtils.getFieldValue(obj, "privateField"));  
    }  

}
