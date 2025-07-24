package RecipeCounter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

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