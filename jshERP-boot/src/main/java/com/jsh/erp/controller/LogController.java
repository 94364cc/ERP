package com.jsh.erp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import com.gitee.starblues.integration.operator.module.PluginInfo;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.MsgEx;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.vo.LogVo4List;
import com.jsh.erp.service.log.LogComponent;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.msg.MsgService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ji sheng hua jshERP
 */
@RestController
@RequestMapping(value = "/log")
@Api(tags = {"日志管理"})
public class LogController {
    private Logger logger = LoggerFactory.getLogger(LogController.class);

    @Resource
    private LogService logService;
    @Resource
    private LogComponent logComponent;

    /**
     * 获取日志信息
     * @return 返回插件信息
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取日志信息")
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
            List resList = logComponent.select(param);
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
}
