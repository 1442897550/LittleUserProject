package xjtutjc.web;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCode")
public class CheckCode extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建一个对象，在内存中图片（验证码图片对象）

        int width = 100;
        int height = 50;

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //美化图片
        //1. 填充背景色
        Graphics graphics = image.getGraphics();//获取画笔对象
        //2.设置画笔颜色
        graphics.setColor(Color.pink);
        graphics.fillRect(0,0,width,height);
        //画边框
        graphics.setColor(Color.BLUE);
        graphics.drawRect(0,0,width-1,height-1);
        //
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        //创建随机角标
        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        //3.写验证码
        for (int j = 1; j <=4 ; j++) {
            int i = random.nextInt(str.length());//随机角标
            char c = str.charAt(i);//随机字符
            sb.append(c);
            graphics.drawString(String.valueOf(c),width/5*j,25);
        }
        //获取验证码
        String s = sb.toString();
        HttpSession session = request.getSession();
        session.setAttribute("checkcode",s);

        //画干扰线
        graphics.setColor(Color.GREEN);
        for (int i = 0; i <6 ; i++) {
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);

            graphics.drawLine(x1,y1,x2,y2);
        }

        //将图片输出页面展示
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
