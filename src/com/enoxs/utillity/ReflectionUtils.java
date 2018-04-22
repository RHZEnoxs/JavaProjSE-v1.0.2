package com.enoxs.utillity;
import java.lang.reflect.Field;  

import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  

public class ReflectionUtils {


    /** 
     * 循環向上轉型, 獲取對象的 DeclaredMethod 
     * @param object : 子類對象 
     * @param methodName : 父類中的方法名 
     * @param parameterTypes : 父類中的方法參數類型 
     * @return 父類中的方法對象 
     */  
      
    public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){  
        Method method = null ;  
          
        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;  
                return method ;  
            } catch (Exception e) {  
                //這裡甚麼都不要做！並且這裡的異常必須這樣寫，不能拋出去。  
                //如果這裡的異常打印或者往外拋，則就不會執行clazz = clazz.getSuperclass(),最後就不會進入到父類中了
            }  
        }
        return null;  
    }

    /** 
     * 直接調用對象方法, 而忽略修飾符(private, protected, default) 
     * @param object : 子類對象 
     * @param methodName : 父類中的方法名 
     * @param parameterTypes : 父類中的方法參數類型 
     * @param parameters : 父類中的方法參數 
     * @return 父類中方法的執行結果 
     */  
      
    public static Object invokeMethod(Object object, String methodName, Class<?> [] parameterTypes,  
            Object [] parameters) {  
        //根據 對象、方法名和對應的方法參數 通過反射 調用上面的方法獲取 Method 對象  
        Method method = getDeclaredMethod(object, methodName, parameterTypes) ;  
          
        //抑制Java對方法進行檢查,主要是針對私有方法而言  
        method.setAccessible(true) ;  
          
            try {  
                if(null != method) {  
                      
                    //調用object 的 method 所代表的方法，其方法的參數是 parameters  
                    return method.invoke(object, parameters) ;  
                }  
            } catch (IllegalArgumentException e) {  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                e.printStackTrace();  
            } catch (InvocationTargetException e) {  
                e.printStackTrace();  
            }  
          
        return null;  
    }  
  
    /** 
     * 循環向上轉型, 獲取對象的 DeclaredField 
     * @param object : 子類對象 
     * @param fieldName : 父類中的屬性名 
     * @return 父類中的屬性對象 
     */  
      
    public static Field getDeclaredField(Object object, String fieldName){  
        Field field = null ;  
          
        Class<?> clazz = object.getClass() ;  
          
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                field = clazz.getDeclaredField(fieldName) ;  
                return field ;  
            } catch (Exception e) {  
                //這裡甚麼都不要做！並且這裡的異常必須這樣寫，不能拋出去。  
                //如果這裡的異常打印或者往外拋，則就不會執行clazz = clazz.getSuperclass(),最後就不會進入到父類中了  
                  
            }   
        }  
      
        return null;  
    }     
      
    /** 
     * 直接設置對象屬性值, 忽略 private/protected 修飾符, 也不經過 setter 
     * @param object : 子類對象 
     * @param fieldName : 父類中的屬性名 
     * @param value : 將要設置的值 
     */  
      
    public static void setFieldValue(Object object, String fieldName, Object value){  
      
        //根據 對象和屬性名通過反射 調用上面的方法獲取 Field對象  
        Field field = getDeclaredField(object, fieldName) ;  
          
        //抑制Java對其的檢查  
        field.setAccessible(true) ;  
          
        try {  
            //將 object 中 field 所代表的值 設置為 value  
             field.set(object, value) ;  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
          
    }  
      
    /** 
     * 直接讀取對象的屬性值, 忽略 private/protected 修飾符, 也不經過 getter 
     * @param object : 子類對象 
     * @param fieldName : 父類中的屬性名 
     * @return : 父類中的屬性值 
     */  
      
    public static Object getFieldValue(Object object, String fieldName){  
          
        //根據 對象和屬性名通過反射 調用上面的方法獲取 Field對象  
        Field field = getDeclaredField(object, fieldName) ;  
          
        //抑制Java對其的檢查  
        field.setAccessible(true) ;  
          
        try {  
            //獲取 object 中 field 所代表的屬性值  
            return field.get(object) ;  
              
        } catch(Exception e) {  
            e.printStackTrace() ;  
        }  
          
        return null;  
    }  
}
