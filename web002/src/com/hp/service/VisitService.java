package com.hp.service;

import com.hp.bean.Visit;
import com.hp.dao.VisitDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitService {
    //全查
    public  Map selectAllByParam(Map map1){
        VisitDao visitDao = new VisitDao();
        List<Map>  visits=visitDao.selectAllByParam(map1);
        Map codemap = new HashMap();
        codemap.put("code",0);//必须和layui的JSON返回的格式一样
        codemap.put("data",visits);
        codemap.put("msg","ok");
        Map countmap=selectCount(map1);
        int count=(int)countmap.get("data");
        codemap.put("count",count);
        System.out.println("codemap  service的 = " + codemap);
        return codemap;
    }

    //全查总条数
    public  Map selectCount(Map map){
        Map codeMap=new HashMap();
        VisitDao visitDao = new VisitDao();
        int i = visitDao.selectCount(map);
        codeMap.put("code",0);
        codeMap.put("data",i);
        codeMap.put("msg","ok");
        return codeMap;
    }
    //添加
    public Map insertVisit(Visit visit){
        VisitDao dao=new VisitDao();

        int i=dao.insertVisit(visit);

        Map codeMap=new HashMap();
        System.out.println("i = " + i);
        if (i==1){
            codeMap.put("code",0);
            codeMap.put("msg","ok");
            return codeMap;
        }else{
            codeMap.put("code",400);
            codeMap.put("msg","no");
            return codeMap;

           }
        }

    }
