package com.cuit.boke.utils;

import com.yinjk.web.core.exception.BizException;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;

public class TransformUtils {

    private static final Logger logger = LoggerFactory.getLogger(TransformUtils.class);

    /**
     * 通过反射将object非空的属性存入map
     *
     * @param object
     * @param map
     */
    public static Map<String, ? super Object> putObjToMap(Object object, Map<String, ? super Object> map, String... ignore) throws BizException {
        Class cc = object.getClass();
        //获得所有私有字段
        Field[] fields = cc.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            //字段名
            String fieldName = fields[i].getName();
            //根据字段名拼接对应get方法
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method;
            try {
                //获得get方法
                method = cc.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                continue;
            }
            if (method != null) {
                //执行get方法取得方法返回值
                Object result = null;
                try {
                    result = method.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    String msg = "反射错误";
                    throw new BizException(msg);
                }
                if (result != null) { //返回值为null则跳过该字段，读取下一个字段
                    if (result instanceof String) {
                        String sresult = (String) result;
                        if (!StringUtils.isBlank(sresult)) { //如果是String，并且String不是空格和空串
                            //添加到map
                            map.put(fieldName, result);
                        }
                    } else {
                        //其他类型只要不是null，均添加到map
                        map.put(fieldName, result);
                    }
                }
            }
        }
        return map;
    }

    public static Map<String, ? super Object> putObjToMap(Object object, final String... ignore) throws BizException {
        Class cc = object.getClass();
        List<String> ignoreList = Arrays.asList(ignore);
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(cc, Object.class);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String name = propertyDescriptor.getName();
                if (ignoreList.contains(name)) { //排除忽略的字段
                    continue;
                }
                Method readMethod = propertyDescriptor.getReadMethod();
                map.put(name, readMethod.invoke(object));
            }
        } catch (Exception e) {
            String msg = "反射错误！";
            throw new BizException(msg);
        }
        return map;
    }

    public static List<Map<String, Object>> putObjToMapWithList(List<?> list, String ... ignore) throws BizException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Object o : list) {
            mapList.add(putObjToMap(o, ignore));
        }
        return mapList;
    }

    /**
     * 通过反射将map解析成一个pojo类
     *
     * @param clazz pojo类型
     * @param map   属性集合
     */
    public static <T> T parseMapToObj(Map<String, ? super Object> map, Class<T> clazz) {
        T target = null;
        try {
            target = clazz.getConstructor().newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        if (Collections.isEmpty(map)) {//如果map为空，直接将创建的对象返回
            return target;
        }
        Set<? extends Map.Entry<String, ? super Object>> entries = map.entrySet();
        for (Map.Entry<String, ? super Object> entry : entries) {
            try {
                Field field = clazz.getDeclaredField(entry.getKey());
                field.setAccessible(true);//设置可以对private设值
                field.set(target, entry.getValue());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return target;
    }


    /**
     * 把字符串类型的ids放到 List集合中
     *
     * @param ids    字符串类型的ids 以逗号分割
     * @param idType id主键类型，只支持Long或者是Integer
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> putIdsToList(@NotNull String ids, @NotNull Class<T> idType) {
        return putIdsToList(ids, ",", idType);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> putIdsToList(@NotNull String ids, final String split,  @NotNull Class<T> idType) {
        if (StringUtils.isBlank(ids)) {
            return new ArrayList<>(1);
        }
        String[] sids = ids.split(split);
        List<T> list = new ArrayList<>(sids.length);
        for (String id : sids) {
            if (idType.equals(Long.class)) {
                list.add((T) Long.valueOf(id));
            } else if (idType.equals(Integer.class)) {
                list.add((T) Integer.valueOf(id));
            } else if (idType.equals(String.class) || idType.equals(Object.class)) {
                list.add((T) id);
            }
        }
        return list;
    }

    /**
     * 将list所装的元素中的某个属性拼成字符串
     * <p>
     * 使用示例：
     * List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3"));
     * String ids = getIdsWithList(list, "|", value -> value);
     * System.out.println(ids);
     *
     * @param list     list
     * @param split    分隔符
     * @param function 通过lambda取出元素中要拼的属性
     * @param <T>      list装的元素的泛型
     * @return 拼接好的字符串
     */
    public static <T> String getIdsByList(List<T> list, String split, Function<T, Object> function) {
        StringBuilder idsBuilder = new StringBuilder("");
        list.forEach(value -> idsBuilder.append(split).append(function.apply(value)));
        String ids = idsBuilder.toString();
        if (StringUtils.isBlank(ids)) {
            return "";
        }
        return ids.substring(1);//去掉第一个分割符
    }

    public static <T> String getIdsByList(List<T> list, String split, Function<T, Object> function, boolean distinct) {
        if (distinct) {//去重
            StringBuilder idsBuilder = new StringBuilder("");
            list.stream().map(function).distinct().forEach(value->{
                idsBuilder.append(split).append(value);
            });
            String ids = idsBuilder.toString();
            if (StringUtils.isBlank(ids)) {
                return "";
            }
            return ids.substring(1);//去掉第一个分割符
        } else {
            return getIdsByList(list, split, function);
        }
    }


    @SuppressWarnings("unchecked")
    public static <R> R castTo(Object obj, Class<R> clazz){
        if (null == obj) {
            return null;
        }
        if (clazz.equals(String.class)) {
            if (obj instanceof Double || obj instanceof Float) { //消除float/double类型转换成string使用科学记数法
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                return (R) nf.format(obj);
            } else {
                return (R) String.valueOf(obj);
            }

        }
        if (!ClassUtils.isPrimitiveOrWrapper(obj.getClass()) && !obj.getClass().equals(String.class) || !ClassUtils.isPrimitiveOrWrapper(clazz)) {
            String msg = "不支持的类型转换";
            logger.error(msg);
            throw new UnsupportedOperationException(msg);
        }
        Class<?> convertedClass = clazz;
        if (!ClassUtils.isPrimitiveWrapper(clazz)) {
            convertedClass = ClassUtils.primitiveToWrapper(clazz);
        }
        if (obj.getClass().equals(convertedClass)) {
            return (R) obj;
        } else {
            String s = String.valueOf(obj);
            return cast(s, (Class<R>) convertedClass);
        }
    }

    private static <R> R cast(String s, Class<R> clazz) {
        if (Integer.class.equals(clazz) || Long.class.equals(clazz)) {
            s = s.replaceAll("\\.\\d+$", ""); //如果要转成整形，截断小数点后的数
        }
        if (Float.class.equals(clazz)) {
            return (R) Float.valueOf(s);
        } else if (Double.class.equals(clazz)) {
            return (R) Double.valueOf(s);
        } else if (Integer.class.equals(clazz)) {
            return (R) Integer.valueOf(s);
        } else if (Long.class.equals(clazz)) {
            return (R) Long.valueOf(s);
        } else if (Boolean.class.equals(clazz)) {
            return (R) Boolean.valueOf(s);
        } else if (Short.class.equals(clazz)) {
            return (R) Short.valueOf(s);
        } else if (Byte.class.equals(clazz)) {
            return (R) Byte.valueOf(s);
        }else{
            String msg = "不支持的类型转换";
            logger.error(msg);
            throw new UnsupportedOperationException(msg);
        }
    }

    public static void main(String[] args){
        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3", "1"));
        String ids = getIdsByList(list, "|", value -> value, true);
        System.out.println(ids);
    }

//    public static void main(String[] args) throws BizException {
//        int i = 1;
//        Object o = i;
//        boolean flag = ClassUtils.isAssignable(o.getClass(), Integer.class, true);
//        System.out.println(flag);
//        System.out.println(castTo(i, String.class));
//    }

//    public static void main(String[] args){
//        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3"));
//        String ids = getIdsWithList(list, "|", value -> value);
//        System.out.println(ids);
//    }

//    public static void main(String[] args) throws BizException {
//        String ids = "1,2,4,6,9,0";
//        List<String> idList = putIdsToList(ids, String.class);
//        idList.forEach(System.out::println);
//    }

//    public static void main(String[] args){
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", 100L);
//        map.put("country", "中国");
//        map.put("districtLv1", "四川");
//        map.put("latitude", 60.25f);
//        DictRegionPO dictRegionPO = CollectionUtils.parseMapToObj(map, DictRegionPO.class);
//        System.out.println(dictRegionPO);
//
//    }


}