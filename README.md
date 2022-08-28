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


##Create Recipe JSON
{
    "name": "Test name 1",
    "numberOfServings": 2,
    "difficulty": "EASY",
    "category": "VEGAN",
    "note": {
        "note": "Test Note 1"
    },
    "instruction": {
        "instruction": "Test Instruction 1"
    },
    "recipeIngredients": [
        {
            "ingredient": {
                "id": 1, //if we want to create new ingredient together with recipe, then remove the id, otherwise use id from existing ingredient.
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