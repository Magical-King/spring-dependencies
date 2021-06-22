package org.springframework.safe.security.validate.code;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.utils.message.Message;
import org.springframework.safe.utils.message.Status;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 图片生成
 * @author Sir.D
 */
@Controller
@Slf4j
public class ValidateCodeController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request  the request
     * @param response the response
     * @param type     the type
     *
     * @throws Exception the exception
     */
    @RequestMapping(value = "/auth/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable("type") String type) throws Exception {
        try{
            validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
        }catch (Exception e){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(new Message(Status.TOKEN_ERROR,e.getMessage())));
        }
    }

}
