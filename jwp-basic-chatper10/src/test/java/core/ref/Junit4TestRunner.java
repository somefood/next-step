package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        Method[] declaredMethods = clazz.getDeclaredMethods();

        Junit4Test junit4Test = clazz.getConstructor().newInstance();

        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(MyTest.class)) {
                declaredMethod.invoke(junit4Test);
            }
        }
    }
}
