package com.pm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`rule`")
public class RulesEntity {
	 @Id
	    @Column(name = "RuleNumber")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer ruleNumber;

	    @Column(name = "Room")
	    private String room;
	    
	    @Column(name = "Type")
	    private String type;
	    
	    @Column(name = "RuleName")
	    private String ruleName;
	    
	    @Column(name = "DescriptionDev")
	    private String descriptionDev;
	    
	    @Column(name = "PickDev")
	    private String pickDev;
	    
	    @Column(name = "PickTest")
	    private String pickTest;
	    
	    @Column(name = "DescriptionTest")
	    private String descriptionTest;

		public Integer getRuleNumber() {
			return ruleNumber;
		}

		public void setRuleNumber(Integer ruleNumber) {
			this.ruleNumber = ruleNumber;
		}

		public String getRoom() {
			return room;
		}

		public void setRoom(String room) {
			this.room = room;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getRuleName() {
			return ruleName;
		}

		public void setRuleName(String ruleName) {
			this.ruleName = ruleName;
		}

		public String getDescriptionDev() {
			return descriptionDev;
		}

		public void setDescriptionDev(String descriptionDev) {
			this.descriptionDev = descriptionDev;
		}

		public String getPickDev() {
			return pickDev;
		}

		public void setPickDev(String pickDev) {
			this.pickDev = pickDev;
		}

		public String getPickTest() {
			return pickTest;
		}

		public void setPickTest(String pickTest) {
			this.pickTest = pickTest;
		}

		public String getDescriptionTest() {
			return descriptionTest;
		}

		public void setDescriptionTest(String descriptionTest) {
			this.descriptionTest = descriptionTest;
		}
	    
	    

}
