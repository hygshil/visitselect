package com.hp.controller;

import com.alibaba.fastjson.JSONObject;
import com.hp.bean.User;
import com.hp.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UserUpdateServlet", urlPatterns = "/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 修正 编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8"); // 注意 , 别写错

        // 2. 接收前端的参数
        String is_del = req.getParameter("is_del");
        String modify_time = req.getParameter("modify_time");
        String password = req.getParameter("password");
        String real_name = req.getParameter("real_name");
        String type = req.getParameter("type");
        String username = req.getParameter("username");
        // 缺少一个最重要的  参数, 即:  主键id 因为 修改是 按主键id 来修改.
        String id = req.getParameter("id");
        // 把参数 赋值成 对象
        // 在修改之前, 先查询出来  前端没有的参数
        // 嗲用service层

        UserService userService = new UserService();
        Map map = userService.selectUserById(Integer.parseInt(id));
        User data = (User) map.get("data");

        User user = new User();
        user.setCreate_time(data.getCreate_time());
        user.setImg(data.getImg());
        user.setIs_del(Integer.parseInt(is_del));
        user.setModify_time(modify_time);
        user.setPassword(password);
        user.setReal_name(real_name);
        user.setType(Integer.parseInt(type));
        user.setUsername(username);
        user.setId(Integer.parseInt(id));
        Map map1 = userService.updateUser(user);
        String s = JSONObject.toJSONString(map1);
        PrintWriter writer = resp.getWriter();
        writer.println(s);//
        writer.close();


    }
}
