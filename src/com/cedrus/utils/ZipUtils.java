package com.cedrus.utils;

import com.cedrus.logger.ApplicationLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @file ZipUtils.java
 * @note Copyright (c) 2016 SoftServe, Inc. Confidential and proprietary. All
 *       rights reserved.
 * @author Pavlo Antentyk (panten@softserveinc.com)
 * @since 2016.09.27
 */


public class ZipUtils {

	private static final ApplicationLogger LOGGER = new ApplicationLogger(ZipUtils.class);

	private List<String> fileList;
	private final String OUTPUT_ZIP_FILE;
	private final String SOURCE_FOLDER;

	public ZipUtils(String src, String dest) {
		OUTPUT_ZIP_FILE = dest;
		SOURCE_FOLDER = src;
		fileList = new ArrayList<String>();
	}

	/**
	 * @param createParentFolder
	 *            if false then archive will be created without parent folder
	 * 
	 * @return
	 */
	public boolean createZip(boolean createParentFolder) {
		generateFileList(new File(SOURCE_FOLDER));
		String source = "";
		if (createParentFolder) {
			try {
				source = SOURCE_FOLDER.substring(SOURCE_FOLDER.lastIndexOf("/") + 1, SOURCE_FOLDER.length());
			} catch (Exception e) {
				source = SOURCE_FOLDER;
			}
		}

		return zipIt(OUTPUT_ZIP_FILE, source);
	}

	private boolean zipIt(String zipFile, String source) {
		boolean result = true;
		byte[] buffer = new byte[1024];

		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {

			fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);

			FileInputStream in = null;

			for (String file : this.fileList) {
				ZipEntry ze = new ZipEntry(source + File.separator + file);
				zos.putNextEntry(ze);
				try {
					in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				} catch (IOException e) {
					LOGGER.error("Error during creating zip " + e.getMessage());
					result = false;
				} finally {
					in.close();
				}
			}

			zos.closeEntry();

		} catch (Exception ex) {
			LOGGER.error("Error during creating zip " + ex.getMessage());
			result = false;
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				LOGGER.error("Error closing ZipOutputStream " + e.getMessage());
			}
		}
		return result;
	}

	private void generateFileList(File node) {

		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.toString()));

		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename));
			}
		}
	}

	private String generateZipEntry(String file) {
		return file.substring(SOURCE_FOLDER.length() + 1, file.length());
	}

	public static boolean unZipIt(String zipFile, String outputFolder) {
		boolean result = true;
		byte[] buffer = new byte[1024];
		ZipInputStream zis = null;
		FileOutputStream fos = null;
		try {

			File folder = new File(outputFolder);
			if (!folder.exists()) {
				folder.mkdir();
			}

			zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();
				fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

		} catch (Exception ex) {
			LOGGER.error("Error during extracting zip " + ex.getMessage());
			result = false;
		} finally {
			try {
				if (zis != null) {
					zis.closeEntry();
				}
			} catch (IOException e) {
				LOGGER.error("Error closing ZipInputStreamEntry " + e.getMessage());
			}
			try {
				if (zis != null) {
					zis.close();
				}
			} catch (IOException e) {
				LOGGER.error("Error closing ZipInputStream " + e.getMessage());
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				LOGGER.error("Error closing FileOutputStream " + e.getMessage());

			}
		}
		return result;
	}

}