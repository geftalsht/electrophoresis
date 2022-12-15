package org.gefsu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentScanner {
    public Set<Class<Object>> findAllClassesUsingClassLoader(Package packageName) {
        InputStream inputStream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream(packageName.getName().replaceAll("[.]","/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader.lines()
            .filter(line -> line.endsWith(".class"))
            .map(line -> getClass(line, packageName))
            .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private Class<Object> getClass(String className, Package packageName) {
        try {
            return (Class<Object>) Class.forName(packageName.getName() + "."
            + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // It is what it is
        }
        return null;
    }

    public Set<Class<Object>> findClassesAnnotatedWith(
        Package packageName, Class<? extends Annotation> annotationClass)
    {
        return findAllClassesUsingClassLoader(packageName)
            .stream()
            .filter(aClass -> aClass.isAnnotationPresent(annotationClass))
            .collect(Collectors.toSet());
    }
}
