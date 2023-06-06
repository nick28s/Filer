package SWA.Filer;

import SWA.Filer.controller.UserController;
import SWA.Filer.model.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUsers() throws Exception{
        mvc.perform(get("/users/listusers")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].username",is("emir")))
                        .andExpect(jsonPath("$[1].username",is("John")));
    }

    @MockBean
    private UserRequest userRequest;
    @Test
    public void createUser() throws Exception{

        userRequest = new UserRequest("alex","pass123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(userRequest);

        mvc.perform(post("/users")
                        .header("username","admin")
                        .header("password","admin1234")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User created successfully."));
    }

    @Test
    public void updateUser() throws Exception{

        userRequest = new UserRequest("newname","pass123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(userRequest);

        mvc.perform(put("/users/22")//userID
                        .header("username","admin")
                        .header("password","admin1234")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User updated successfully."));
    }

    @Test
    public void deleteUser() throws Exception{
        mvc.perform(delete("/users/26")//userID
                        .header("username","admin")
                        .header("password","admin1234")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("User deleted successfully."));
    }
}