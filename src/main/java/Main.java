import com.bp3.ibmbpm.rest.BasicAuthentication;
import com.bp3.ibmbpm.rest.Context;
import com.bp3.ibmbpm.rest.call.CallException;
import com.bp3.ibmbpm.rest.call.process.GetProcessStateCall;
import com.bp3.ibmbpm.rest.call.process.ProcessesSearchCall;
import com.bp3.ibmbpm.rest.model.process.ProcessInstanceSummary;
import com.bp3.ibmbpm.rest.model.process.ProcessesSearchResult;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, CallException {
        long startTime = System.currentTimeMillis();
        String url = "http://bp3-pcadv-857.bp-3.lan:9080/";
        Context context = new Context().setServerUri(new URI(url));
        context.setAuthentication(new BasicAuthentication("bbeasley",                                                                                               "Drive1018"));
        ProcessesSearchCall call = new ProcessesSearchCall(context)
                .setModifiedAfter("2011-08-04T03:12:53Z");

        ProcessesSearchResult processesSearchResult = call.call();
        System.out.println("process size is " + processesSearchResult.getProcesses().size());
        GetProcessStateCall processStateCall = new GetProcessStateCall(context);
        processStateCall.setParts(GetProcessStateCall.Part.DATA_MODEL);
        for (ProcessInstanceSummary process : processesSearchResult.getProcesses()) {
            processStateCall.setProcessInstanceId(process.getPiid());
            processStateCall.call();
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
