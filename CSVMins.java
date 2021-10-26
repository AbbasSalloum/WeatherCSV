/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMins {
    public CSVRecord coldestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord SmallestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            SmallestSoFar = getSmallestOfTwo(currentRow, SmallestSoFar);
        }
        //The largestSoFar is the answer
        return SmallestSoFar;
    }

    public void testColdestInDay () {
        FileResource fr = new FileResource();
        CSVRecord Smallest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature was " + Smallest.get("TemperatureF") +
                   " at " + Smallest.get("TimeEST"));
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        double lowest=0;
        double current=0;
        for(CSVRecord record : parser){
        if(lowestSoFar==null){
         lowestSoFar=record;
        }if(record.get("Humidity").equals("N/A")){
         current=-999;
        }else{
         current=Double.parseDouble(record.get("Humidity"));
        }
        if(lowestSoFar.get("Humidity").equals("N/A")){
            lowest=-999;
            }else{
            lowest=Double.parseDouble(lowestSoFar.get("Humidity"));
            }
          if(current<lowest && current!=-999){
            lowestSoFar=record;
            }  
       }
        return lowestSoFar;
    }

    public CSVRecord getSmallestOfTwo (CSVRecord currentRow, CSVRecord SmallestSoFar) {
        //If largestSoFar is nothing
        if (SmallestSoFar == null) {
            SmallestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double SmallestTemp = Double.parseDouble(SmallestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature > largestSoFar’s
            if (currentTemp < SmallestTemp) {
                //If so update largestSoFar to currentRow
                SmallestSoFar = currentRow;
            }
        }
        return SmallestSoFar;
    }

    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        
        CSVRecord Result=lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Lowest Humidity was"+ Result.get("Humidity"));
        
    }
    public String fileWithColdestTemperature () {
        //If largestSoFar is nothing
        CSVRecord coldestSoFar = null;
                String coldestFileName = null;
                DirectoryResource dr=new DirectoryResource();
        for(File f : dr.selectedFiles()){
          FileResource fr = new FileResource(f);
                  CSVRecord current = coldestHourInFile(fr.getCSVParser());
                  if(coldestSoFar == null){
                      coldestSoFar=current;
                    
                    }
                    else{
                     double currentTem = Double.parseDouble(current.get("TemperatureF"));
                     double coldestTem = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                     if(currentTem!=-9999 && currentTem < coldestTem){
                         coldestSoFar=current;
                         coldestFileName=f.getName();
                        
                        }
                    
                    }
          }
          return coldestFileName;
    }
    public void testFileWithColdestTemperature1(){
       
        String ColdestFile=fileWithColdestTemperature ();
        System.out.println(ColdestFile);
      
       }
       public void testFileWithColdestTemperature2() {
           String coldestname = fileWithColdestTemperature();
           System.out.print("Coldest day was in file ");
           System.out.println(coldestname);
           FileResource fr = new FileResource();
           CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
           System.out.print("The coldest temperature on that way was ");
           System.out.println(coldest.get("TemperatureF"));
           System.out.println("All the temperatures on the coldest day were");
           for (CSVRecord record:fr.getCSVParser()) {
            System.out.print(record.get("DateUTC"));
            System.out.print(" ");
            System.out.println(record.get("TemperatureF"));
        }
    }
       
}
