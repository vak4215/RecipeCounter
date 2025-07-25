package back_end.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;

import back_end.service.IngredientService;
import back_end.model.Ingredient;

@SpringBootTest
@AutoConfigureMockMvc
public class IngredientControllerTest {
    @Autowired
	private MockMvc mvc;

	@Autowired
	private IngredientService ingredientService;

	@Test
	public void testGetIngredient() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/ingredients/all-purpose flour"))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"name\":\"all-purpose flour\",\"calories\":110,\"servingSize\":0.25,\"servingUnit\":\"CUP\"}"));
	}

	@Test
	public void testGetIngredientNotFound() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/ingredients/a made up non-existent ingredient"))
			.andExpect(status().isNotFound());
	}
    
	@Test
	public void testGetAllIngredients() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/ingredients"))
			.andExpect(status().isOk());
	}

	@Test
	public void testCreateExistingIngredient() throws Exception {
		List<Ingredient> ingredients = ingredientService.getIngredients();

		mvc.perform(MockMvcRequestBuilders.post("/ingredients")
			.contentType("application/json")
			.content(ingredients.get(0).toString())
		)
		.andExpect(status().isConflict());
	}

	@Test
	public void testCreateNewIngredient() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/ingredients")
			.contentType("application/json")
			.content("{\"name\":\"test\",\"calories\":110,\"servingSize\":0.25,\"servingUnit\":\"CUP\"}")
		)
		.andExpect(status().isCreated());

		// TODO : find a better cleanup mechanism
		ingredientService.deleteIngredient("test");
	}

	/*
	 * Tests the flow of creating an ingredient, then updating it.
	 */
	@Test
	public void testUpdateIngredient() throws Exception {
		// Ingredient doesn't exist yet, so the post request should create it
		mvc.perform(MockMvcRequestBuilders.put("/ingredients/test-put-1")
			.contentType("application/json")
			.content("{\"name\":\"test-put-1\",\"calories\":110,\"servingSize\":0.25,\"servingUnit\":\"CUP\"}")
		)
		.andExpect(status().isCreated());

		// Ingredient exists now, so the put request should update it
		mvc.perform(MockMvcRequestBuilders.put("/ingredients/test-put-1")
			.contentType("application/json")
			.content("{\"name\":\"TEST-put-2\",\"calories\":1234,\"servingSize\":2.22,\"servingUnit\":\"TEASPOON\"}")
		)
		.andExpect(status().isOk())
		.andExpect(content().json("{\"name\":\"test-put-2\",\"calories\":1234,\"servingSize\":2.22,\"servingUnit\":\"TEASPOON\"}"));
	
		// An ingredient with the original name should not be found
		mvc.perform(MockMvcRequestBuilders.get("/ingredients/test-put-1"))
			.andExpect(status().isNotFound());

		// TODO : find a better cleanup mechanism
		ingredientService.deleteIngredient("test-put-2");
	}

}
