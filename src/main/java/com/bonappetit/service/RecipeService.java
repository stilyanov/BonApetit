package com.bonappetit.service;

import com.bonappetit.config.UserSession;
import com.bonappetit.model.dto.AddRecipeDTO;
import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.CategoryName;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipeRepository;
import com.bonappetit.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserSession userSession;

    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, CategoryRepository categoryRepository, UserSession userSession) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.userSession = userSession;
    }

    public boolean create(AddRecipeDTO recipeDTO) {
        if (!userSession.isLoggedIn()) {
            return false;
        }

        Optional<User> userId = userRepository.findById(userSession.id());

        if (userId.isEmpty()) {
            return false;
        }

        Optional<Category> byName = categoryRepository.findByName(recipeDTO.getCategory());

        if (byName.isEmpty()) {
            return false;
        }

        Recipe recipe = new Recipe();
        recipe.setName(recipeDTO.getName());
        recipe.setIngredients(recipeDTO.getIngredients());
        recipe.setCategory(byName.get());
        recipe.setAddedBy(userId.get());

        recipeRepository.save(recipe);

        return true;
    }

    public Map<CategoryName, List<Recipe>> findAllByCategory() {
        Map<CategoryName, List<Recipe>> result = new HashMap<>();

        List<Category> allCategories = categoryRepository.findAll();

        for (Category category : allCategories) {
            List<Recipe> recipes = recipeRepository.findAllByCategory(category);

            result.put(category.getName(), recipes);
        }

        return result;
    }

    public void addToFavourites(Long id, Long recipeId) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return;
        }

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            return;
        }

        userOptional.get().addFavourite(recipeOptional.get());

        userRepository.save(userOptional.get());
    }
}
