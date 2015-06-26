package com.bailv.util;

import java.util.HashMap;
import java.util.Map;

import com.bailv.util.controlSocket.ControlType;

/**
 * 
 * 控制命令获取类
 * @author:	泓钦
 * @time:	2015年6月17日	下午9:30:36
 */
public class ControlCmd {

	public enum CmdName{
		OPEN_ALL_SWITCH,OPEN_FIRST_SWITCH,OPEN_SECOND_SWITCH,OPEN_THIRD_SWITCH,
		CLOSE_ALL_SWITCH,CLOSE_FIRST_SWITCH,CLOSE_SECOND_SWITCH,CLOSE_THIRD_SWITCH,
		OPEN_WINDOW,CLOSE_WINDOW,OPEN_DOOR,CLOSE_DOOR,OPEN_CURTAIN,CLOSE_CURTAIN
	};
	
	private static final Map<CmdName, String> cmd = new HashMap<CmdName,String>(){
		{
			put(CmdName.OPEN_ALL_SWITCH, "SAO");
			put(CmdName.CLOSE_ALL_SWITCH, "SAC");
			put(CmdName.OPEN_FIRST_SWITCH, "S1O");
			put(CmdName.CLOSE_FIRST_SWITCH, "S1C");
			put(CmdName.OPEN_SECOND_SWITCH, "S2O");
			put(CmdName.CLOSE_SECOND_SWITCH, "S2C");
			put(CmdName.OPEN_THIRD_SWITCH, "S3O");
			put(CmdName.CLOSE_THIRD_SWITCH, "S3C");
			put(CmdName.OPEN_CURTAIN, "CO0");
			put(CmdName.CLOSE_CURTAIN, "CC0");
			put(CmdName.OPEN_DOOR, "EO0");
			put(CmdName.CLOSE_DOOR, "EC0");
			put(CmdName.OPEN_WINDOW, "WO0");
			put(CmdName.CLOSE_WINDOW, "WC0");
		}
	};
	
	public static String getCmd(CmdName cmdName){
		return cmd.get(cmdName);
	}
	
}
