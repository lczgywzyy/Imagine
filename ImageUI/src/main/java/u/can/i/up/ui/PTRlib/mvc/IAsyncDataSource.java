package u.can.i.up.ui.PTRlib.mvc;

public interface IAsyncDataSource<DATA> {

	public RequestHandle refresh(ResponseSender<DATA> sender) throws Exception;

	public RequestHandle loadMore(ResponseSender<DATA> sender) throws Exception;

	public boolean hasMore();

}
