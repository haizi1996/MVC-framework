package framework.servlet;

public abstract class AbstractResourceHandler {
	
	protected AbstractResourceHandler successor;
	
	public abstract String handleRequest(String uri);

	public AbstractResourceHandler getSuccessor() {
		return successor;
	}

	public void setSuccessor(AbstractResourceHandler successor) {
		this.successor = successor;
	}

}
