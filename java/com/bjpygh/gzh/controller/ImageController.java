package com.bjpygh.gzh.controller;

import com.bjpygh.gzh.bean.DsImage;
import com.bjpygh.gzh.entity.Status;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ImageController {
	
	private static final Log logger = LogFactory.getLog(ImageController.class);

	@ResponseBody
    @RequestMapping(value = "/comment_picture",method = RequestMethod.POST)
    public Status saveImage(HttpServletRequest servletRequest,
                          @ModelAttribute DsImage images ) {

	    System.out.println(images);
        List<MultipartFile> files = images.getImages();
        System.out.println(files);
        List<String> fileNames = new ArrayList<String>();

        if (null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {

                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                File image = new File(servletRequest.getServletContext().getRealPath("/CommentImg"));
                if(!image.exists()){
                    image.mkdirs();
                }
                File imageFile = new File(servletRequest.getServletContext()
                        .getRealPath("/CommentImg"), fileName);
                try {
                    multipartFile.transferTo(imageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // save product here
//        model.addAttribute("image", dsImage);
        /**
         * 这儿的图片地址上线需要改IP为120.24.184.86
         */
//      out.print("http://120.24.184.86:8080/glxt/dsimage/"+fileNames);

        return Status.success().add("picture",fileNames);
    }
	
}
