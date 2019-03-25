# Wall-e RoboRally 
Simple skeleton with libgdx. 


## Known bugs
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

## Compiling

Move to the Assets directory so we are sure the assets files are included:

```bash
cd ./core/assets
```

Then setup using Maven:

```bash
mvn clean dependency:copy-dependencies compile assembly:single  package -f ../../
```

## Running

Assuming you have done the steps in [Compiling](#compiling),
runninng should be as simple as:

```bash
java -jar target/wall-e-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Testing

If you haven't already:
```bash
cd ./core/assets
```

Then run the tests with Maven:

```bash
mvn test -f ../../
```
