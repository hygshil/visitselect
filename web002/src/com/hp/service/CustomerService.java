package com.hp.service;


import com.hp.bean.Customer;
import com.hp.dao.CustomerDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    //全查
    public Map selectAllByParam(Map map){

        CustomerDAO dao= new CustomerDAO();
        List<Map> maps = dao.selectAllByParam(map);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("data",maps);
        codeMap.put("msg","ok");
        Map countMap = selectAllByParamCount(map);
        int count = (int) countMap.get("data");
        codeMap.put("count",count);
        return  codeMap;
    }
    // 全查总条数 多的
    public Map selectAllByParamCount(Map map){
        Map codeMap = new HashMap();
        CustomerDAO dao= new CustomerDAO();
        int i= dao.selectAllByParamCount(map);
        codeMap.put("code",0);
        codeMap.put("data",i);
        codeMap.put("msg","ok");
        return  codeMap;
    }
    //添加
    public Map insertCustomer(Customer customer){
        CustomerDAO dao =new CustomerDAO();
        int i = dao.insertCustomer(customer);
        Map codeMap=new HashMap();
        System.out.println("i = " + i);
        if (i==1){
            codeMap.put("code",0);
            codeMap.put("msg","添加成功");
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","添加失败");
        }
        return codeMap;
    }
    //删除，批量删除
    public int deleteByCustomerId(Integer id){
        CustomerDAO customerDAO = new CustomerDAO();
        int i = customerDAO.deleteByCustomerId(id);
        return i;
    }

    //删除一条数据
    public int deleteOneCustomerId(Integer id){
        CustomerDAO customerDAO = new CustomerDAO();
        int i = customerDAO.deleteOneCustomerId(id);
        return i;
    }
}
