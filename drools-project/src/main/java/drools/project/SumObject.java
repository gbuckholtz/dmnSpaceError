package drools.project;
import java.util.List;
public class SumObject {
    private List<Integer> ListOfNumbers;
    private  Integer SingleNumber;
    private List<Vehicle> VehicleList;
    private List<Vehicle> airplane_List;


    public List<Integer> getListOfNumbers() {
    
        return ListOfNumbers;
    }
    public void setListOfNumbers(List<Integer> listOfNumbers) {
        ListOfNumbers = listOfNumbers;
    }
    public Integer getSingleNumber() {
        return SingleNumber;
    }
    public void setSingleNumber(Integer singleNumber) {
        SingleNumber = singleNumber;
    }
    public List<Vehicle> getVehicleList() {
        return VehicleList;
    }
    public void setVehicleList(List<Vehicle> vehicleList) {
        VehicleList = vehicleList;
    }
    public List<Vehicle> getAirplane_List() {
        return airplane_List;
    }
    public void setAirplane_List(List<Vehicle> airplane_List) {
        this.airplane_List = airplane_List;
    }

}
