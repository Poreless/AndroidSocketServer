package wjx.server.operator;

import java.util.ArrayList;

public abstract class BaseOperator {// 抽象类，用于被具体的操作所继承
	public abstract ArrayList<String> exe(String cmdBody) throws Exception;
}