package next.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerScanner {

    private final List<Class<?>> classes;

    public ControllerScanner(List<Class<?>> classes) {
        this.classes = classes;
    }

    public ControllerScanner(Class<?>... classes) {
        this(new ArrayList<>(Arrays.asList(classes)));
    }

    public List<Class<?>> extractController() {
        return classes.stream()
                .filter(this::isController)
                .collect(Collectors.toList());
    }

    private boolean isController(Class<?> typeToken) {
        return typeToken.isAnnotationPresent(Controller.class);
    }

    public static List<Class<?>> scan(String basePackage) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = basePackage.replace('.', '/');

        List<Class<?>> classes = new ArrayList<>();

        try {
            List<File> files = new ArrayList<>();
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                files.add(new File(resource.getFile()));
            }
            for (File file: files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, basePackage));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                //System.out.println("[Directory] " + file.getAbsolutePath());
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                //System.out.println("[File] " + file.getAbsolutePath());
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                try {
                    classes.add(Class.forName(className, false, classLoader));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return classes;
    }
}
