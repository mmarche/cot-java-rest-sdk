package com.telekom.m2m.cot.restsdk.inventory.event;

import com.telekom.m2m.cot.restsdk.CloudOfThingsPlatform;
import com.telekom.m2m.cot.restsdk.CloudOfThingsRestClient;
import com.telekom.m2m.cot.restsdk.devicecontrol.DeviceControlApi;
import com.telekom.m2m.cot.restsdk.event.Event;
import com.telekom.m2m.cot.restsdk.event.EventApi;
import com.telekom.m2m.cot.restsdk.util.CotSdkException;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

import static org.mockito.Matchers.any;

/**
 * Created by breucking on 03.02.16.
 */
public class EventApiTest {
    @Test(expectedExceptions = CotSdkException.class)
    public void testGetEventWithFailure() throws Exception {
        CloudOfThingsRestClient rc = Mockito.mock(CloudOfThingsRestClient.class);
        CloudOfThingsPlatform platform = Mockito.mock(CloudOfThingsPlatform.class);
        Mockito.when(platform.getEventApi()).thenReturn(new EventApi(rc));
        Mockito.doThrow(CotSdkException.class).when(rc).getResponse(any(String.class), any(String.class), any(String.class));

        EventApi eventApi = platform.getEventApi();
        eventApi.getEvent("foo");
    }

    @Test
    public void testGetEvent() throws Exception {

        String eventJsonExample = "{\n" +
                "  \"id\" : \"10\",\n" +
                "  \"self\" : \"...\",\n" +
                "  \"time\" : \"2011-09-06T12:03:27.000+02:00\",\n" +
                "  \"creationTime\" : \"2011-09-06T12:03:27.000+02:00\",\n" +
                "  \"type\" : \"com_telekom_DoorSensorEvent\",\n" +
                "  \"text\" : \"Door sensor was triggered.\",\n" +
                "  \"source\" : { \"id\":\"12345\", \"self \": \"...\" }\n" +
                "}";

        CloudOfThingsRestClient rc = Mockito.mock(CloudOfThingsRestClient.class);
        CloudOfThingsPlatform platform = Mockito.mock(CloudOfThingsPlatform.class);
        Mockito.when(platform.getEventApi()).thenReturn(new EventApi(rc));
        Mockito.when(rc.getResponse(any(String.class), any(String.class), any(String.class))).thenReturn(eventJsonExample);

        EventApi eventApi = platform.getEventApi();
        Event event = eventApi.getEvent("foo");

        Assert.assertEquals(event.getId(), "10");
        Assert.assertEquals(event.getType(), "com_telekom_DoorSensorEvent");
        Assert.assertEquals(event.getText(), "Door sensor was triggered.");
        Assert.assertEquals(event.getCreationTime().compareTo(new Date(1315303407000L)), 0);
        Assert.assertEquals(event.getTime().compareTo(new Date(1315303407000L)), 0);


    }
}