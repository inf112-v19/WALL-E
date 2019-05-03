# Wall-e RoboRally 
Simple skeleton with libgdx. 

## NB!
Make sure run configurations are set to directory core/assets

## Known bugs
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

## Compiling

Compile everything using Maven:

```bash
mvn clean dependency:copy-dependencies compile assembly:single package
```

## Running

Assuming you have done the steps in [Compiling](#compiling),
running should be as simple as:

```bash
java -jar target/wall-e-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Testing

Run the tests with Maven:

```bash
mvn test
```
