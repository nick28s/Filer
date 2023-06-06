package SWA.Filer;

import SWA.Filer.controller.DirectoriesController;
import SWA.Filer.controller.UserController;
import SWA.Filer.model.DirectoriesRequest;
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
@WebMvcTest(DirectoriesController.class)
public class DirectoriesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getDirectories() throws Exception{
        mvc.perform(get("/directories/listdirectories")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].name",is("test")))
                        .andExpect(jsonPath("$[1].name",is("test2")));
    }

   @MockBean
    private DirectoriesRequest directoriesRequest;
    @Test
    public void updateDirectory() throws Exception{

        directoriesRequest = new DirectoriesRequest("newName");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(directoriesRequest);

        mvc.perform(put("/directories/16")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Directory updated successfully."));
    }

    @Test
    public void createDirectory() throws Exception{

        directoriesRequest = new DirectoriesRequest("newDirectory");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(directoriesRequest);

        mvc.perform(post("/directories")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Directory created successfully."));
    }

    @Test
    public void deleteDirectory() throws Exception{
        mvc.perform(delete("/directories/17")//directoryID
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Directory deleted successfully."));
    }
}