package core.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import next.controller.ControllerScanner;
import next.controller.qna.ShowEditController;
import next.controller.HomeController;
import next.controller.qna.*;
import next.controller.user.CreateUserController;
import next.controller.user.ListUserController;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import next.controller.user.ProfileController;
import next.controller.user.UpdateFormUserController;
import next.controller.user.UpdateUserController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        mappings.put("/", new HomeController());
//        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
//        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
//        mappings.put("/users", new ListUserController());
//        mappings.put("/users/login", new LoginController());
//        mappings.put("/users/profile", new ProfileController());
//        mappings.put("/users/logout", new LogoutController());
//        mappings.put("/users/create", new CreateUserController());
//        mappings.put("/users/updateForm", new UpdateFormUserController());
//        mappings.put("/users/update", new UpdateUserController());
//        mappings.put("/qna/form", new ForwardController("/qna/form.jsp"));
//        mappings.put("/qna/show", new ShowController());
//        mappings.put("/qna/create", new AddQuestionController());
//        mappings.put("/qna/edit/show", new ShowEditController());
//        mappings.put("/qna/edit", new EditController());
//        mappings.put("/api/qna/addAnswer", new AddAnswerController());
//        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());
//        mappings.put("/api/qna/list", new MobileQnaShowController());

        logger.info("Initialized Request Mapping!");

        List<Class<?>> classes = ControllerScanner.scan("next.controller");
        for (Class<?> aClass : classes) {
            if (aClass.isAnnotationPresent(next.controller.Controller.class)) {
                next.controller.Controller annotation = aClass.getAnnotation(next.controller.Controller.class);
                Constructor<?> constructor = aClass.getConstructor();
                mappings.put(annotation.value(), (Controller) constructor.newInstance());
            }
        }
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

}
