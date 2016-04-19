import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author weixing
 */
public class UserController extends Controller {

    public Result validateUser(String username) {
        return ok("Hello " + username);
    }
}
