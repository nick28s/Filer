package SWA.Filer;

import SWA.Filer.controller.FileListController;
import SWA.Filer.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FileListController.class)
public class FileControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllFiles() throws Exception{
        mvc.perform(get("/files/listallfiles")
                        .header("username","admin")
                        .header("password","admin1234")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].fileName",is("Download.png")))
                        .andExpect(jsonPath("$[1].fileName",is("decToASCII.png")));
    }

    @Test
    public void getFiles() throws Exception{
        mvc.perform(get("/files/13/listfiles")//userID 13
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].fileName",is("Download.png")))
                        .andExpect(jsonPath("$[1].fileName",is("decToASCII.png")));
    }
}