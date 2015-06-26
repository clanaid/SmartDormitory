package com.bailv.util;

/**
 * 
 * 自定义的Event事件类型
 * 
 * @author: 泓钦
 * @time: 2015年6月23日 下午5:41:59
 */
public class EventsPost {

	public enum EventType {
		DDPUSH, SLEEPALARM, GETUPALARM, OUTALARM, HOMESTATE
	};

	private String cmd;
	private EventType eventType;
	private State homesate;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public EventsPost(String cmd, EventType eventType) {
		// TODO 自动生成的构造函数存根
		this.cmd = cmd;
		this.eventType = eventType;
	}

	public State getHomesate() {
		return homesate;
	}

	public void setHomesate(State homesate) {
		this.homesate = homesate;
	}

	public EventsPost(char which, Boolean action, EventType eventType) {
		// TODO 自动生成的构造函数存根
		this.homesate = new State(which, action);
		this.eventType = eventType;
	}

	public class State {
		private boolean action;
		private char which;

		public State(char which, boolean action) {
			this.action = action;
			this.which = which;
		}

		public boolean isAction() {
			return action;
		}

		public void setAction(boolean action) {
			this.action = action;
		}

		public char getWhich() {
			return which;
		}

		public void setWhich(char which) {
			this.which = which;
		}
	}
}
