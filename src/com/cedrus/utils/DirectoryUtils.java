package com.cedrus.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;

public class DirectoryUtils {

	private Collection<URL> fileList;

	public DirectoryUtils() {
		fileList = new ArrayList<URL>();
	}

	public Collection<URL> getFiles() {
		return fileList;
	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}

	public void scanDirectory(File file, String filter) throws MalformedURLException, FileNotFoundException {
		File[] files = file.listFiles();
		if (files == null) {
			throw new FileNotFoundException();
		}
		for (File f : files) {
			if (f.isFile() && f.getName().endsWith(filter)) {
				fileList.add(f.toURI().toURL());
			}
			if (f.listFiles() != null) {
				scanDirectory(f, filter);
			}
		}
	}

	public static boolean deleteDirectory(File directory) {
		try {
			Files.walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static boolean cleanFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isDirectory()) {
					if(!cleanFolder(f)) {
						return false;
					}
				} else {
					if(!f.delete()) {
						return false;
					}
				}
			}
		}
		return true;
	}
}