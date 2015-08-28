package u.can.i.up.ui.PTRlib.mvc;

public interface ResponseSender<DATA> {

	public void sendError(Exception exception);

	public void sendData(DATA data);

}