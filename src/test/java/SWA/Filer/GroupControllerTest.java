package SWA.Filer;

import SWA.Filer.controller.GroupController;
import SWA.Filer.model.GroupRequest;
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
@WebMvcTest(GroupController.class)
public class GroupControllerTest {

    @Autowired
    private MockMvc mvc;
    @Test
    public void getGroups() throws Exception {
        mvc.perform(get("/groupp/listgroups")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[1].name",is("TIB")))
                        .andExpect(jsonPath("$[2].name",is("SWB")));
    }
    @MockBean
    private GroupRequest groupRequest;
    @Test
    public void createGroup() throws Exception{

        GroupRequest GroupRequest = new GroupRequest("alex_gruppe");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(GroupRequest);

        mvc.perform(post("/groupp")
                        .header("username","admin")
                        .header("password","admin1234")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Group created successfully."));
    }

    @Test
    public void updateGroup() throws Exception{

         groupRequest = new GroupRequest("new");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(groupRequest);

        mvc.perform(put("/groupp/28")
                        .header("username","admin")
                        .header("password","admin1234")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Group updated successfully."));
    }

    @Test
    public void deleteGroup() throws Exception{
        mvc.perform(delete("/groupp/23")//userID
                        .header("username","admin")
                        .header("password","admin1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Group deleted successfully."));
    }
}


