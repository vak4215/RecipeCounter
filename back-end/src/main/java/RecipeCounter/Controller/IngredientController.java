package main.java.RecipeCounter.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

	@GetMapping("/{id}")
	public String getIngredient(@PathVariable Long id) {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("")
	public String getIngredients() {
		return "Greetings from Spring Boot!";
	}

}