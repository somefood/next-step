package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method declaredMethod : declaredMethods) {
            declaredMethod.invoke(clazz.getConstructor().newInstance());
//            declaredMethod.invoke(clazz.newInstance()); // 이 방법은 deprecated됨
        }
    }
}
