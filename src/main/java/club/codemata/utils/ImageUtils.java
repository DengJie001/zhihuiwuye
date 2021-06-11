package club.codemata.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author DengJie
 * @version 1.0
 * @ClassName ImageUtils
 * @Description 图片操作工具类
 * @createTime 2021/03/06 10:43:00
 */
public class ImageUtils {
    /**
     * @author DengJie
     * @description 上传图片
     * @Date 2021/3/6 10:56
     * @Param [javax.servlet.http.HttpServletRequest, org.springframework.web.multipart.MultipartFile]
     * @return java.lang.String
     */
    public static String upload(HttpServletRequest request, MultipartFile pictureFile, String tag) throws IOException {
        String imgPath = null; // 装配后的文件路径
        //String docBase = "C:\\Users\\Administrator\\Desktop\\images\\"; // 图片在服务器端的真实路径的基础路径
        //String path = "http://localhost:8080/images/";  // 图片的虚拟路径的基础路径
        String docBase = "/home/lxzhwy/"; // 图片在服务器端的真实路径的基础路径
        String path = "http://codemata.club:8080/images/";  // 图片的虚拟路径的基础路径
        // 上传图片
        if (pictureFile != null && !pictureFile.isEmpty()) {
            // 使用UUID给图片命名，并去掉四个-
            String name = UUIDUtils.getUUID().replaceAll("-", "");

            // 获取文件的扩展名
            String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());

            // 设置图片上传路径
            if (tag.equals("管理员头像")) {
                docBase += "manager/avatar/";
                path += "manager/avatar/";
            } else if (tag.equals("设备照片")) {
                docBase += "equipment/";
                path += "equipment/";
            } else if (tag.equals("场地照片")) {
                docBase += "placeInfo/";
                path += "placeInfo/";
            } else if ("维修人员头像".equals(tag)) {
                docBase += "worker/";
                path += "worker/";
            } else if ("投诉建议".equals(tag)) {
                docBase += "complaint/";
                path += "complaint/";
            }

            // 绝对路径保存图片
            pictureFile.transferTo(new File(docBase + name + "." + ext));

            // 装配图片地址
            imgPath = path + name + "." + ext;
        }
        return imgPath;
    }

    public static String wxUploadImage(HttpServletRequest request, MultipartFile image, String tag) throws IOException {
        String imgPath = null; // 装配后的文件路径
        //String docBase = "C:\\Users\\Administrator\\Desktop\\images\\"; // 图片在服务器端的真实路径的基础路径
        //String path = "http://localhost:8080/images/";  // 图片的存在于服务器中的路径
        String docBase = "/home/lxzhwy/"; // 图片在服务器端的真实路径的基础路径
        String path = "http://codemata.club:8080/images/";  // 图片的虚拟路径的基础路径
        if (image != null && !image.isEmpty()) {
            // UUID给图片命名
            String name = UUIDUtils.getUUID().replaceAll("-", "");

            // 获取图片后缀
            String ext = FilenameUtils.getExtension(image.getOriginalFilename());

            if ("投诉建议".equals(tag)) {
                docBase += "complaint/";
                path += "complaint/";
            } else if ("失物招领".equals(tag)) {
                docBase += "lostItem/";
                path += "lostItem/";
            }
            image.transferTo((new File(docBase + name + "." + ext)));
            imgPath = path + name + "." + ext;
        }
        return imgPath;
    }
}
