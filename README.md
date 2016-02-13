# File Comparator
This program is intended to compare the differences between two text files within which each line is a key-value pair.
## Build the project
This is a Java project, after you've cloned it, go to your workspace and run the following command: <br/>
`mvn clean package` <br/>
It'll generate a `Diff.jar` file in the `target` folder under your workspace, which you may copy it anywhere you want.
## Usage
The default separator of the key-value in a line is white space, which includes both space character or tab character. You may specify any other string as the separator by define -Dseparator=...
### Syntax
Assume you have packaged this program into an executable jar `Diff.jar`: <br/>
[1] `java -jar Diff.jar [options] <file1> <file2>` <br/>
[2] `java -jar Diff.jar [options] <directory>` <br/>
*Note*: the directory you choose to compare must contains exactly TWO files. <br/>
Options include: <br/>
`-h -help --help`	: show usage info. <br/>
`-Dseparator=...`	: defines the separator other than the default space character. <br/>
`-Dout=...`	: defines the output file to store the result. <br/>
###Examples:
`java -jar Diff.jar a.txt b.txt` 	// Compare the two files a.txt and b.txt <br/>
`java -jar Diff.jar foo/bar` 	    // Compare the two files in the directory foo/bar <br/>
`java -jar Diff.jar .` 		        // Compare the two files in current directory <br/>
`java -jar Diff.jar . -Dseparator=:` 	    // Defines the separator of ':' <br/>
`java -jar Diff.jar . -Dout=result.txt` 	// Defines the output file 'result.txt' to store the comparing result <br/>
<br/>

Any questions or suggestions please feel free to reach the author `Wade Pei` at wadepei@163.com <br/>
Thanks. <br/>
