package u.can.i.up.ui.event;

import in.srain.cube.request.FailData;

public class ErrorMessageDataEvent {

    public String dataTag;
    public String message;

    public ErrorMessageDataEvent(FailData failData, String dataTag, String msg) {
        this.dataTag = dataTag;
        this.message = msg;
    }
}
