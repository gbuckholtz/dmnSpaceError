package drools.project;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.drools.core.event.AfterActivationFiredEvent;
import org.drools.mvelcompiler.ast.ListAccessExprT;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

/**
 * Azure Functions with HTTP Trigger.
 */
public class SpacesInFieldNames {

    Integer assessment_Count;
    Boolean sROR_Granted;
    LocalDate hiring_Date;
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    public static void main(String[] args){

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();

        KieBase kBase = kContainer.getKieBase();
        KieSession kSession = kBase.newKieSession();

        DMNRuntime dmnRuntime = kSession.getKieRuntime(DMNRuntime.class);

        DMNModel dmnModel = dmnRuntime.getModel("https://kiegroup.org/dmn/_691425E9-D743-4F6F-9019-2607F6766BFD", "sror");
        
      
        kSession.addEventListener(new DebugAgendaEventListener());

       
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

        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

        for (DMNMessage msg: dmnResult.getMessages())
        {
            System.out.println(msg.getText());
        }

        DMNDecisionResult  listSum = dmnResult.getDecisionResultByName("AppendListSum");
        BigDecimal decVal = (BigDecimal)listSum.getResult();
        Integer intVal = decVal.intValue();
    
    }
}
