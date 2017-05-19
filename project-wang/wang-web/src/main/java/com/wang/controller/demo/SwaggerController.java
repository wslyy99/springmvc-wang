package com.wang.controller.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "restful", description = "关于Restful接口文档注释")
@RestController
@RequestMapping("swagger")
public class SwaggerController {

    @ApiOperation(value = "GET获取数据",produces="application/json")
    @RequestMapping(value = "resource/{id}", method = RequestMethod.GET)
    public ResponseEntity<ModelMap> swaggerGetResource(@ApiParam(name = "id", value = "编号", required = true) @PathVariable String id) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("httpCode", HttpStatus.OK.value());
        modelMap.put("msg", HttpStatus.OK.getReasonPhrase());
        modelMap.put("timestamp", System.currentTimeMillis());
        modelMap.put("apiversion", 2);
        return ResponseEntity.ok(modelMap);
    }
    
    @ApiOperation(value = "POST新增数据")
    @RequestMapping(value = "resource/{id}", method = RequestMethod.POST)
    public ResponseEntity<ModelMap> postResource(@ApiParam(name = "id", value = "编号", required = true) @PathVariable String id){
        ModelMap modelMap = new ModelMap();
        modelMap.put("status", HttpStatus.BAD_REQUEST.value());
        modelMap.put("timestamps",System.currentTimeMillis());
        modelMap.put("msg", HttpStatus.BAD_REQUEST.getReasonPhrase());
        modelMap.put("apiversion", 2);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(modelMap);
    }
    
    
    @ApiOperation(value = "DELETE删除数据")
    @RequestMapping(value = "resource/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ModelMap> deleteResource(@ApiParam(name = "id", value = "编号", required = true) @PathVariable String id){
        ModelMap modelMap = new ModelMap();
        modelMap.put("status", HttpStatus.OK.value());
        modelMap.put("timestamps",System.currentTimeMillis());
        modelMap.put("msg", HttpStatus.OK.getReasonPhrase());
        modelMap.put("apiversion", 2);
        return ResponseEntity.status(HttpStatus.OK).body(modelMap);
    }
    
    @ApiOperation(value = "PUT更新数据")
    @RequestMapping(value = "resource/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ModelMap> updateResource(@ApiParam(name = "id", value = "编号", required = true) @PathVariable String id){
        ModelMap modelMap = new ModelMap();
        modelMap.put("status", HttpStatus.OK.value());
        modelMap.put("timestamps",System.currentTimeMillis());
        modelMap.put("msg", HttpStatus.OK.getReasonPhrase());
        modelMap.put("apiversion", 2);
        return ResponseEntity.status(HttpStatus.OK).body(modelMap);
    }
}