package instAwarePlanning.translator.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

	public static void writeToFile(List<String> lines, String fileName) throws IOException {

		System.out.println("Writing new domain with institutional knowledge... ");
		/// Path file = Paths.get(packPath + "/domains/" + fileName);
		Path file = Paths.get("domains/" + fileName);
		Files.write(file, lines, Charset.forName("UTF-8"));
		// Append option
		// Files.write(file, lines, Charset.forName("UTF-8"),
		// StandardOpenOption.APPEND);
	}
	
	public static List<String> readExistingDomainAsLines(String existingDomain) {

		Path path = Paths.get("domains/" + existingDomain);
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			throw new Error("Can't read file: " + "domains/" + existingDomain);
		}
	}
	
}
