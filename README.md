# Wall-e RoboRally 
Simple skeleton with libgdx. 


## Known bugs
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

### Working directory
To run the game and tests, working directory needs to be set to `$PROJECT_DIR/core/assets`.
E.g., in IntelliJ IDEA, edit run configurations and set **Working directory** to `/IdeaProjects/WALL-E/core/assets`

## Running
Make sure [working directory](#working-directory) has been configured correctly.
To run the game, run Main.main().

## Testing
Make sure [working directory](#working-directory) has been configured correctly.
Tests are located at `src/test/java/`, and are ran with JUnit.
