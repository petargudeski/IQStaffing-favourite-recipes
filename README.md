#**IQStaffing-favourite-recipes**
This is an application that allow customers to manage their favourite recipes.

##Swagger

### Local
http://localhost:8080/swagger-ui/index.html

**locally**
```
mvn clean package -DskipTests
```

### Run
```
java "-Dspring.profiles.active=local" -jar "IQStaffing-favourite-recipes/target/favourite-recipes-1.0.0.jar"
```

####ENUMS
```
Category { NONE, VEGAN, VEGETARIAN, GLUTEN_FREE }
Difficulty { EASY, MEDIUM, HIGH }
Unit { WHOLE, COUNT, HANDFUL }
```

##Create Recipe JSON
** POST http://localhost:8080/api/recipes
```
{
    "name": "Test name 1",
    "numberOfServings": 2,
    "difficulty": "EASY",
    "category": "VEGAN",
    "note": {
        "note": "Test Note 1"
    },
    "instruction": {
        "id": 1, //if we want to create new instruction together with recipe, then remove the id, otherwise use id to use existing instruction.
        "instruction": "Test Instruction 1"
    },
    "recipeIngredients": [
        {
            "ingredient": {
                "id": 1, //if we want to create new ingredient together with recipe, then remove the id, otherwise use id to use existing ingredient.
                "name": "Test name 1"
            },
            "unit": "COUNT",
            "quantity": 2
        },
        {
            "ingredient": {
                "name": "Test name 2"
            },
            "unit": "WHOLE",
            "quantity": 3
        }
    ]
}
```

##Update Recipe JSON
** PUT http://localhost:8080/api/recipes/{recipeId} 
```
{
    "name": "Test name 111",
    "numberOfServings": 111,
    "difficulty": "EASY",
    "category": "VEGAN",
    "note": {
        "note": "Test Note 111"
    },
    "instruction": {
        "instruction": "Test Instruction 111"
    },
    "recipeIngredients": [
        {
            "id": { //we must add the id for Composite Key
                "recipeId": 1,
                "ingredientId": 1
            },
            "ingredient": {  
                "name": "Test Test 11"
            },
            "unit": "COUNT",
            "quantity": 22
        },
        {
            "id": {
                "recipeId": 1,
                "ingredientId": 2
            },
            "ingredient": {
                "name": "Test Test 222"
            },
            "unit": "HANDFUL",
            "quantity": 222
        }
    ]
}
```

##Update Recipe JSON
** POST http://localhost:8080/api/recipes/{recipeId}/ingredient?quantity={quantityNumber}&unit={Unit}
```
[
    {
        "name": "Ingredient 1"
    },
    {
        "name": "Ingredient 2"
    }
]
```