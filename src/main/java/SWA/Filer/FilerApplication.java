package SWA.Filer;

import SWA.Filer.controller.UserController;
import SWA.Filer.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
//@RestController
public class FilerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilerApplication.class, args);}
/*	@GetMapping("/")
	public String hello(){
			return "Hello World";
	}
	*//*test_git_functionalities*/

}
