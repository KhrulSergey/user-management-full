package com.khsa.usermanagement.controller;

import com.khsa.usermanagement.domain.model.User;
import com.khsa.usermanagement.service.UserService;
import com.khsa.usermanagement.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Контроллер управления сущностью "Пользователи"
 */
@RestController
@RequestMapping(path = UserController.BASE_PATH)
public class UserController {

    public static final String BASE_PATH = "/user";
    public static final String PATH_ADD = "/add/";
    public static final String PATH_GET = "/get/{id}";
    public static final String PATH_DELETE = "/delete/{id}";
    public static final String PATH_LIST = "/list";
    public static final String PATH_EDIT = "/edit/";

    public static final String WEB_PATH_ADD = "/register";
    public static final String WEB_PATH_SAVE = "/save";
    public static final String WEB_PATH_GET = "/one/{id}";
    public static final String WEB_PATH_DELETE = "/remove/{id}";
    public static final String WEB_PATH_LIST = "/all";
    public static final String WEB_PATH_EDIT = "/modify";

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Добавление пользователя в Систему
     *
     * @param user добавляемый пользователь
     * @return результат выполнения операции в JSON формате. При ошибке возвращает 404ую ошибку
     */
    @PostMapping(path = PATH_ADD)
    public ResponseEntity<ResponseMessage> add(@RequestBody User user) {
        try {
            long id = userService.add(user).getId();
            URI location = ServletUriComponentsBuilder.fromPath(PATH_ADD)
                    .buildAndExpand(id)
                    .toUri();
            return ResponseEntity.created(location)
                    .body(new ResponseMessage());
        } catch (ConstraintViolationException e) {
            Set<String> errors = e.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            return new ResponseEntity<>(new ResponseMessage(errors), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = WEB_PATH_ADD)
    protected ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userNew", new User());
//        modelAndView.addObject("genderList", Gender.values());
        modelAndView.setViewName("user/register");
        return modelAndView;
//        return "user/register";
    }

    @PostMapping(path = WEB_PATH_SAVE)
    protected ModelAndView registerNewUser(HttpServletRequest request, @ModelAttribute("userNew") User user,
                                           BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("user/register");
            mav.addObject("userNew", user);
            return mav;
            //return new ModelAndView("register-user", "user", user);
        }

        User u = userService.add(user);
        request.getSession().setAttribute("user", u);
        //TODO return to prev page or home
        return new ModelAndView("account-success", "user", u);

//        } catch (UserException e) {
//            System.out.println("Exception: " + e.getMessage());
//            return new ModelAndView("error", "errorMessage", "error while login");
//        }
    }


    /**
     * Получение одного пользователя из системы
     *
     * @param id идентификатор для получения данных
     * @return результат выполнения операции в JSON формате. При ошибке возвращает 404ую ошибку
     */
    @GetMapping(path = PATH_GET)
    public ResponseEntity<User> get(@PathVariable("id") long id) {
        User user = userService.get(id);
        return Objects.nonNull(user) ?
                new ResponseEntity<>(user, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Получение списка пользователей из системы
     *
     * @param pageable параметр пагинации выдачи результата
     * @return результат выполнения операции в JSON формате. При ошибке возвращает 404ую ошибку
     */
    @GetMapping(path = PATH_LIST)
    public ResponseEntity<Page<User>> list(@PageableDefault Pageable pageable) {
        Page<User> users = userService.list(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = WEB_PATH_LIST)
    public ModelAndView showAllUsers(Model model, @PageableDefault Pageable pageable) {
        try {
            Page<User> users = userService.list(pageable);
            model.addAttribute("users", users);
            return new ModelAndView("user/userlist", "users", users);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return new ModelAndView("error", "errorMessage", "error while login");
        }
    }

    /**
     * Обновление данных пользователя
     *
     * @param user данные пользователя для обновления
     * @return результат выполнения операции в JSON формате. При ошибке возвращает 404ую ошибку
     */
    @PutMapping(path = PATH_EDIT)
    public ResponseEntity<ResponseMessage> edit(@RequestBody User user) {
        try {
            userService.edit(user);
            return ResponseEntity.ok(new ResponseMessage());

        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(e.getConstraintViolations()
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toSet())));
        }
    }

    // TODO update page
    @RequestMapping(path = "/update/{id}")
    public ModelAndView edit(@PathVariable Integer id, Model model) {
        User user = userService.get(id);
        model.addAttribute("user", user);
        return new ModelAndView("userform", "user", user);
    }

    // TODO update page
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ModelAndView edit(User user, Model model) {

        try {
            User updatedUser = userService.edit(user);
            return new ModelAndView("users");
        } catch (ConstraintViolationException e) {
            return new ModelAndView("error", "errors", e.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet()));
        }
    }

    /**
     * Удаление пользователя из Системы
     *
     * @param id идентификатор пользователя для удаления
     * @return результат выполнения операции в JSON формате. При ошибке возвращает 404ую ошибку
     */
    @DeleteMapping(path = PATH_DELETE)
    public ResponseEntity<ResponseMessage> delete(@PathVariable("id") long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok(new ResponseMessage());
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
