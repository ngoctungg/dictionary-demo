package com.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.entity.RulesEntity;
import com.pm.repository.RulesRepository;

@Service
public class RulesService {
    @Autowired
    private RulesRepository rulesService;
	public List<RulesEntity> getAllRules(){
		return rulesService.findAll();
	}
	
}
