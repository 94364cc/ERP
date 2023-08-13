package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Role;
import com.jsh.erp.datasource.entities.RoleEx;
import com.jsh.erp.datasource.entities.UserEx;
import com.jsh.erp.service.role.RoleComponent;
import com.jsh.erp.service.role.RoleService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.ErpInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji sheng hua jshERP
 */
@RestController
@RequestMapping(value = "/role")
@Api(tags = {"角色管理"})
public class RoleController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private RoleService roleService;
    @Resource
    private RoleComponent roleComponent;
    @Resource
    private UserBusinessService userBusinessService;

    /**
     * 角色对应应用显示
     * @param request
     * @return
     */
    @GetMapping(value = "/findUserRole")
    @ApiOperation(value = "查询用户的角色")
    public JSONArray findUserRole(@RequestParam("UBType") String type, @RequestParam("UBKeyId") String keyId,
                                  HttpServletRequest request)throws Exception {
        JSONArray arr = new JSONArray();
        try {
            //获取权限信息
            String ubValue = userBusinessService.getUBValueByTypeAndKeyId(type, keyId);
            List<Role> dataList = roleService.findUserRole();
            if (null != dataList) {
                for (Role role : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", role.getId());
                    item.put("text", role.getName());
                    Boolean flag = ubValue.contains("[" + role.getId().toString() + "]");
                    if (flag) {
                        item.put("checked", true);
                    }
                    arr.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * create by: zcc
     * description:
     *  修改角色
     * @Param: beanJson
     * @return java.lang.Object
     */
    @PutMapping("/updateRole")
    @ApiOperation(value = "修改角色")
    @ResponseBody
    public Object updateUser(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        roleService.updateRole(obj, request);
        return result;
    }


    /**
     * 获取用户信息
     * @return 返回插件信息
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取用户信息")
    public BaseResponseInfo getPluginInfo(@RequestParam(value = "search",required = false) String search,
                                          @RequestParam("currentPage") Integer currentPage,
                                          @RequestParam("pageSize") Integer pageSize,
                                          HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String,String> param = new HashMap<>();
            param.put(Constants.SEARCH,search);
            param.put("currentPage",""+currentPage);
            param.put("pageSize",""+pageSize);
            List resList = roleComponent.select(param);
            map.put("rows", resList);
            map.put("total", resList.size());
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    @GetMapping(value = "/allList")
    @ApiOperation(value = "查询全部角色列表")
    public List<Role> allList(HttpServletRequest request)throws Exception {
        return roleService.allList();
    }

    @GetMapping(value = "/checkIsNameExist")
    @ApiOperation(value = "检查名称是否存在")
    public String checkIsNameExist(@RequestParam Long id, @RequestParam(value ="name", required = false) String name,
                                   HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int exist = roleService.checkIsNameExist(id, name);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }


    /**
     * create by: cjl
     * description:
     *  修改角色
     * create time: 2019/3/8 16:06
     * @Param: beanJson
     * @return java.lang.Object
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改角色")
    @ResponseBody
    public Object updateRole(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            roleService.updateRole(obj, request);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
    /**
     * 批量设置状态-启用或者禁用
     * @param jsonObject
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    @ApiOperation(value = "批量设置状态")
    public String batchSetStatus(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request)throws Exception {
        Boolean status = jsonObject.getBoolean("status");
        String ids = jsonObject.getString("ids");
        Map<String, Object> objectMap = new HashMap<>();
        int res = roleService.batchSetStatus(status, ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }
}
