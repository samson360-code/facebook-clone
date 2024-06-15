package facebook.facebookwebpage;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String regester() {
        return "registration";
    }

    @PostMapping("/register")
    public ModelAndView signUp(User user) {
        Optional<User> opUser = userRepository.findById(user.getusername());
        if (opUser.isPresent()) {
            ModelAndView model = new ModelAndView("/register");
            model.addObject("message", "user already exist  click login");
            return model;
        } else {
            ModelAndView model = new ModelAndView("/profile");
            User registerd = userRepository.save(user);
            model.addObject("signed", registerd);
            return model;

        }
    }

    @PostMapping("/home")
    public ModelAndView homepageview(@ModelAttribute("username") String username) {
        Optional<User> opUser = userRepository.findById(username);
        ModelAndView model = new ModelAndView("/home");
        if (opUser.isPresent()) {
            User existedUser = opUser.get();
            model.addObject("user", existedUser);
        }
        return model;
    }

    @PostMapping("/login")
    public ModelAndView login(User user) {
        Optional<User> opUser = userRepository.findById(user.getusername());
        if (opUser.isPresent()) {
            User existedUser = opUser.get();
            if (existedUser.getpassword().equals(user.getpassword())) {
                ModelAndView model = new ModelAndView("/home");
                model.addObject("user", user);
                return model;
            } else {
                ModelAndView model = new ModelAndView("/login");
                model.addObject("message", "password doesnt match");
                return model;
            }
        } else {
            ModelAndView model = new ModelAndView("/login");
            model.addObject("message", "user doesnt exist");
            return model;
        }
    }

    @GetMapping("/profile")
    public ModelAndView profile(@ModelAttribute("username") String username) {
        ModelAndView model = new ModelAndView("/profile");
        Optional<User> opuser = userRepository.findById(username);
        if (opuser.isPresent()) {
            User user = opuser.get();
            model.addObject("signed", user);
        }
        return model;
    }

    @PostMapping("/update")
    public ModelAndView update(String username, String image) {
        ModelAndView model = new ModelAndView("/profile");
        Optional<User> opuser = userRepository.findById(username);
        if (opuser.isPresent()) {
            User user = opuser.get();
            user.setimage(image);
            userRepository.save(user);
            model.addObject("succesfuly", "saved successfully");
            model.addObject("signed", user);
        }
        return model;
    }

    @PostMapping("/delete")
    public ModelAndView delete(String username) {
        ModelAndView model = new ModelAndView("/login");
        userRepository.deleteById(username);
        return model;
    }
}
