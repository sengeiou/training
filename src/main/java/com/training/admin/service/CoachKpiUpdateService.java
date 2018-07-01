package com.training.admin.service;

import com.training.dao.ContractDao;
import com.training.dao.MemberDao;
import com.training.dao.StaffDao;
import com.training.service.MemberCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachKpiUpdateService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private MemberCardService memberCardService;

    public void execute() {


    }

}
