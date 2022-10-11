package com.bmsoft.toolkit.mybatis.support;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.bmsoft.toolkit.core.Dict;
import com.bmsoft.toolkit.core.holder.DictHolder;
import com.bmsoft.toolkit.mybatis.annotation.Translate;
import com.bmsoft.toolkit.mybatis.annotation.Translation;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * 字典翻译拦截器
 *
 * @author llk
 * @date 2019-11-11 19:03
 */
@Intercepts(
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class)
)
public class TranslationInterceptor implements Interceptor {

    public TranslationInterceptor() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> objects = (List<Object>) invocation.proceed();
        return translate(objects);
    }

    private Object translate(List<Object> objects) throws NoSuchFieldException, IllegalAccessException {
        Integer size = DictHolder.getInstance().size();
        // Objects.nonNull(objects.get(0)), 解决统计函数返回null 的问题
        if (size > 0 && CollUtil.isNotEmpty(objects) && Objects.nonNull(objects.get(0))) {
            Class<?> aClass = objects.get(0).getClass();
            Translation translation = aClass.getAnnotation(Translation.class);
            if (Objects.nonNull(translation)) {
                for (Object object : objects) {
                    Field[] fields = ReflectUtil.getFields(aClass);
                    for (Field field : fields) {
                        Translate translate = field.getAnnotation(Translate.class);
                        if (translate != null) {
                            String byFieldStr = translate.byField();
                            String type = translate.type();
                            Field byField = aClass.getDeclaredField(byFieldStr);
                            byField.setAccessible(true);
                            Object o = byField.get(object);

                            Dict dict = DictHolder.getInstance().get(type, String.valueOf(o));

                            field.setAccessible(true);
                            field.set(object, dict.getValue());
                        }
                    }
                }
            }
        }
        return objects;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
