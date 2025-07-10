
# FileFixer

This project is a java application renames files to confrom with the upload format for MyElearning.


## Running

Download or clone the git repository. Java 8 and Maven are required to run this project.
Navigate to the directory:

```
...FileFixer\filefixer
```

The project can be built and tests run automatically using:

```bash
 mvn verify
```
Tests can also be run separately by using:
```bash
mvn test
```
The PDFs and CSV file must be in the same directory. Alternatively, a zip file can be used but it must have both the PDFs and CSV.
Files to be renamed should be placed in
```
...\filefixer\filesToRename
```
The App can be run using:
```
mvn compile exec:java -Dexec.mainClass=com.freshers.filefixer.App
```
It can also be run from within the App class file.


## Javadoc
The Javadoc can be found [in the repository here.](https://github.com/KcMentor/FileFixer/tree/main/filefixer/target/site/apidocs)

tn
    
