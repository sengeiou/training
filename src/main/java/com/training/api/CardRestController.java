package com.training.api;

import com.training.domain.Card;
import com.training.service.*;
import com.training.entity.*;
import com.training.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.training.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;

import java.util.List;

/**
 * card API控制器
 * Created by huai23 on 2018-06-06 18:46:26.
 */ 
@RestController
@RequestMapping("/api/card")
public class CardRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CardService cardService;

    /**
     * 新增实体
     * @param card
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    @RequestMapping (value = "add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody CardEntity card,HttpServletRequest request, HttpServletResponse response){
        logger.info(" cardRestController  add  card = {}",card);
        return cardService.add(card);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    @RequestMapping (value = "find", method = RequestMethod.GET)
    public ResponseEntity<String> find(@ModelAttribute CardQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<CardEntity> page = cardService.find(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 分页查询
     * @param query
     * @param pageRequest
     * Created by huai23 on 2018-06-06 18:46:26.
     */
    @RequestMapping (value = "list", method = RequestMethod.GET)
    public ResponseEntity<String> list(@ModelAttribute CardQuery query ,@ModelAttribute PageRequest pageRequest,HttpServletRequest request, HttpServletResponse response){
        Page<Card> page = cardService.list(query,pageRequest);
        return ResponseUtil.success(page);
    }

    /**
     * 查询总数
     * @param query
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    @RequestMapping (value = "count", method = RequestMethod.GET)
    public ResponseEntity<String> count(@ModelAttribute CardQuery query,HttpServletRequest request, HttpServletResponse response){
        Long count = cardService.count(query);
        JSONObject jo = new JSONObject();
        jo.put("count", count);
        return ResponseUtil.success(jo);
    }

    /**
     * 根据ID查询实体
     * @param id
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    @RequestMapping (value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getById(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        CardEntity cardDB = cardService.getById(id);
        if(cardDB==null){
            return ResponseUtil.exception("查无数据");
        }
        return ResponseUtil.success(cardDB);
    }

    /**
     * 根据实体更新
     * @param card
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    @RequestMapping (value = "update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody CardEntity card,HttpServletRequest request, HttpServletResponse response){
        logger.info("  update  card = {}",card);
        return cardService.update(card);
    }

    /**
     * 根据ID删除
     * @param id
     * Created by huai23 on 2018-06-06 18:46:26.
     */ 
    @RequestMapping (value = "delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("  delete  id = {}",id);
        return cardService.delete(id);
    }


}

