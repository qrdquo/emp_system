package com.awei.controller;

import com.awei.entity.Emp;
import com.awei.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("emp")
@Slf4j
public class EmpController {

    @Autowired
    private EmpService empService;
    @Value("${photo.dir}")
    private String realPath;


    /**
     * 更新员工信息
     */
    @PostMapping("update")
    public Map<String,Object> update( Emp emp, MultipartFile photo) throws IOException {
        log.info("员工信息：[{}]",emp.toString());

        Map<String,Object> map = null;
        try {
            if(photo!=null&&photo.getSize()!=0){
                //删除原来的头像
                File file = new File(realPath,emp.getPath());
                if(file.exists()){
                    file.delete();//删除头像
                }
                log.info("新头像信息：[{}]",photo.getOriginalFilename());
                //新头像保存
                String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(photo.getOriginalFilename());
                photo.transferTo(new File(realPath,newFileName));
                //设置新头像地址
                emp.setPath(newFileName);
            }
            map = new HashMap<>();
            //保存员工信息
            empService.update(emp);
            map.put("state",true);
            map.put("msg","员工信息修改成功");
        } catch (Exception e) {
            map.put("state",false);
            map.put("msg","员工信息修改失败");
        }
        return map;
    }
    /**
     * 根据id查询员工信息
     */
    @GetMapping("findOne")
    public Emp findOne(String id){
        return empService.findOne(id);
    }


    /**
     * 删除员工信息
     */
    @DeleteMapping("delete")
    public Map<String,Object> delete(String id){
        log.info("删除员工的id:[{}]",id);
        Map<String, Object> map = new HashMap<>();
        try {

            //1删除员工头像
            Emp emp = empService.findOne(id);
            File file = new File(realPath,emp.getPath());
            if(file.exists()){
                file.delete();//删除头像
            }
            empService.delete(id);
            map.put("state",true);
            map.put("msg","删除成功");
        }catch (Exception e){
            map.put("state",false);
            map.put("msg","删除失败");
        }
        return map;
    }


    /**
     *保存员工信息
     */
    @PostMapping("save")
    public Map<String,Object> save( Emp emp, MultipartFile photo) throws IOException {
        log.info("员工信息：[{}]",emp.toString());
        log.info("头像信息：[{}]",photo.getOriginalFilename());
        Map<String,Object> map = null;
        try {
            map = new HashMap<>();
            //头像保存
            String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(photo.getOriginalFilename());
            photo.transferTo(new File(realPath,newFileName));
            //设置头像地址
            emp.setPath(newFileName);
            //保存员工信息
            empService.save(emp);
            map.put("state",true);
            map.put("msg","员工信息保存成功");
        } catch (Exception e) {
            map.put("state",false);
            map.put("msg","员工信息保存失败");
        }
        return map;
    }


    //获取员工列表的方法
    @PostMapping("/findAll")
    public List<Emp> findAll(){
        return empService.findAll();
    }
}
