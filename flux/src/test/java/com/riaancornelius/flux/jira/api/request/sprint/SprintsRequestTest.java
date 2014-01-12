package com.riaancornelius.flux.jira.api.request.sprint;

import com.octo.android.robospice.SpringAndroidSpiceService;
import com.riaancornelius.flux.api.JiraSpiceTestService;
import com.riaancornelius.flux.jira.domain.sprint.Sprints;
import org.fest.assertions.Assertions;
import org.fest.assertions.Fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.tester.org.apache.http.FakeHttpLayer;
import org.springframework.web.client.RestClientException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: riaan.cornelius
 */
@RunWith(RobolectricTestRunner.class)
public class SprintsRequestTest {

    private SprintsRequest sprintsRequest;

    @Before
    public void setup() throws Exception {
        System.out.println("SETUP");

        sprintsRequest = new SprintsRequest(1L);
        JiraSpiceTestService service = new JiraSpiceTestService();
        service.setValuesOn(sprintsRequest);

        // By default, return error if no other response was queued
        //Robolectric.getFakeHttpLayer().interceptHttpRequests(true);
        Robolectric.setDefaultHttpResponse(400, "Fail");

        System.out.println("SETUP COMPLETE");
    }

    @After
    public void cleanup() throws Exception {
        System.out.println("CLEANUP");
        Robolectric.clearPendingHttpResponses();
        FakeHttpLayer fakeHttpLayer = Robolectric.getFakeHttpLayer();
        assertThat(fakeHttpLayer.hasPendingResponses()).isFalse();
        System.out.println("CLEANUP COMPLETE");
    }

    @Test
    public void testFailing() throws Exception {
        System.out.println("TEST FAILING");
        Robolectric.setDefaultHttpResponse(400, "Fail");
        assertThat(Robolectric.getFakeHttpLayer().isInterceptingHttpRequests()).isTrue();
        try {
            sprintsRequest.loadDataFromNetwork();
            assertThat(Robolectric.httpRequestWasMade()).isTrue();
            Fail.fail("Request should have thrown a RestClientException");
        } catch (RestClientException rce) {
            assertThat(true).isTrue();
        }
        System.out.println("TEST FAILING COMPLETE");
    }

    // @Test
    public void testSuccessMultipleSprints() throws Exception {
        System.out.println("TEST SUCCESS");

        String response = "{\"sprints\":[{\"id\":1,\"name\":\"Sprint 0 - Planning\",\"state\":\"CLOSED\",\"linkedPagesCount\":0},{\"id\":2,\"name\":\"Sprint 1\",\"state\":\"CLOSED\",\"linkedPagesCount\":0},{\"id\":3,\"name\":\"Sprint 2\",\"state\":\"CLOSED\",\"linkedPagesCount\":0}],\"rapidViewId\":1}";

        Robolectric.addPendingHttpResponse(200, response);
        //Robolectric.setDefaultHttpResponse(200, response);

//        FakeHttpLayer fakeHttpLayer = Robolectric.getFakeHttpLayer();
//        assertThat(fakeHttpLayer.hasPendingResponses()).isTrue();

        try {
            Sprints sprints = sprintsRequest.loadDataFromNetwork();
            assertThat(Robolectric.httpRequestWasMade()).isTrue();
            // Assertions
            assertThat(sprints.getSprints().size()).isEqualTo(3);
        } catch (RestClientException rce) {
            Fail.fail("No exception expected!", rce);
        }
        System.out.println("TEST SUCCESS COMPLETE");
    }

}
