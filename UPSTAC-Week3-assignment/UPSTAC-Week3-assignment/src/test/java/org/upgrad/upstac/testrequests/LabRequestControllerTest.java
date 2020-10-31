package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabRequestController;
import org.upgrad.upstac.testrequests.lab.TestStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
class LabRequestControllerTest {


    @Autowired
    LabRequestController labRequestController;




    @Autowired
    TestRequestQueryService testRequestQueryService;


    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_update_the_request_status(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.INITIATED);
        //Implement this method

        //Create another object of the TestRequest method and explicitly assign this object for Lab Test using assignForLabTest() method
        // from labRequestController class. Pass the request id of testRequest object.

        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'INITIATED'
        // make use of assertNotNull() method to make sure that the lab result of second object is not null
        // use getLabResult() method to get the lab result

        TestRequest test=labRequestController.assignForLabTest(testRequest.requestId);
        assertThat(testRequest.requestId,is(test.requestId));
        assertThat(test.getStatus(),is(RequestStatus.INITIATED));
        assertNotNull(test.getLabResult());


    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;

        //Implement this method


        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForLabTest() method
        // of labRequestController with InvalidRequestId as Id

        ResponseStatusException obj= assertThrows(ResponseStatusException.class,()->{labRequestController.assignForLabTest(InvalidRequestId);});


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

        assertThat(obj.getMessage(),is("Invalid ID"));

    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_valid_test_request_id_should_update_the_request_status_and_update_test_request_details(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Implement this method
        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        CreateLabResult result=getCreateLabResult(testRequest);
        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'LAB_TEST_IN_PROGRESS'. Make use of updateLabTest() method from labRequestController class (Pass the previously created two objects as parameters)
        TestRequest testRequest1=getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);
        labRequestController.updateLabTest(testRequest1.requestId,result);

        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_COMPLETED'
        // 3. the results of both the objects created should be same. Make use of getLabResult() method to get the results.


        assertThat(testRequest.requestId,is(testRequest1.requestId));
        assertThat(testRequest1.getStatus(),is(RequestStatus.LAB_TEST_COMPLETED));
        assertThat(testRequest.getLabResult(),is(testRequest1.getLabResult()));


    }


    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_test_request_id_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);


        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        CreateLabResult result=getCreateLabResult(testRequest);
        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method
        Long InvalidRequestId= -34L;
        ResponseStatusException obj=assertThrows(ResponseStatusException.class,()->{labRequestController.updateLabTest(InvalidRequestId,result);});


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"
        assertThat(obj.getMessage(),is("Invalid ID"));

    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_empty_status_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter
        // Set the result of the above created object to null.
        CreateLabResult result=getCreateLabResult(testRequest);
        result.setResult(null);


        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method
        ResponseStatusException obj=assertThrows(ResponseStatusException.class,()->{labRequestController.updateLabTest(testRequest.requestId,result);});


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "ConstraintViolationException"
        assertThat(obj.getMessage(),is("ConstraintViolationException"));

    }

    public CreateLabResult getCreateLabResult(TestRequest testRequest) {

        //Create an object of CreateLabResult and set all the values
        // Return the object
        CreateLabResult result=getCreateLabResult(testRequest);
        result.setBloodPressure("High");
        result.setComments("Normal");
        result.setHeartBeat("72");
        result.setOxygenLevel("Normal");
        result.setTemperature("98F");
        return result;



         // Replace this line with your code
    }

}