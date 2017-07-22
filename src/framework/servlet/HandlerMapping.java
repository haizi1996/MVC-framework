package framework.servlet;

import java.util.HashMap;
import java.util.Map;

import framework.mvc.ControllerUtil;
import framework.mvc.Handler;
import framework.mvc.Request;

public class HandlerMapping {

	@SuppressWarnings("unused")
	public Handler getHandler(String requestPath, String requestMethod) {
		// TODO Auto-generated method stub
		Map<Request, Handler> ACTION_MAP = ControllerUtil.getACTION_MAP();
		int value = 1 << 20;
		Handler handler = null;
		Request req = null;
		for (Request request : ACTION_MAP.keySet()) {
			if (request.isRequest(requestMethod, requestPath)) {
				if (request.getPathValue() < value) {
					handler = ACTION_MAP.get(request);
					value = request.getPathValue();
				}
			}
		}
		if (req != null && handler != null){
			String[] lines = requestPath.split("/");
			Map<Integer, String> map = req.getPath();
			Map<String, String> pathVariable = new HashMap<String, String>();
			for (Integer index : map.keySet()) {
				if (index < lines.length) {
					pathVariable.put(map.get(index), lines[index]);
				}
			}
			handler.setPathVariable(pathVariable);
		}
		return handler;
	}

}
