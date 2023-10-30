import javax.servlet.annotation.WebFilter;

@WebFilter
public class Dog implements Animal {

    private int age;
    private String name;

    public Dog(int age) {
        this(age, "바둑이");
    }

    public Dog(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public void sayHello() {
        System.out.println("안녕하세요 저는 " + name + "이구, 나이는 " + age + "입니다~");
    }

    protected void bark() {
        System.out.println("왈왈왈왈");
    }

    @Override
    public String toString() {
        return "Dog{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
