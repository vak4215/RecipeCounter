package back_end.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import back_end.service.IngredientService;
import back_end.model.Ingredient;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
	private IngredientService ingredientService;

	@Autowired
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}

	@GetMapping("/{name}")
	public ResponseEntity<Ingredient> getIngredient(@PathVariable String name) {
		Ingredient ingredient = ingredientService.getIngredientByName(name);

		if (ingredient != null) {
			return new ResponseEntity<>(ingredient, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("")
	public List<Ingredient> getIngredients() {
		return ingredientService.getIngredients();
	}

	@PutMapping("/{currentName}")
	public ResponseEntity<Ingredient> updateIngredient(@PathVariable String currentName, @RequestBody Ingredient ingredient) {
		Ingredient existingIngredient = ingredientService.getIngredientByName(currentName);

		try {
			if (existingIngredient != null) {
				Ingredient newIngredient = ingredientService.updateIngredient(currentName, ingredient);
				return new ResponseEntity<>(newIngredient, HttpStatus.OK);
			} else {
				Ingredient newIngredient = ingredientService.addIngredient(ingredient);
				return new ResponseEntity<>(newIngredient, HttpStatus.CREATED);
			}
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(existingIngredient, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("")
	public ResponseEntity<String> addIngredient(@RequestBody Ingredient ingredient) {
		try {
			ingredientService.addIngredient(ingredient);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
}