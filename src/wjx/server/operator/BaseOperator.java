package wjx.server.operator;

import java.util.ArrayList;

public abstract class BaseOperator {// �����࣬���ڱ�����Ĳ������̳�
	public abstract ArrayList<String> exe(String cmdBody) throws Exception;
}