package com.hp.dao;


import com.hp.bean.Visit;
import com.hp.util.DBHelper;
import com.hp.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitDao {
    //新增
    public int insertVisit(Visit visit){

        //第1：创建链接对象
        Connection connection = DBHelper.getConnection();
        //因为添加的是变量用》？代替
        String sql="insert into t_visit values (null,?,?,?,?,?)";
        PreparedStatement ps=null;
        int i=0;
        try {
            ps=connection.prepareStatement(sql);
            ps.setInt(1,visit.getUser_id());
            ps.setInt(2,visit.getCust_id());
            ps.setString(3,visit.getVisit_desc());
            ps.setString(4,visit.getVisit_time());
            ps.setString(5,visit.getCreate_time());

            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return i;
    }

    //全查
    public List<Map> selectAllByParam(Map map){

        System.out.println("map dao= " + map);
        for (Object o : map.keySet()) {
            System.out.println("o = " + o);
        }
        List lists=new ArrayList();
        String page =(String) map.get("page");//接收前端参数放入到map中
        String limit =(String) map.get("limit");

        String user_id = (String) map.get("user_id");
        String cust_id = (String) map.get("cust_id");
        String cust_name = (String) map.get("cust_name");
        String visit_desc = (String) map.get("visit_desc");
        String visit_time = (String) map.get("visit_time");
        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、创建sql语句
        String sql="select v.* ,c.cust_name from  t_visit v  join t_customer  c  on v.cust_id  = c.id  where 1=1  ";
        if(null!=user_id&&user_id.length()>0){
            sql=sql+" and user_id like '%"+user_id+"%'    ";
        }
        if(null!=cust_id&&cust_id.length()>0){
            sql=sql+" and cust_id = "+cust_id+"   ";
        }
        if(null!=cust_name&&cust_name.length()>0){
            sql=sql+" and c.cust_name like '%"+cust_name+"%'  ";
        }
        if(null!=visit_desc&&visit_desc.length()>0){
            sql=sql+" and visit_desc like '%"+visit_desc+"%'  ";
        }
        if(null!=visit_time&&visit_time.length()>0){
            sql=sql+" and visit_time like '%"+visit_time+"%'  ";
        }
        sql = sql + " limit ? , ?";
        System.out.println(" dao de limit sql = " + sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page), Integer.parseInt(limit));
        try {
        //3、使用链接对象获取预编译对象
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());//这是索引
            ps.setInt(2,Integer.parseInt(limit));
            //4.执行sql
            rs = ps.executeQuery();
        //5、遍历结果集,一一的取对象
        while (rs.next()){
            Map dataMap=new HashMap();
            dataMap.put("id",rs.getInt("id"));
            dataMap.put("user_id",rs.getInt("user_id"));
            dataMap.put("cust_id",rs.getInt("cust_id"));
            dataMap.put("cust_name",rs.getString("cust_name"));
            dataMap.put("visit_desc",rs.getString("visit_desc"));
            dataMap.put("visit_time",rs.getString("visit_time"));
            dataMap.put("create_time",rs.getString("create_time"));
            lists.add(dataMap);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }finally {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       return  lists;
    }

    //查询总条数
    public  int selectCount(Map map1){
        //1、步骤1、创建链接对象
        Connection connection = DBHelper.getConnection();
        //2、创建sql语句
        String sql="select count(*) total from  t_visit v  join t_customer  c  on v.cust_id  = c.id where 1=1 ";
        System.out.println("sql count 的 = " + sql);
        PreparedStatement ps=null;
        ResultSet rs=null;
        int total=0;
        try {
            //3、使用链接对象获取预编译对象
            ps= connection.prepareStatement(sql);
            //4、执行预编译对象，得出结果集
            rs= ps.executeQuery();
            //5、遍历结果集,一一的取对象
            if (rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }
    public static void main(String[] args) {
        VisitDao visitDao = new VisitDao();
         Map paramMap=new HashMap();
        paramMap.put("page","1");
       paramMap.put("limit","5");
        List<Map> maps=visitDao.selectAllByParam(paramMap);
        System.out.println("maps = " + maps);
        System.out.println("maps.size() = " + maps.size());

        //测试总条数
        int i = visitDao.selectCount(paramMap);
        System.out.println("i = " + i);


        //添加
       /* VisitDao visitDao = new VisitDao();
        Visit visit = new Visit();
        visit.setUser_id(33);
        visit.setCust_id(77);
        visit.setVisit_desc("测试拜访222");
        visit.setVisit_time("2021-08-05");
        visit.setCreate_time("2020-09-12");

      int i=  visitDao.insertVisit(visit);
        System.out.println("i = " + i);*/



    }
}
