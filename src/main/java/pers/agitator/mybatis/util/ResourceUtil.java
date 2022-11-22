package pers.agitator.mybatis.util;

import java.io.InputStream;

public class ResourceUtil {
    private static final ClassLoader CLASS_LOADER = ResourceUtil.class.getClassLoader();

    public static InputStream getResources(String resource) {
        return CLASS_LOADER.getResourceAsStream(resource);
    }

}
