# yamlparser
This is the library to parse a yml file to a POJO.
It can also resolve the environment variables.

Add ```yamlparser``` as a dependency:

```
<dependency>
  <groupId>com.github.vivekkothari</groupId>
  <artifactId>yamlparser</artifactId>
  <version>0.0.2</version>
</dependency>
```

Code examples:

```
Config config = YamlParser.loadWithEnvironmentResolution(new FileInputStream("config.yml"), Config.class);
```

The above code will parse the ```config.yml``` file and map it to the ```Config```.
If there are any environment variables, defined as ```dbHost: ${DB_HOST}```, it will try to resolve it and fail if it fails to resolve it.

Libraries used:
```
Snake YML
Commons Lang
```
