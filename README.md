# File Comparator
This program is intended to compare the differences between two text files within which each line is a key-value pair.
## Build the project
This is a Java project, after you've cloned it, go to your workspace and run the following command:
`mvn clean package`
It'll generate a `Diff.jar` file in the `target` folder under your workspace, which you may copy it anywhere you want.
## Usage
The default separator of the key-value in a line is white space, which includes both space character or tab character. You may specify any other string as the separator by define -Dseparator=...
### Syntax
Assume you have packaged this program into an executable jar `Diff.jar`:
[1] java -jar Diff.jar [options] <file1> <file2>
[2] java -jar Diff.jar [options] <directory>
Options include:
-h -help --help	: show usage info.
-Dseparator=...	: defines the separator other than the default space character.
-Dout=...	: defines the output file to store the result.
###Examples:
java -jar Diff.jar a.txt b.txt 	// Compare the two files a.txt and b.txt
java -jar Diff.jar foo/bar 	// Compare the two files in the directory foo/bar
java -jar Diff.jar . 		// Compare the two files in current directory
java -jar Diff.jar . -Dseparator=: 	// Defines the separator of ':'
java -jar Diff.jar . -Dout=result.txt 	// Defines the output file 'result.txt' to store the comparing result


