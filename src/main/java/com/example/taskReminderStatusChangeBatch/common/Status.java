package com.example.taskReminderStatusChangeBatch.common;

// TODO 定数クラスで出た例外はどこで拾う？
public enum Status {
	
	NON_EXECTED("0", "未実施"),
	EXECUTED("1", "実行済");
	
	private String code;
    private String name;
    
    private Status(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    
    public static Status getValue(String code) {
    	Status[] array = values();
    	for(Status num : array) {
    		if(code.equals(num.getCode())) {
    			return num;
    		}
    	}
    	throw new IllegalArgumentException("undefined : " + code);
    }
           
}
	