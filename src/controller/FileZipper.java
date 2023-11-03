package controller;

import java.io.*;
import java.util.zip.*;

public class FileZipper {



    public void zipFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the path to files to be zipped: ");
            String pathSrc = reader.readLine();
            System.out.print("Enter the zip file name: ");
            String fileZipName = reader.readLine();
            System.out.print("Enter the path to store the zip file: ");
            String pathCompress = reader.readLine();

            boolean result = compressTo(pathSrc, fileZipName, pathCompress);
            if (result) {
                System.out.println("Files zipped successfully!");
            } else {
                System.out.println("Error while zipping files.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unzipFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the path to the zip file: ");
            String pathZipFile = reader.readLine();
            System.out.print("Enter the path to extract the files: ");
            String pathExtract = reader.readLine();

            boolean result = extractTo(pathZipFile, pathExtract);
            if (result) {
                System.out.println("Files unzipped successfully!");
            } else {
                System.out.println("Error while unzipping files.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean compressTo(String pathSrc, String fileZipName, String pathCompress) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(pathCompress, fileZipName));
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            File fileToZip = new File(pathSrc);

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            }
            zipOut.closeEntry();
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    public boolean extractTo(String pathZipFile, String pathExtract) {
        try {
            File file = new File(pathZipFile);
            File destDir = new File(pathExtract);

            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            byte[] buffer = new byte[1024];
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file));
            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                String filePath = pathExtract + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath, buffer);
                } else {
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void extractFile(ZipInputStream zipIn, String filePath, byte[] buffer) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        int len;
        while ((len = zipIn.read(buffer)) > 0) {
            bos.write(buffer, 0, len);
        }
        bos.close();
    }
}
