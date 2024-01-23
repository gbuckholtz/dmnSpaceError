/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * with WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package drools.project;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleTest {
    static final Logger LOG = LoggerFactory.getLogger(RuleTest.class);

    @Test
    public void FieldWithSpaces() {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }

        LOG.info("Creating kieBase");
        KieBase kieBase = kContainer.getKieBase();

        LOG.info("There should be rules: ");
        for ( KiePackage kp : kieBase.getKiePackages() ) {
            for (Rule rule : kp.getRules()) {
                LOG.info("kp " + kp + " rule " + rule.getName());
            }
        }

        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession();
        DMNRuntime dmnRuntime = session.getKieRuntime(DMNRuntime.class);
        DMNContext dmnContext = dmnRuntime.newContext();

        List<Integer> listofints = new ArrayList<>();
        for(int i = 1; i <=10; i++){
            listofints.add(i);
        }


        Vehicle vehicle = new Vehicle("Car",5);
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);
        vehicleList.add(vehicle);



        Vehicle plane = new Vehicle("Plane",5);
        List<Vehicle> planeList = new ArrayList<>();
        planeList.add(plane);
        planeList.add(plane);

        SumObject sumObject = new SumObject();
        sumObject.setListOfNumbers(listofints);
        sumObject.setSingleNumber(100);
        sumObject.setVehicleList(vehicleList);
        sumObject.setAirplane_List(planeList);

        dmnContext.set("ComplexObject",sumObject);

        DMNModel dmnModel = dmnRuntime.getModel("https://kiegroup.org/dmn/_4D1B958B-C2A1-4F14-AB7B-E1A034341217", "listtest");
        
        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

        for (DMNMessage msg: dmnResult.getMessages())
        {
            System.out.println(msg.getText());
        }

        DMNDecisionResult  withSpacesListCountResult = dmnResult.getDecisionResultByName("ListWithSpaces");
        BigDecimal withSpacesDec = (BigDecimal)withSpacesListCountResult.getResult();

        // this is where things fall apart.  we get a null value back
        // if the code is fixed this assertion will be true and the test will pass
        assertNotNull(withSpacesDec);    
        assertDoesNotThrow(()->{withSpacesDec.intValue();});

        
        int withSpacesInt = withSpacesDec.intValue();
        assertEquals(2,withSpacesInt,"Count of list from WITH spaces decision");


    }
    @Test
    public void FieldWithoutSpaces() {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kContainer = kieServices.getKieClasspathContainer();
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }

        LOG.info("Creating kieBase");
        KieBase kieBase = kContainer.getKieBase();

        LOG.info("There should be rules: ");
        for ( KiePackage kp : kieBase.getKiePackages() ) {
            for (Rule rule : kp.getRules()) {
                LOG.info("kp " + kp + " rule " + rule.getName());
            }
        }

        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession();
        DMNRuntime dmnRuntime = session.getKieRuntime(DMNRuntime.class);
        DMNContext dmnContext = dmnRuntime.newContext();

        List<Integer> listofints = new ArrayList<>();
        for(int i = 1; i <=10; i++){
            listofints.add(i);
        }


        Vehicle vehicle = new Vehicle("Car",5);
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);
        vehicleList.add(vehicle);



        Vehicle plane = new Vehicle("Plane",5);
        List<Vehicle> planeList = new ArrayList<>();
        planeList.add(plane);
        planeList.add(plane);

        SumObject sumObject = new SumObject();
        sumObject.setListOfNumbers(listofints);
        sumObject.setSingleNumber(100);
        sumObject.setVehicleList(vehicleList);
        sumObject.setAirplane_List(planeList);

        dmnContext.set("ComplexObject",sumObject);

        DMNModel dmnModel = dmnRuntime.getModel("https://kiegroup.org/dmn/_4D1B958B-C2A1-4F14-AB7B-E1A034341217", "listtest");
        
        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

        for (DMNMessage msg: dmnResult.getMessages())
        {
            System.out.println(msg.getText());
        }

        DMNDecisionResult  withoutSpacesListCountResult = dmnResult.getDecisionResultByName("ListWithoutSpaces");
        BigDecimal withoutSpacesDec = (BigDecimal)withoutSpacesListCountResult.getResult();

        assertNotNull(withoutSpacesDec);

        int withoutSpacesInt = withoutSpacesDec.intValue();
   
        assertNotNull(withoutSpacesInt);
        assertEquals(2,withoutSpacesInt,"Count of list from without spaces decision");

    }
}