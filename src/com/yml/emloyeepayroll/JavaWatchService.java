package com.yml.emloyeepayroll;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.*;

public class JavaWatchService {
	private final WatchService watcher;
	private final Map<WatchKey, Path> dirWatchers;

	/**
	 * creates the watcher service
	 * @param dir
	 * @throws IOException
	 */
	public JavaWatchService(Path dir) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.dirWatchers = new HashMap<WatchKey, Path>();
		scanAndRegisterDirectories(dir);
	}

	/**
	 * method to register the directories with watcher service
	 * @param dir
	 * @throws IOException
	 */
	private void registerDirWatchers(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		dirWatchers.put(key, dir);
	}

	/**
	 * method to register the dir and sub-dir
	 * @param start
	 * @throws IOException
	 */
	private void scanAndRegisterDirectories(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				registerDirWatchers(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * method to process all the events for keys queued 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processEvents() {
		while (true) {
			WatchKey Key;
			try {
				Key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}
			Path dir = dirWatchers.get(Key);
			if (dir == null)
				continue;
			for (WatchEvent<?> event : Key.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				Path name = ((WatchEvent<Path>) event).context();
				Path child = dir.resolve(name);
				System.out.format("%s: %s\n", event.kind().name(), child); // print out event

				if (kind == ENTRY_CREATE) {
					try {
						if (Files.isDirectory(child))
							scanAndRegisterDirectories(child);

					} catch (IOException x) {
					}
				} else if (kind.equals(ENTRY_DELETE)) {
					if (Files.isDirectory(child))
						dirWatchers.remove(Key);

				}
			}
			boolean valid = Key.reset();
			if (!valid) {
				dirWatchers.remove(Key);
				if (dirWatchers.isEmpty())
					break;
			}
		}
	}
}
