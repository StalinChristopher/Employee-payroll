package com.yml.employeepayrolltest;
import com.yml.emloyeepayroll.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollTest {
	private static String HOME = System.getProperty("user.home");
	private static String PLAY_WITH_NIO = "TempPlayGround";

	@Test
	public void givenPathWhenCheckedThenConfirm() throws IOException {
		// Check if the File exists
		Path homePath = Paths.get(HOME);
		Assert.assertTrue(Files.exists(homePath));

		// Delete File and check file not exists
		Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
		if (Files.exists(playPath))
			FileUtils.deleteFiles(playPath.toFile());
		Assert.assertTrue(Files.notExists(playPath));

		// Create Directory
		Files.createDirectory(playPath);
		Assert.assertTrue(Files.exists(playPath));

		// Create File
		IntStream.range(1, 10).forEach(cntr -> {
			Path tempFile = Paths.get(playPath + "/temp" + cntr);
			try {
				Files.createFile(tempFile);
			} catch (IOException E) {
			}
			Assert.assertTrue(Files.exists(tempFile));
		});
		// listing of files
		Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);

	}
}
