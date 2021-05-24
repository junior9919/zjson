package com.halo.zjson;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

import java.util.Map;

public class JSONUtils<T> {

    private Class<T> clazz;

    public JSONUtils(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public T getJsonBean(String jsonStr) {
        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        return (T) JSONObject.toBean(jsonObj, this.clazz);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public T getComplexJsonBean(String jsonStr, Map<String, Class> classMap) {
        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        return (T) JSONObject.toBean(jsonObj, this.clazz, classMap);
    }

    public String getJsonStr(T jsonBean) {
        JSONObject jsonObj = JSONObject.fromObject(jsonBean);
        return jsonObj.toString();
    }

    public T getJsonBeanCaseInsensitive(String jsonStr) {
        JsonConfig config = new JsonConfig();
        config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {

            @Override
            public String transformToJavaIdentifier(String str) {
                char[] chars = str.toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                return new String(chars);
            }
        });
        config.setRootClass(this.clazz);

        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        return (T) JSONObject.toBean(jsonObj, config);
    }
}
