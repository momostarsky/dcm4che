package org.dcm4che3.tool.storescp;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDeletionUtil {
    private static final Logger LOGGER = Logger.getLogger(FileDeletionUtil.class.getName());

    public static void createStorageDirectory(File storageDir) {
        // 检查目录是否已经存在
        if (!storageDir.exists()) {
            // 在尝试创建目录之前记录日志
            LOGGER.info("尝试创建存储目录: " + storageDir.getAbsolutePath());

            // 尝试创建目录，并捕获可能的异常
            try {
                if (storageDir.mkdirs()) {
                    // 目录创建成功时记录日志
                    LOGGER.info("存储目录创建成功: " + storageDir.getAbsolutePath());
                } else {
                    // 目录创建失败时记录日志
                    LOGGER.info("存储目录创建失败: " + storageDir.getAbsolutePath());
                }
            } catch (SecurityException se) {
                // 处理安全异常，并记录日志
                LOGGER.info("创建目录时遇到安全异常: " + storageDir.getAbsolutePath()+":"+ se.getMessage());
            }
        } else {
            // 目录已存在时记录日志（根据实际需求，这里可以省略或添加特定逻辑）
            LOGGER.info("存储目录已存在: " + storageDir.getAbsolutePath());
        }
    }

    public static void safeDeleteFile(File dest) {
        if (dest == null) {
            LOGGER.log(Level.WARNING, "File to delete is null.");
            return;
        }

        if (!dest.exists()) {
            LOGGER.log(Level.INFO, "File or directory does not exist: {0}", dest.getAbsolutePath());
            return;
        }

        try {
            if (dest.delete()) {
                LOGGER.log(Level.INFO, "File or directory deleted successfully: {0}", dest.getAbsolutePath());
            } else {
                LOGGER.log(Level.WARNING, "Failed to delete the file or directory: {0}", dest.getAbsolutePath());
            }
        } catch (SecurityException se) {
            LOGGER.log(Level.SEVERE, "Security exception while trying to delete the file or directory: {0}", new Object[]{dest.getAbsolutePath(), se});
        }
    }

    public static void main(String[] args) {
        // Example usage
        File fileToDelete = new File("path/to/your/file");
        safeDeleteFile(fileToDelete);
    }
}
