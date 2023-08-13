package com.jsh.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import com.jsh.erp.datasource.entities.Msg;
import com.jsh.erp.datasource.entities.MsgEx;
import com.jsh.erp.datasource.vo.LogVo4List;
import com.jsh.erp.service.msg.MsgComponent;
import com.jsh.erp.service.msg.MsgService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.Constants;
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
@RequestMapping(value = "/msg")
@Api(tags = {"消息管理"})
public class MsgController {
    private Logger logger = LoggerFactory.getLogger(MsgController.class);

    @Resource
    private MsgService msgService;
    @Resource
    private MsgComponent msgComponent;

    /**
     * 根据状态查询消息
     * @param status
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/getMsgByStatus")
    @ApiOperation(value = "根据状态查询消息")
    public BaseResponseInfo getMsgByStatus(@RequestParam("status") String status,
                                           HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<MsgEx> list = msgService.getMsgByStatus(status);
            res.code = 200;
            res.data = list;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

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
            List resList = msgComponent.select(param);
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

    /**
     * 批量更新状态
     * @param jsonObject
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/batchUpdateStatus")
    @ApiOperation(value = "批量更新状态")
    public BaseResponseInfo batchUpdateStatus(@RequestBody JSONObject jsonObject,
                                              HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String ids = jsonObject.getString("ids");
            String status = jsonObject.getString("status");
            msgService.batchUpdateStatus(ids, status);
            res.code = 200;
            res.data = "更新成功";
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据状态查询数量
     * @param status
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/getMsgCountByStatus")
    @ApiOperation(value = "根据状态查询数量")
    public BaseResponseInfo getMsgCountByStatus(@RequestParam("status") String status,
                                                HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Long> map = new HashMap<String, Long>();
            Long count = msgService.getMsgCountByStatus(status);
            map.put("count", count);
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
     * 根据类型查询数量
     * @param type
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/getMsgCountByType")
    @ApiOperation(value = "根据类型查询数量")
    public BaseResponseInfo getMsgCountByType(@RequestParam("type") String type,
                                                HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Integer> map = new HashMap<>();
            Integer count = msgService.getMsgCountByType(type);
            map.put("count", count);
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
     * 全部设置未已读
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/readAllMsg")
    @ApiOperation(value = "全部设置未已读")
    public BaseResponseInfo readAllMsg(HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            msgService.readAllMsg();
            res.code = 200;
            res.data = "操作成功!";
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
