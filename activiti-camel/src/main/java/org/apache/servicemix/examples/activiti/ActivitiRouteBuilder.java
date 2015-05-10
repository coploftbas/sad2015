/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicemix.examples.activiti;

import static org.activiti.camel.ActivitiProducer.PROCESS_KEY_PROPERTY;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.Simple;

/**
 * Camel routes that interact with the business process defined in the
 * OSGI-INF/activiti/OrderProcess.bpmn20.xml file
 */
public class ActivitiRouteBuilder extends RouteBuilder {

    private final Helper helper = new Helper();

    @Override
    public void configure() throws Exception {
        from("file:var/violation")
	        .setBody(bean(helper))
	        .setProperty(PROCESS_KEY_PROPERTY, simple("file:name"))
	        .log("Violation data have been submitted")
	        .to("direct:validateViolationData");
    
        from("direct:validateViolationData")
        	.bean(Invoker.class, "validateViolationData(${body})")
        	.log("The violation data has been validated : ${body}")
        	.choice()
        	.when().simple("${body[isValid]} == true")
        		.to("direct:createTicket")
        	.otherwise()
                .to("direct:invalid");

        from("direct:createTicket")
        	.bean(Invoker.class, "createTicket(${body})")
        	.log("The ticket has been created");
        
        from("direct:invalid")
        	.log("The case was discarded");

        /*
         * This route will start a new OrderProcess instance.  Using the PROCESS_KEY_PROPERTY, we are assigning a
         * business key to our process to allow for easier correlation in later processing steps.  We are also
         * sending a Map containing additional variables to add to the process instance.
         */
//    	from("file:var/order")
//	        .setBody(bean(helper))
//	        .setProperty(PROCESS_KEY_PROPERTY, simple("file:name"))
//	        .to("activiti:OrderProcess")
//	        .log("Process to handle incoming order file has been started (process instance id ${body})");

        /*
         * This route will notify a running OrderProcess of an order delivery event.  Here too, we are setting the
         * PROCESS_KEY_PROPERTY to correlate the delivery message with right order process instance.
         */
//        from("file:var/activiti-camel/delivery")
//            .log("Notifying process about delivery for order ${file:name}")
//            .setBody(bean(helper))
//            .setProperty(PROCESS_KEY_PROPERTY, simple("file:name"))
//            .to("activiti:OrderProcess:receiveDelivery");
        
        
        /*
         * The BPMN process can also trigger Camel routes as part of the process.  In these routes, the variables that
         * you added to the process are available as Exchange properties.  The next two routes will be triggered while
         * processing the order and the order delivery.
         */
//        from("activiti:OrderProcess:getViolatedEvidenceProcess?copyVariablesToProperties=true")
//            .log("Processing evidence ${property.orderid} created on ${property.timestamp}")
//            .log("  original message: ${property.message}");
//            .setBody(bean(helper))
//            .setProperty(PROCESS_KEY_PROPERTY, simple("${property}"))
//            .to("activiti:OrderProcess:validateLicensePlateProcess");

//        from("activiti:OrderProcess:processDelivery?copyVariablesToProperties=true")
//            .log("Processing delivery for order ${property.orderid} created on ${property.timestamp}")
//            .log("  original message: ${property.message}");
        
    }

    /*
     * A few helper methods used for routing
     */
    public static final class Helper {
        /*
         * This method will extract information from the Exchange (using Camel annotations) and put them in a
         * Map that will be used for setting up the process' variables.
         */
        @Handler
        public Map getProcessVariables(@Body String violationId,
                                       @Header(Exchange.FILE_NAME) String filename,
                                       @Simple("${date:now:yyyy-MM-dd kk:mm:ss}") String timestamp) throws JAXBException, IOException {
            Map<String, Object> variables = new HashMap<String, Object>();
            
            variables.put("id", violationId);
            variables.put("filename", filename);
            variables.put("timestamp", timestamp);
            return variables;
        }
    }
}
