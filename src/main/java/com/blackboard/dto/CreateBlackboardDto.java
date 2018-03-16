package com.blackboard.dto;

import java.util.List;
import java.util.Map;

import com.blackboard.entity.Blackboard;

/**
 *  创建黑板报包装类
 * @author xmz
 *
 */
public class CreateBlackboardDto {

	private Blackboard blackboard;
	private CheckAttack checkAttack;
	private List<Map<String,Object>> visibleRange;
	
	public List<Map<String, Object>> getVisibleRange() {
		return visibleRange;
	}

	public void setVisibleRange(List<Map<String, Object>> visibleRange) {
		this.visibleRange = visibleRange;
	}

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
