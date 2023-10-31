package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        logger.debug(Arrays.toString(clazz.getDeclaredFields()));
        logger.debug(Arrays.toString(clazz.getDeclaredFields()));
        logger.debug(Arrays.toString(clazz.getDeclaredFields()));
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        User user = clazz.getConstructor(String.class, String.class, String.class, String.class).newInstance("sjh", "1234", "홍석주", "test@test.com");

        System.out.println(user);

        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            Parameter[] parameters = declaredConstructor.getParameters();
            System.out.println(Arrays.toString(parameters));
            User o = (User) declaredConstructor.newInstance("sjh", "1234", "홍석주", "test@test.com");
            System.out.println(o);
        }
    }

    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = new Student();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.getType() == String.class) {
                declaredField.set(student, "홍석주");
            } else if (declaredField.getType() == int.class) {
                declaredField.set(student, 15);
            }
        }

        System.out.println(student.getAge() + " " + student.getName());
    }
}
