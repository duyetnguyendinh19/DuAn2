package com.vn.common;

import com.google.common.base.Strings;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils {
    public static Result executeStorage(String uploadingDirectory,
                                        String fileName, String base64Encode,
                                        boolean isAllowOverRide, boolean isUniqueNameFile, String webContextPath) {
        Result result = new Result();
        boolean isSaveWebResourcePath = (webContextPath != null);
        try {
            byte[] imageByte = Base64.decodeBase64(base64Encode);
            setupDirectoryStorage(uploadingDirectory);
            String filePath;
            String webResourcePath = "";
            filePath = uploadingDirectory + fileName;
            if (isSaveWebResourcePath) {
                webResourcePath = webContextPath + fileName;
            }
            if (!isUniqueNameFile) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
                String nowFormat = sdf.format(new Date());
                String fileNameCustom = FilenameUtils.removeExtension(fileName) +
                        '_' + nowFormat + '.' + FilenameUtils.getExtension(fileName);
                filePath = uploadingDirectory + fileNameCustom;
                if (isSaveWebResourcePath) {
                    webResourcePath = webContextPath + fileNameCustom;
                }
            }
            File file = new File(filePath);
            if (file.exists() && !isAllowOverRide) {
                result.setSuccess(false);
                result.setResult(String.format(Result.EXISTED_FILE_ERROR, fileName));
                return result;
            } else if (file.exists() && isAllowOverRide) {
                file.delete();
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(imageByte);
            outputStream.close();
            result.setSuccess(true);
            result.setResult(isSaveWebResourcePath ? webResourcePath : filePath);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setResult(ex.toString());
        }
        return result;
    }

    public static Result executeStorage(String uploadingDirectory,
                                        DescriptionBase64File descriptionBase64File,
                                        boolean isAllowOverRide, boolean isUniqueNameFile, String webContextPath) {
        String base64Encode = descriptionBase64File.getContent();
        String fileName = descriptionBase64File.getFileName();
        return executeStorage(uploadingDirectory, fileName,
                base64Encode, isAllowOverRide, isUniqueNameFile, webContextPath);
    }

    public static Result executeStorage(String uploadingDirectory,
                                        DescriptionBase64File[] descriptionBase64Files,
                                        boolean isAllowOverRide, boolean isUniqueNameFile, String webContextPath) {
        boolean isSuccessAll = true;
        String pathImages = "[";
        for (DescriptionBase64File descriptionBase64File : descriptionBase64Files) {
            Result result = executeStorage(uploadingDirectory, descriptionBase64File,
                    isAllowOverRide, isUniqueNameFile, webContextPath);
            if (result.isSuccess == false) {
                isSuccessAll = false;
                break;
            } else {
                pathImages += "\"" + result.getResult() + "\"" + ",";
            }
        }
        if (isSuccessAll && pathImages.endsWith(",")) {
            pathImages = pathImages.substring(0, pathImages.length() - 1);
        }
//        pathImages = pathImages.substring(0, pathImages.length() - 1);
        pathImages += "]";
        Result result = new Result();
        result.setSuccess(isSuccessAll);
        result.setResult(pathImages);
        return result;
    }

    public static Result storageFile(String uploadingDirectory,
                                     DescriptionBase64File descriptionBase64File,
                                     boolean isAllowOverRide, boolean isUniqueNameFile) {
        return executeStorage(uploadingDirectory, descriptionBase64File, isAllowOverRide,
                isUniqueNameFile, null);
    }

    public static Result storageFile(String uploadingDirectory,
                                     DescriptionBase64File descriptionBase64File,
                                     boolean isAllowOverRide, boolean isUniqueNameFile, String webContextPath) {
        return executeStorage(uploadingDirectory, descriptionBase64File, isAllowOverRide,
                isUniqueNameFile, webContextPath);
    }

    public static Result storageFile(String uploadingDirectory,
                                     DescriptionBase64File[] descriptionBase64Files,
                                     boolean isAllowOverRide, boolean isUniqueNameFile) {
        return executeStorage(uploadingDirectory, descriptionBase64Files,
                isAllowOverRide, isUniqueNameFile, null);

    }

    public static Result storageFile(String uploadingDirectory,
                                     DescriptionBase64File[] descriptionBase64Files,
                                     boolean isAllowOverRide, boolean isUniqueNameFile, String webContextPath) {
        return executeStorage(uploadingDirectory, descriptionBase64Files,
                isAllowOverRide, isUniqueNameFile, webContextPath);

    }

    public static void setupDirectoryStorage(String directory) {
        File uploadRootDir = new File(directory);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
    }

    public static class Result {
        private static final String EXISTED_FILE_ERROR = "File %s đã tổn tại, xin đổi tên";
        private static final String SERVER_ERROR = "Server xử lý thất bại";
        private boolean isSuccess;
        private String result;

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    public static class DescriptionBase64File {
        private String fileName;
        private String content;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static boolean deleteSingleImage(String filePath) {
        boolean result = false;
        if (!Strings.isNullOrEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                result = file.delete();
            }
        }

        return result;
    }

    public static String changeWebSourcePathToFilePath(String filePath, String rootFolderPath, String sourceWebPath) {
        String result = filePath;
        if (!Strings.isNullOrEmpty(filePath)) {
            if (filePath.contains(sourceWebPath)) {
                filePath = filePath.replace(sourceWebPath, "");
                result = filePath;
            }
            if (!filePath.contains(rootFolderPath)) {
                result = rootFolderPath + filePath;
            }
        }
        return result;
    }

    public static List<String> getImgPathFromString(String input) {
        List<String> result = new ArrayList<>();
        if (input != null && !input.trim().isEmpty()) {
            if (input.startsWith("[")) {
                input = input.substring(1);
            }
            if (input.endsWith("]")) {
                input = input.substring(0, input.length() - 1);
            }
            String[] inputArray = input.split(",");
            for (String string : inputArray) {
                if (string != null && !string.trim().isEmpty()) {
                    string = string.replace("\"", "");
                    result.add(string);
                }
            }
        }
        return result;
    }
}
