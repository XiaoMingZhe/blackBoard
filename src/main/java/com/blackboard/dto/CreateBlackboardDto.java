package com.blackboard.dto;

import com.blackboard.entity.Blackboard;

/**
 *  创建黑板报包装类
 * @author xmz
 *
 */
public class CreateBlackboardDto {

	private Blackboard blackboard;
	private CheckAttack checkAttack;

	public Blackboard getBlackboard() {
		return blackboard;
	}

	public void setBlackboard(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	public CheckAttack getCheckAttack() {
		return checkAttack;
	}

	public void setCheckAttack(CheckAttack checkAttack) {
		this.checkAttack = checkAttack;
	}

	@Override
	public String toString() {
		return "CreateBlackboardDto [blackboard=" + blackboard + ", checkAttack=" + checkAttack + "]";
	}

	
}
