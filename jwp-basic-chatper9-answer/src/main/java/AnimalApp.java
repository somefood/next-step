import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class AnimalApp {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<Dog> dogClass = Dog.class;

        System.out.println(dogClass.getSimpleName());

        System.out.println(Arrays.toString(dogClass.getMethods()));
        System.out.println(Arrays.toString(dogClass.getDeclaredMethods()));

        System.out.println(Arrays.toString(dogClass.getConstructors()));
        System.out.println(Arrays.toString(dogClass.getDeclaredConstructors()));

        System.out.println(Arrays.toString(dogClass.getAnnotations()));
        System.out.println(Arrays.toString(dogClass.getDeclaredAnnotations()));

        System.out.println(Arrays.toString(dogClass.getFields()));
        System.out.println(Arrays.toString(dogClass.getDeclaredFields()));

        Constructor<Dog> constructor = dogClass.getConstructor(int.class, String.class);
        Dog dog = constructor.newInstance(24, "뭉망이");

        System.out.println(dog);
    }
}
